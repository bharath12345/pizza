package in.bharathwrites

import java.io.File

import scala.annotation.tailrec
import scala.util.control.NonFatal

object Pizza extends App {

  case class Order(order_time: Long, cook_time: Long)

  if(args.isEmpty) {
    println(s"program needs input file as argument. exiting")
  } else {
    val input = new File(args(0))
    if(!input.exists()) {
      println(s"file ${args(0)} does not exist. exiting")
    } else {
      println(s"reading input file ${args(0)}")
      try {
        val source = scala.io.Source.fromFile(input)
        val lines = source.getLines().toVector
        val num_customers = lines.head.trim.toLong
        val order_pattern = """^(\d+)\s+(\d+)$""".r
        val orders: Vector[Order] = (for {
          l <- lines.tail; line = l.trim; if line.nonEmpty
        } yield {
          line match {
            case order_pattern(ti, li) => Option(Order(ti.toLong, li.toLong))
            case _ => println(s"invalid line: $line"); None
          }
        }).flatten
        source.close()
        if (orders.size != num_customers) {
          println(s"num_customers from first line = $num_customers, but the number of actual orders in input file = " +
            s"${orders.size}. please fix the input data. exiting")
        } else {
          val orders_by_time = orders.sortBy(_.order_time)
          println(s"Minimum Average Wait Time = ${findAverageWait2(orders_by_time)}")
        }
      } catch {
        case NonFatal(ex) =>
          println(s"hit exception: ${ex.getLocalizedMessage}")
          ex.printStackTrace()
      }
    }
  }

  def findAverageWait(orders: Vector[Order]): Long = {
    var current_time = 0L
    var pending_orders = orders
    var wait_time = 0L

    while(pending_orders.nonEmpty) {
      val outstanding_orders = findOrdersAtCurrentTime(pending_orders, current_time)
      val smallest_cook_time_order = findSmallestCookTimeOrder(outstanding_orders)
      wait_time = wait_time + (current_time + smallest_cook_time_order.cook_time - smallest_cook_time_order.order_time)
      current_time = current_time + smallest_cook_time_order.cook_time
      pending_orders = pending_orders.filter(_ != smallest_cook_time_order)
    }

    wait_time / orders.length
  }

  def findOrdersAtCurrentTime(orders: Vector[Order], current_time: Long): Vector[Order] = {
    orders.filter(_.order_time <= current_time)
  }

  def findSmallestCookTimeOrder(orders: Vector[Order]): Order = {
    orders.sortBy(_.cook_time).head
  }

  ///

  def findAverageWait2(orders: Vector[Order]): Long = {
    @tailrec def inner(o: Vector[Order], current_time: Long, wait_time: Long): Long = {
      val outstanding_orders = findOrdersAtCurrentTime(o, current_time)
      val smallest_cook_time_order = findSmallestCookTimeOrder(outstanding_orders)
      val new_wait_time = wait_time + (current_time + smallest_cook_time_order.cook_time - smallest_cook_time_order.order_time)
      val new_current_time = current_time + smallest_cook_time_order.cook_time
      val pending_orders = o.filter(_ != smallest_cook_time_order)
      pending_orders.isEmpty match {
        case true => new_wait_time
        case false => inner(pending_orders, new_current_time, new_wait_time)
      }
    }

    val total_wait_time = inner(orders, 0, 0)
    total_wait_time / orders.length
  }
}

