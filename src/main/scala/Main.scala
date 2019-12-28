import java.io.{File, PrintStream}

import org.apache.log4j.varia.NullAppender

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main {
  def main(args: Array[String]): Unit = {
    val o = new PrintStream(new File("A.txt"))

    val console = System.out
    System.setOut(o)

    val ocrParser = new OCRParser()

    val x = new File("./pdfs").listFiles().map{ f =>
      Future(ocrParser.parsePDF(f))
    }.toList

    val y = Await.result(Future.sequence(x), Duration.Inf)

    System.setOut(console)

    println(y.foldLeft(""){ (acc, text) =>
      acc + text + "\n\n\t\n\n"
    })

    new File("A.txt").delete()
  }
}