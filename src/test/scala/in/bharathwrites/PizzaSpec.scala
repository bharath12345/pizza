package in.bharathwrites

import in.bharathwrites.Pizza.Order
import org.scalatest._

import scala.collection.mutable

class PizzaSpec extends FlatSpec with Matchers {

  "Average Wait Time" should "say 9" in {
    val orders = mutable.Buffer(Order(0, 3), Order(1, 9), Order(2, 6))
    Pizza.findAverageWait(orders) shouldEqual 9
  }

  it should "say 8" in {
    val orders = mutable.Buffer(Order(0, 3), Order(1, 9), Order(2, 5))
    Pizza.findAverageWait(orders) shouldEqual 8
  }

  it should "say 1 for 1000 orders" in {
    val max_one_min_orders = (for(i <- new Range(0, 1000, 1)) yield Order(i, 1)).toBuffer
    Pizza.findAverageWait(max_one_min_orders) shouldEqual 1
  }

  it should "say 1 for 10000 orders" in {
    val max_one_min_orders = (for(i <- new Range(0, 10000, 1)) yield Order(i, 1)).toBuffer
    Pizza.findAverageWait(max_one_min_orders) shouldEqual 1
  }

  it should "say 1 for 100000 orders" in {
    val max_one_min_orders = (for(i <- new Range(0, 100000, 1)) yield Order(i, 1)).toBuffer
    Pizza.findAverageWait(max_one_min_orders) shouldEqual 1
  }

  // this took 2min:45sec on my laptop
  it should "say 1 for 1000000 orders" in {
    val max_one_min_orders = (for(i <- new Range(0, 1000000, 1)) yield Order(i, 1)).toBuffer
    Pizza.findAverageWait(max_one_min_orders) shouldEqual 1
  }

  /*it should "say 1 for 1000000000 orders" in {
    val max_one_min_orders = (for(i <- new Range(0, 1000000000, 1)) yield Order(i, 1)).toArray
    Pizza.findAverageWait(max_one_min_orders) shouldEqual 1
  }*/
}
