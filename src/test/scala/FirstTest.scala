import org.scalatest._

class FirstTest extends FunSuite {
  test("Hello should start with H") {
    assert("hello".startsWith("h"))
    assert(true != false)
  }
}