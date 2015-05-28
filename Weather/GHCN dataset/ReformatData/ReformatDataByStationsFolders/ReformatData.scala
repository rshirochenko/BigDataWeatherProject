import java.io.StringReader
import java.io.StringWriter

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

import collection.JavaConversions._
import scala.collection.immutable.ListMap


object ReformatData {
  
  ///*Form the line with features column format (station id, date, tmin, tmax, tobs, prcp, snow, snwd)   *///
  def formLine(date_arr:Array[Array[String]]):String = {
    var formated_line = Array.fill[String](8)("")
    date_arr.foreach{line =>
      formated_line(0) = line(0) //station ID
      formated_line(1) = line(1) //date
      var x = line(2)
      x match {
        case "TMIN" => formated_line(2) = line(3) 
        case "TMAX" => formated_line(3) = line(3)
        case "TOBS" => formated_line(4) = line(3)
        case "PRCP" => formated_line(5) = line(3)
        case "SNOW" => formated_line(6) = line(3)
        case "SNWD" => formated_line(7) = line(3)
        case _ => 
      } 
    }
    return formated_line.mkString(",")
  }
  
  //form the line by column	    		
  def reformatByColumn(stringsFromOneStation:String):String = {
    var lines = stringsFromOneStation.split(";")
    var lines_group_by_date = lines.map(_.split(",")).groupBy(x => x(1))
    var lines_group_by_date_sorted = ListMap(lines_group_by_date.toSeq.sortBy(_._1):_*)
    
    var result = lines_group_by_date_sorted.map{group => formLine(group._2)} 
    return result.mkString(";")
  }

  def main(args: Array[String]) {
    	val sc = new SparkContext(new SparkConf().setAppName("ReformatData"))
     
      //Open weather_data 
      val data = sc.sequenceFile[String, String]("/projects/bravvfings/dataset/ghcn_seq_column/*")

      //Open the stations file
      val stations_file = sc.textFile("/projects/bravvfings/active_stations_id.txt")
     
      //Filter weather_stations and save as sequence file (TODO: separate this action in different file)
      //val us_stations = data.map(x => (x._1,reformatByColumn(x._2))).filter(x => x._1 contains "US")
      //us_stations.saveAsSequenceFile("/projects/bravvfings/dataset/ghcn_seq_column")
      
      //Saving folders by stations r
      val stations_id_arr = stations_file.collect()
      stations_id_arr.foreach{ station_id =>
        data.filter(s => s._1 contains station_id).flatMap(x => x._2.split(";")).coalesce(1,true).saveAsTextFile("/projects/bravvfings/dataset/ghcn_by_active_station/"+station_id)
      }
  }
}
