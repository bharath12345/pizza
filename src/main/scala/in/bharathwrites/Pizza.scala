package in.bharathwrites

import java.io.File

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.control.NonFatal

object Pizza extends App {

  case class Order(order_time: Long, cook_time: Long)

  case class PositionedOrder(order_time: Long, cook_time: Long, position: Long)

  if (args.isEmpty) {
    println(s"program needs input file as argument. exiting")
  } else {
    val input = new File(args(0))
    if (!input.exists()) {
      println(s"file ${args(0)} does not exist. exiting")
    } else {
      readInputFile(input)
    }
  }

  def readInputFile(input: File) = {
    println(s"reading input file ${args(0)}")
    try {
      val source = scala.io.Source.fromFile(input)
      val lines = source.getLines().toVector
      val num_customers = lines.head.trim.toLong
      val order_pattern = """^(\d+)\s+(\d+)$""".r

      // using mutable buffer instead of any immutable collection because of the remove operation in
      // findAverageWait function which is a constant time operation. Not using parallel collections and the algorithm
      // is single threaded, so mutable buffers should be safe to use
      val orders: mutable.Buffer[Order] = (for {
        l <- lines.tail; line = l.trim; if line.nonEmpty
      } yield {
        line match {
          case order_pattern(ti, li) => Option(Order(ti.toLong, li.toLong))
          case _ => println(s"invalid line: $line"); None
        }
      }).flatten.toBuffer

      source.close()

      if (orders.size != num_customers) {
        println(s"num of customers from first line = $num_customers, but the number of actual orders in input file = " +
          s"${orders.size}. please fix the input data. exiting")
      } else {
        val orders_by_time = orders.sortBy(_.order_time)
        val average_wait_time = findAverageWait(orders_by_time)
        println(s"Minimum Average Wait Time = ${average_wait_time}")
      }
    } catch {
      case NonFatal(ex) =>
        println(s"hit exception: ${ex.getLocalizedMessage}")
        ex.printStackTrace()
    }
  }

  def findAverageWait(orders: mutable.Buffer[Order]): Long = {
    def findOrdersAtCurrentTime(o: mutable.Buffer[Order], current_time: Long): mutable.Buffer[PositionedOrder] = {
      o.takeWhile(_.order_time <= current_time).zipWithIndex.map {
        case (ord, pos) => PositionedOrder(ord.order_time, ord.cook_time, pos)
      }
    }

    def findSmallestCookTimeOrder(o: mutable.Buffer[PositionedOrder]): PositionedOrder = {
      // if there is an order of 1min it avoids the expensive sort
      val small_orders: mutable.Buffer[PositionedOrder] = o.takeWhile(_.cook_time == 1)
      if (small_orders.isEmpty)
        o.sortBy(_.cook_time).head
      else
        small_orders.head
    }

    @tailrec def inner(o: mutable.Buffer[Order], current_time: Long, wait_time: Long): Long = {
      val outstanding_orders = findOrdersAtCurrentTime(o, current_time)
      val smallest_cook_time_order = findSmallestCookTimeOrder(outstanding_orders)
      val new_wait_time = wait_time + (current_time + smallest_cook_time_order.cook_time - smallest_cook_time_order.order_time)
      val new_current_time = current_time + smallest_cook_time_order.cook_time
      o.remove(smallest_cook_time_order.position.toInt)
      o.isEmpty match {
        case true => new_wait_time
        case false => inner(o, new_current_time, new_wait_time)
      }
    }

    val order_count = orders.length
    val total_wait_time = inner(orders, 0, 0)
    total_wait_time / order_count
  }
}

