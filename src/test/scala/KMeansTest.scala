import com.cruder.vivhaldoop.Point
import com.cruder.vivhaldoop.KMeans
import org.scalatest.{FlatSpec, Matchers}

class KMeansTest extends FlatSpec with Matchers {
  "closest" should "return a Point with the index of the closest center" in {
    // Given
    val centers: Array[Point] = Array(Point(0, 0, 0), Point(100, 100, 1), Point(-1000, -1000, 2))
    val point: Point = Point(-1001, -1001, 1)

    // When
    val result = KMeans.closest(centers, point)
    // Then
    result shouldBe Point(-1001, -1001, 2)
  }

  "getCenters" should "return the centers of each categories" in {
    // Given
    val k: Int = 3
    val values: Array[Point] = Array(Point(0, 0, 0), Point(300, 300, 1), Point(-1000, -1000, 2)
      , Point(1, 1, 0), Point(100, 100, 1), Point(-3000, -3000, 2))

    // When
    val centers: Array[Point] = KMeans.getCenters(k, values)

    // Then
    val expected: Array[Point] = Array(Point(0.5, 0.5, 0), Point(200, 200, 1), Point(-2000, -2000, 2))

    centers should contain theSameElementsAs expected
  }

  "kmeans" should "return the right category for each point" in {
    // Given
    val data: Array[Point] = Array(
      Point(10, 5, 0),
      Point(30, 30, 0),
      Point(54, 33, 0),
      Point(15, 1, 0),
      Point(21, 8, 0),
      Point(41, 12, 0),
      Point(165, 9, 0),
      Point(165, 20, 0),
      Point(435, 3, 0),
      Point(68, 13, 0),
      Point(65, 1, 0),
      Point(234, 3, 0),
      Point(390, 3, 0),
      Point(131, 0, 0),
      Point(197, 6, 0)
    )

    // When
    val results: Array[Int] = KMeans.kmeans(3, data, 100).map(p => p.cat)

    // Then
    val expected: Array[Int] = Array(0, 0, 0, 0, 0, 0, 1, 1, 2, 0, 0, 1, 2, 1, 1)

    results should contain theSameElementsAs expected
  }
}