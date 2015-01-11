package week1
import scala.concurrent.{Future , Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by Dim on 11.01.2015.
 */
object Main extends App {



  val sumF = Future {
    (1L to 100000L).sum
  }

  sumF onSuccess {
    case s => println(s"s = $s")
  }

  // или лучше так: val doubledSumF = sumF.map(_ * 2)
  val doubledSumF = sumF.flatMap {
    case s => Future { s * 2 }
  }

  val tripledSumF = sumF.flatMap {
    case s => Future { s * 3 }
  }

  val resultF = for {
    s1 <- doubledSumF
    s2 <- tripledSumF
  } yield s1 + s2

  val result = Await.result(resultF, 5.seconds)
  println(s"result = $result")

}
