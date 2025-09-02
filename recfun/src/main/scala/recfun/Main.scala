package recfun
import common._

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
      @tailrec
    def loop(c:Int, r:Int, prevRow: List[Int], curRowNum:Int): Int = {
        val middle = prevRow.zip(prevRow.tail).map{(a) => a._1 + a._2}
        val curRow = 1 :: (middle ::: List(1))
        if (r==curRowNum) curRow(c)
        else loop(c, r, curRow, curRowNum + 1)
      }
    if(c==0 || r==0) 1
    else loop(c, r, List(1), 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = ???

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
