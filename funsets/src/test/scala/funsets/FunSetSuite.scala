package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet contains elem") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s2, 1), "Singleton")
      assert(contains(s2, 2), "Singleton")
      assert(!contains(s3, 2), "Singleton")
      assert(contains(s3, 3), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("forall with complex sets") {
    new TestSets {
      val setForAll = union(union(s1, s2), union(s3, s4))
      val evens = intersect(setForAll, union(s2, s4))

      assert(forall(setForAll, x => x > 0), "All elements positive")
      assert(forall(evens, x => x % 2 == 0), "All elements of evens are even")
      assert(!forall(setForAll, x => x % 2 == 0), "Not all elements are even")
    }
  }

  test("exists with complex sets") {
    new TestSets {
      val setExists = union(union(s1, s2), union(s3, union(s4, s5)))

      assert(exists(setExists, x => x % 2 == 1), "Odd exist")
      assert(!exists(setExists, x => x > 10), "element > 10 not exists")
      assert(exists(setExists, x => x == 3 || x == 4), "3 or 4 exists")
      assert(!exists(setExists, x => x == 3 && x == 4), "3 also 4 not exists")
    }
  }

  test("map with complex sets") {
    new TestSets {
      val setMap = union(union(s1, s2), s3)
      val squares = map(setMap, x => x * x)
      assert(contains(squares, 1), "Squares contains 1")
      assert(contains(squares, 4), "Squares contains 4")
      assert(contains(squares, 9), "Squares contains 9")
      assert(!contains(squares, 2), "Squares not contains 2")

      val shifted = map(setMap, x => x + 10)
      assert(contains(shifted, 11), "Shifted contains 11")
      assert(contains(shifted, 12), "Shifted contains 12")
      assert(contains(shifted, 13), "Shifted contains 13")

      val setMap2 = union(s4, s5)
      val mod2 = map(setMap2, x => x % 2)
      assert(contains(mod2, 0), "Mapped contains 0")
      assert(contains(mod2, 1), "Mapped contains 1")
    }
  }
}
