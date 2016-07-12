package com.fyber.models

import scala.collection.mutable.ArrayBuffer

/**
 * @author hesham380
 * Class which represents window and holds list of current items in window and attributes needed
 *  ArrayBuffer list -> holds current items in window
 *  Double RS -> rolling sum of measurements in the window.
 *  Double MinV -> minimum price ratio in the window
 *  Double MaxV -> maximum price ratio the window  
 */
class OutputModel {
  var list = new ArrayBuffer[PriceRatioModel]();
  var RS: Double = 0;
  var MinV: Double = 0;
  var MaxV: Double = 0;

  //function to print row in table
  def print() {
    //getting last element in list
    var last: PriceRatioModel = list.last;
    // appending values in line to be printed
    var str: String = last.numOfSeconds + "\t\t" + last.priceRatioMeasurement + "\t\t" + list.size + "\t\t" + RS + "\t\t" + MinV + "\t\t" + MaxV;
    println(str);
  }
 //function to return string containg row 
  def getString(): String = {
    //getting last element in list
    var last: PriceRatioModel = list.last;
    // appending values in line to be printed
    var str: String = last.numOfSeconds + "\t\t" + last.priceRatioMeasurement + "\t\t" + list.size + "\t\t" + RS + "\t\t" + MinV + "\t\t" + MaxV;
    return str;
  }
  
  //function to add new item to window and fit in window and adjust parameters accordingly
  def addItem(p: PriceRatioModel) {
    //add new Item in window and update window to fit size

    //list is empty then add item and update values
    if (list.isEmpty) {
      list += p;
      RS = round(p.priceRatioMeasurement);
      MinV = round(p.priceRatioMeasurement);
      MaxV = round(p.priceRatioMeasurement);
    } // list is not empty then adjust window to fit new item
    else {
      var first: Int = list.head.numOfSeconds;
      var diff: Int = p.numOfSeconds - first;
      while (diff > 60 && !list.isEmpty) {
        list.remove(0);
        if (!list.isEmpty) {
          first = list.head.numOfSeconds;
          diff = p.numOfSeconds - first;
        }
      }
      //adding new item to list
      list += p;
      //resetting max and min
      MinV = Double.MaxValue;
      MaxV = Double.MinValue;
      //resetting Rolling sum
      RS = 0;
      //calculating new min and max and Rolling sum
      for (item <- list) {
        RS += item.priceRatioMeasurement;
        RS = round(RS);
        if (item.priceRatioMeasurement < MinV)
          MinV = round(item.priceRatioMeasurement);

        if (item.priceRatioMeasurement > MaxV)
          MaxV = round(item.priceRatioMeasurement);
      }

    }

  }
  
  
  //function to round to double to 5 decimal places
  def round(n:Double) : Double ={
    var res = (n * 100000).round / 100000.toDouble;
    return res;
  }
}