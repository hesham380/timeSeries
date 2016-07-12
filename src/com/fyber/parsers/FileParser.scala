package com.fyber.parsers

import com.fyber.models.PriceRatioModel
import java.io.PrintWriter
import com.fyber.models.OutputModel
import java.io.File
import scala.io.Source


/**
 * @author hesham380
 * 
 * Singleton class used to parse input file and output result table to file named "outputTable" and in console 
 *
 */
object FileParser {


  def parseFile(filePath: String) {
    //creating output model which will be the window holding the items
    var outModel = new OutputModel();
    //writer to write to file
    val writer = new PrintWriter(new File("outputTable.txt"))

    //reading input file line by line and adding it to window(output model) then printing window
    try {
      //printing header 
      println("T         \t\tV      \t\tN\t\tRS    \t\tMinV  \t\tMaxV");
      println("---------------------------------------------");
      writer.write("T         \t\tV      \t\tN\t\tRS    \t\tMinV  \t\tMaxV" + "\n");
      writer.write("-----------------------------------------------------------------------" + "\n");
      for (line <- Source.fromFile(filePath).getLines) {
        var list = line.split(" ");
        if (list.size < 2) {
          list = line.split("\t")
        }
        var p1 = list(0).toInt;
        var p2 = list(1).toDouble;
        //round double to 5 decimal places
        var p2Round = (p2 * 100000).round / 100000.toDouble;
        var p = new PriceRatioModel(p1, p2Round);
        outModel.addItem(p);
        outModel.print();
        writer.write(outModel.getString() + "\n")
      }
      //closing & flushing writer
      writer.close();
      writer.flush();

    } catch {
      case e: Exception => {
        e.printStackTrace();
        println("Exception in reading input")
      }
    }
  }

}