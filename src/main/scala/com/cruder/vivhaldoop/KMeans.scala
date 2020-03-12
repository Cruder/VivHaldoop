package com.cruder.vivhaldoop

import scala.math.{pow, sqrt}
import scala.util.Random
import org.apache.spark.sql.{DataFrame, SparkSession}

case class Point(x: Double, y: Double, cat: Int)

object KMeans {
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
    * @param k      number of centers
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
    * Returns a DataFrame with 3 columns
    *
    * @param k          The number of clusters
    * @param values     The values of lat/long or nbWords/nbHashtags.
    * @param iterations Number of iterations of the algorithm
    */
  def kmeans(k: Int, values: Array[Point], iterations: Int): Array[Point] = {
    // Initialization
    val r: Random = Random

    // Construction de la liste avec une catégorie aléatoire
    var arr: Array[Point] =
        values.map(o => Point(o.x, o.y, r.nextInt(k)))

    // k-first centers as Array
    var centers: Array[Point] = (
      for (i <- 0 until k)
        yield Point(arr(i).x, arr(i).y, i)
      ) toArray

    // Cycles
    for (_ <- 0 until iterations) {
      // Assignation aux clusters
      arr = arr.map(t => closest(centers, t))

      // Calcul des nouveaux centres
      centers = getCenters(k, arr)
    }

    // Assignation finale
    arr.map(t => closest(centers, t))
  }
}