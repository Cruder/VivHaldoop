package com.cruder.vivhaldoop

import org.apache.spark.rdd.RDD
import scala.math.{pow, sqrt}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.lit
import com.cruder.vivhaldoop.share.AppProperties.spark

case class Point(x: Double, y: Double, cat: Int)

object KMeans {
  def main(args: Array[String]): Unit = {
    spark.sqlContext.sql("use twitter")
    val df: DataFrame = spark.sqlContext.sql("SELECT lat, long FROM twitter.hashtags")
      .withColumn("cat", lit(0))
      .withColumnRenamed("lat", "x")
      .withColumnRenamed("long", "y")

    import spark.sqlContext.implicits._
    val rdd: RDD[Point] = df.as[Point].rdd

    val rddKMeans: RDD[Point] = kmeans(4, rdd, 1000)
    //rddKMeans.toDF().write.option("path", path).saveAsTable("kmeans")
  }

  /**
    * returns a Point with the index of the closest center
    */
  def closest(centers: Array[Point], point: Point): Point = {
    // Distances between the point and each centers
    val list: Array[Double] =
      centers
        .sortBy(_.cat)
        .map(t => magnitude(t.x, t.y, point.x, point.y))

    // The Point's category is the closest distance (i.e. the minimum value of the list)
    Point(point.x, point.y, list.indices.minBy(list))
  }

  /**
    * Returns the Mathematic magnitude between 2 coordinates
    *
    * @param x1 , y1  The coordinates of the first point
    * @param x2 , y2  The coordinates of the second point
    */
  def magnitude(x1: Double, y1: Double, x2: Double, y2: Double): Double =
    sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2))

  /**
    * Array to 3 cols DataFrame
    *
    * @param values The Array to convert
    */
  def toDataFrame(spark: SparkSession, values: Array[Point]): DataFrame = {
    val rdd = spark.sparkContext.parallelize(values)
    spark.createDataFrame(rdd)
  }

  /**
    * Returns the centers of each categories
    *
    * @param k      Number of centers
    * @param values The values to use
    */
  def getCenters(k: Int, values: Array[Point]): Array[Point] =
    (for (i <- 0 until k)
      yield Point(
        values.filter(t => t.cat == i).foldLeft(0.0)(_ + _.x) / values.count(t => t.cat == i) // Average of x
        , values.filter(t => t.cat == i).foldLeft(0.0)(_ + _.y) / values.count(t => t.cat == i) // Average of y
        , i) // Category
      ) toArray

  /**
    * Sums 2 Point coordinates
    *
    * @param p1  The first Point
    * @param p2  The second Point
    */
  def sumPoints(p1: Point, p2: Point): Point =
    Point(p1.x + p2.x, p1.y + p2.y, p1.cat)

  /**
    * Returns a DataFrame with 3 columns
    *
    * @param k          The number of clusters
    * @param values     The values of lat/long or nbWords/nbHashtags.
    * @param iterations Number of iterations of the algorithm
    */
  def kmeans(k: Int, values: RDD[Point], iterations: Int): RDD[Point] = {
    // Initialization
   // val r: Random = Random

    // Construction de la liste avec une catégorie aléatoire
   // val arr: RDD[Point] =
     //   values.map(o => Point(o.x, o.y, r.nextInt(k)))

    // k-first centers as Array // takeSample
    var i = -1
    var centers: Array[Point] =
      values.takeSample(withReplacement = true, k)
      .map(p => {
        i = i + 1
        Point(p.x, p.y, i)
      })

   /* var centers: Array[Point] = (
      for (i <- 0 until k)
        yield Point(arr(i).x, arr(i).y, i)
      ) toArray */

    // Cycles
    for (_ <- 0 until iterations) {
      // Calcul des nouveaux centres
      centers =
        values
          .map(t => closest(centers, t))
          .map(t => (t.cat, (t, 1.0)))
          .reduceByKey{(p1, p2) => (sumPoints(p1._1, p2._1), p1._2 + p2._2)}
          .map{case (cat, (p, cpt)) => (cat, Point(p.x / cpt, p.y / cpt, cat))}
          .map(t => t._2)
          .collect()
    }

    // Assignation finale
    values.map(t => closest(centers, t))
  }
}