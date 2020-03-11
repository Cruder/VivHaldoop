import org.apache.spark.sql.DataFrame

case class Tweet(x: Double, y: Double)

val rdd = spark.sparkContext.parallelize(Seq(
  Tweet(10, 5), Tweet(30, 30), Tweet(54, 33)
  ,Tweet(15, 1), Tweet(21, 8), Tweet(41, 12)
  ,Tweet(165, 9), Tweet(165, 20), Tweet(435, 3)
  ,Tweet(68, 13), Tweet(65, 1), Tweet(234, 3)  
  ,Tweet(390, 3), Tweet(131, 0), Tweet(197, 6)
))

val df: DataFrame = spark.createDataFrame(rdd)
df.show()



/*************************/


import scala.util.Random
import scala.collection.immutable.Map
import scala.math.{pow, sqrt}

case class TweetEnhanced(x: Double, y: Double, cat: Int)

/**
* @param k  The number of clusters
* @param df  The DataFrame of lat/long or nbWords/nbHashtags.
* @param iterations  Number of iterations of the algorithm
*/
def kmeans(k: Int, df: DataFrame, iterations: Int)/*: DataFrame */= {
  // Initialization
  val r:Random = Random
  val size: Int = df.count().toInt
  
  // Construction de la liste avec une catégorie aléatoire
  val arr: Array[TweetEnhanced] = 
      df.as[Tweet].collect()
      .map(t => TweetEnhanced(t.x, t.y, r.nextInt(k)))

        println("Test arr")
    for(element<-arr)  
        {  
            println(element)  
        }  
  
  // k-first centers as Array
  val centers: Array[TweetEnhanced] = (
    for(i <- 0 until k)
      yield (TweetEnhanced(arr(i).x, arr(i).y, i))
  ) toArray
  
      println("Test centers")
    for(element<-centers)  
        {  
            println(element)  
        }  
  
  // Cycles
  for(i <- 0 until iterations) {
    println("It")
    // Assignation aux clusters
    val temp: Array[TweetEnhanced]
      = arr.map(t => closest(centers, t))
    
      println("\nTest result")
    for(element<-temp)  
        {  
            println(element)  
        }  
  }

}

/**
* returns a TweetEnhanced with the index of the closest center
*/
def closest(centers: Array[TweetEnhanced], tweet: TweetEnhanced): TweetEnhanced = {
  val list: Array[Double] = 
    centers
    .sortBy(_.cat)
    .map(t => magnitude(t.x, t.y, tweet.x, tweet.y))
  
  TweetEnhanced(tweet.x, tweet.y, list.indices.minBy(list))
}

/**
* Returns the Mathematic magnitude between 2 coordinates
*
* @params x1, y1  The coordinates of the first point
* @params x2, y2  The coordinates of the second point
*/
def magnitude(x1: Double, y1: Double, x2: Double, y2: Double): Double = 
  sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2))

println("Test Close: " + closest(Array(TweetEnhanced(0, 0,0), TweetEnhanced(100,100,1), TweetEnhanced(-1000, -1000,2)), TweetEnhanced(-1001,-1001, 1)))

//println(df.as[Tweet].collect())

kmeans(3, df, 2)