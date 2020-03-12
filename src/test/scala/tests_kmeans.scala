import spark.implicits._

"closest" should "return a Data with the index of the closest center" in {
    // Given
    val centers: Array[Data] = Array(Data(0, 0,0),Data(100,100,1), Data(-1000, -1000,2))
    val point: Data = Data(-1001,-1001, 1)

    // When
    val result = closest(centers, point)
    // Then
    result shouldBe Data(-1001, -1001, 2)
}

"getCenters" should "return the centers of each categories" in {
    // Given
    val k: Int = 3
    val values: Array[Data] = Array(Data(0, 0,0), Data(300,300,1), Data(-1000, -1000,2)
                                ,Data(1, 1,0), Data(100,100,1), Data(-3000, -3000,2))

    // When
    val centers: Array[Data] = getCenters(k, values)

    // Then
    val expected: Array[Data] = Array(Data(0.5, 0.5, 0), Data(200, 200, 1), Data(-2000, -2000, 2))

    centers should contain theSameElementsAs expected
}
