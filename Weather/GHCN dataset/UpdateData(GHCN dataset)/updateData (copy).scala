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
val curdat = "20150501"
// Filter updatefile to only keep the current date , around 100k line

val weather_data = weather_data_file.map(line => line.split(","))
val weather_data_filtered  = weather_data.filter ( x => x(1) == curdat ).map( x => x)

// Array[String] of each of our stationsid
//TODO Change this path
var fileList =  new File("/home/vinhmau/updata/stations/").listFiles.filter(_.isDirectory).map(_.getName)
// Now we only got all the lines we have to add .map(str => (str(0),str(1), str(2), str(3)) )
 val linestoAdd = weather_data_filtered.filter ( x => fileList.contains(x(0)) ).map(x=>(x(0),x(1),x(2),x(3)))

linestoAdd.coalesce(1,true).saveAsTextFile("file:///home/vinhmau/updata2/newdata.csv")

val file = sc.textFile("file:///home/vinhmau/updata2/newdata.csv")
val lines = file.map(x=> (x.split(",")(0), x)).groupByKey
 lines.mapValues(x => x.mkString(";")).saveAsSequenceFile("file:///home/vinhmau/updata2/newdataparsed.csv")


val data = sc.sequenceFile[String, String]("file:///home/vinhmau/updata2/newdataparsed.csv")

      //Open the stations file
     
      //Filter weather_stations and save as sequence file (TODO: separate this action in different file)
      //val us_stations = data.map(x => (x._1,reformatByColumn(x._2))).filter(x => x._1 contains "US")
      //us_stations.saveAsSequenceFile("/projects/bravvfings/dataset/ghcn_seq_column")
      
      //Saving folders by stations r
      val formatteddata = data.map(x => (x._1,reformatByColumn(x._2))) // PROBLEM AT THIS STEP
      // WORKING VERSION IS AFTER THE +++++++ comment

      //ReformatByColumn(stringsFromOneStation:String)
      val derupu = data.map(x => reformatByColumn(x._2) )
      val derp = data.map(x => x._2 )
      // Array((USC00281335,20150501,TMAX,90);(USC00281335,20150501,TMIN,44);(USC00281335,20150501,PRCP,248);(USC00281335,20150501,SNWD,40), (USC00282644,20150501,TMAX,0);(USC00282644,20150501,TMIN,-133);(USC00282644,20150501,TOBS,-39);(USC00282644,20150501,PRCP,0);(USC00282644,20150501,SNOW,0), (USC00282768,20150501,TMAX,42);(USC00282768,20150501,TMIN,-167);(USC00282768,20150501,TOBS,-67);(USC00282768,20150501,PRCP,128);(USC00282768,20150501,SNOW,0);(USC00282768,20150501,SNWD,0), (USC00282023,20150501,TMAX,-83);(USC00282023,20150501,TMIN,-183);(USC00282023,20150501,TOBS,-122);(USC00282023,20150501,PRCP,0);(USC00282023,20150501,SNOW,0);(USC00282023,20150501,SNWD,0))
      derp
      var lines = derp.map(x => x.split(";"))

    var lines_group_by_date = lines.map(x=> x.map(_.split(",")).groupBy(x => x(1)))

   var lines_group_by_date_sorted = lines_group_by_date.map(x => ListMap(x.toSeq.sortBy(_._1):_*))

  var result = lines_group_by_date_sorted.map(x => x.map{group => formLine(group._2)})

fileList.foreach{ station_id =>
        result.filter(s => s._1 contains station_id).flatMap(x => x._2.split(";")).coalesce(1,true).saveAsTextFile("file:///home/vinhmau/updata2/derp3/"+station_id)
      }

   //    var lines = stringsFromOneStation.split(";")
    



      fileList.foreach{ station_id =>
        formatteddata.filter(s => s._1 contains station_id).flatMap(x => x._2.split(";")).coalesce(1,true).saveAsTextFile("file:///home/vinhmau/updata2/derp2/"+station_id)
      }

// Same code as ReformedData to handle the "cleaning/split?"
// Then, again, splitting problem, except that the size of our file is "small"

// Merge in the (correct) StationId folder

//+++++++++++++++++++++++++++++++++++++++++++++++++=


 val formatteddata = data.map(x => x._1,reformatByColumn(x._2)) // PROBLEM AT THIS STEP
      //ReformatByColumn(stringsFromOneStation:String)
      val derupu = data.map(x => reformatByColumn(x._2) )
      val derp = data.map(x =>(x._1,  x._2 ))
      // Array((USC00281335,20150501,TMAX,90);(USC00281335,20150501,TMIN,44);(USC00281335,20150501,PRCP,248);(USC00281335,20150501,SNWD,40), (USC00282644,20150501,TMAX,0);(USC00282644,20150501,TMIN,-133);(USC00282644,20150501,TOBS,-39);(USC00282644,20150501,PRCP,0);(USC00282644,20150501,SNOW,0), (USC00282768,20150501,TMAX,42);(USC00282768,20150501,TMIN,-167);(USC00282768,20150501,TOBS,-67);(USC00282768,20150501,PRCP,128);(USC00282768,20150501,SNOW,0);(USC00282768,20150501,SNWD,0), (USC00282023,20150501,TMAX,-83);(USC00282023,20150501,TMIN,-183);(USC00282023,20150501,TOBS,-122);(USC00282023,20150501,PRCP,0);(USC00282023,20150501,SNOW,0);(USC00282023,20150501,SNWD,0))
      derp
      var lines = derp.map(x => (x._1 , x._2.split(";")))

    var lines_group_by_date = lines.map(x=> (x._1,x._2.map(_.split(",")).groupBy(x => x(1)))

   var lines_group_by_date_sorted = lines_group_by_date.map(x => (x._1,ListMap(x._2.toSeq.sortBy(_._1):_*)))

  var result = lines_group_by_date_sorted.map(x => (x._1, x._2.map{group => formLine(group._2)}))
  var final_result = result.map(x => x +";");

fileList.foreach{ station_id =>
    //val  station_id = "USC00281335"
        val tempresult =  result.filter(s => s._1 contains station_id).flatMap(x => x._2)//.coalesce(1,true) .saveAsTextFile("file:///home/vinhmau/updata2/derp4/"+station_id)
      val oldresult= sc.textFile("file:///home/vinhmau//updata/stations/" + station_id)
      val finalfile = oldresult.union(tempresult);
      finalfile.coalesce(1,true) .saveAsTextFile("file:///home/vinhmau/updata2/derp6/"+station_id)
      }

   //    var lines = stringsFromOneStation.split(";"
    // CRON TASK + shell script
    // Download the new file
    // run the spark script
    // delte the original stations folder
    // copy the updated folder in the original folder


  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++




  var result = lines_group_by_date_sorted.map(x => x.map{group => formLine(group._2)})
//(List( (USC00281335 , 20150501, , ,248))
//List((USC00281335,20150501,44),,248))

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
  def reformatByColumn(stringsFromOneStation:String):String = {
    var lines = stringsFromOneStation.split(";")
    var lines_group_by_date = lines.map(_.split(",")).groupBy(x => x(1))
    var lines_group_by_date_sorted = ListMap(lines_group_by_date.toSeq.sortBy(_._1):_*)
    
    var result = lines_group_by_date_sorted.map{group => formLine(group._2)} 
    return result.mkString(";")
  }