import scala.io.Source
import com.fyber.models.PriceRatioModel
import com.fyber.models.OutputModel
import java.io.PrintWriter
import java.io.File
import com.fyber.parsers.FileParser

/**
 * @author hesham380
 * Main Class which calls parser to process file
 *
 */
object Executor {

  def main(args: Array[String]) {
    FileParser.parseFile("input.txt");
  }

}