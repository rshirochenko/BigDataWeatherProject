package Dataset1

import java.util.{Calendar}
import java.io.File
import java.io.StringReader
import java.io.StringWriter
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import collection.JavaConversions._
import scala.collection.immutable.ListMap
 // NOT DONE YET
//TODO
// 
// CRON Task in the web part to run at 0X AM ?
//- ReDownload the current year file, could be long
  object UpdateData {
        val sc = new SparkContext(new SparkConf().setAppName("UpdateData"))

      def main(args: Array[String]) {

val today = Calendar.getInstance().getTime()
val year = today.getYear() + 1900
val month = (today.getMonth() +1)
var  month_Str ="";
if (month < 10) { month_Str  = "0".concat(month.toString()) } else { month_Str = month.toString()}

val day = today.getDate()
// TODO Update this path and the call to the ile
val path = year.toString().concat(".csv")
val weather_data_file = sc.textFile("file:///home/vinhmau/updata/2015-crop.csv")

//val curdat = year.toString().concat(month_Str.concat(day.toString()))
val curdat = "20150523"
// Filter updatefile to only keep the current date , around 100k line

val weather_data = weather_data_file.map(line => line.split(","))
val weather_data_filtered  = weather_data.filter ( x => x(1) == curdat ).map( x => x)

// Array[String] of each of our stationsid
//TODO Change this path
//var fileList =  new File("/home/vinhmau/updata/stations/").listFiles.filter(_.isDirectory).map(_.getName)

 val rdd = sc.textFile("hdfs:///projects/bravvfings/dataset/stationslist/stations.txt")
 val fileList = rdd.toArray.toList
// Now we only got all the lines we have to add .map(str => (str(0),str(1), str(2), str(3)) )
 val linestoAdd = weather_data_filtered.filter ( x => fileList.contains(x(0)) ).map(x=>(x(0),x(1),x(2),x(3)))

linestoAdd.coalesce(1,true).saveAsTextFile("hdfs:////projects/bravvfings/dataset/TEST/TEMP4/newdata.csv")

val file = sc.textFile("hdfs:////projects/bravvfings/dataset/TEST/TEMP4/newdata.csv")
val lines2 = file.map(x=> (x.split(",")(0), x)).groupByKey
 lines2.mapValues(x => x.mkString(";")).saveAsSequenceFile("hdfs:////projects/bravvfings/dataset/TEST/TEMP4/newdataparsed.csv")


val data = sc.sequenceFile[String, String]("hdfs:////projects/bravvfings/dataset/TEST/TEMP4/newdataparsed.csv")

      //Open the stations file
     
      //Filter weather_stations and save as sequence file (TODO: separate this action in different file)
      //val us_stations = data.map(x => (x._1,reformatByColumn(x._2))).filter(x => x._1 contains "US")
      //us_stations.saveAsSequenceFile("/projects/bravvfings/dataset/ghcn_seq_column")
      
      //Saving folders by stations r
     

//val formatteddata = data.map(x => x._1,reformatByColumn(x._2)) // PROBLEM AT THIS STEP
      //ReformatByColumn(stringsFromOneStation:String)
      //val inter = data.map(x => reformatByColumn(x._2) )
      val inter2 = data.map(x =>(x._1,  x._2 ))
      var lines = inter2.map(x => (x._1 , x._2.split(";")))

    var lines_group_by_date = lines.map(x=> (x._1,x._2.map(_.split(",")).groupBy(x => x(1))))

   var lines_group_by_date_sorted = lines_group_by_date.map(x => (x._1,ListMap((x._2).toSeq.sortBy(_._1):_*)))

  var result = lines_group_by_date_sorted.map(x => (x._1, x._2.map{group => formLine(group._2)}))
  var final_result = result.map(x => x +";");

fileList.foreach{ station_id => //try {
        val tempresult =  result.filter(s => s._1 contains station_id).flatMap(x => x._2)//.coalesce(1,true) .saveAsTextFile("file:///home/vinhmau/updata2/derp4/"+station_id)
      val oldresult= sc.textFile("hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/" + station_id)
      val finalfile = oldresult.union(tempresult);
      finalfile.coalesce(1,true) .saveAsTextFile("hdfs:////projects/bravvfings/dataset/TEST/TEMP4/result/"+station_id)
          //}
     //catch {
       //    case _: Throwable => {
         //         val oldresult= sc.textFile("hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/" + station_id)
           //     oldresult.coalesce(1,true) .saveAsTextFile("hdfs:////projects/bravvfings/dataset/TEST/TEMP4/result/"+station_id)


           //}
  //}
      }



}
  def formLine(date_arr:Array[Array[String]]):String = {
    var formated_line = Array.fill[String](5)("")
    date_arr.foreach{line =>
      formated_line(0) = line(0).replaceAll("\\(", "")  //station ID
      formated_line(1) = line(1) //date
      var x = line(2)
      x match {
        case "TMIN" => formated_line(2) = line(3).replaceAll("\\)", "") 
        case "TMAX" => formated_line(3) = line(3).replaceAll("\\)", "") 
        //case "TOBS" => formated_line(4) = line(3)
        case "PRCP" => formated_line(4) = line(3).replaceAll("\\)", "") 
        case "SNOW" => //formated_line(6) = line(3)
       // case "SNWD" => formated_line(7) = line(3)
        case _ => 
      } 
    }
    return formated_line.mkString(",")
  }
  
  //form the line by column         
  

}