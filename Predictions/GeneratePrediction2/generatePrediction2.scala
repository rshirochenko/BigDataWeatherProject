package ModelFinal

import java.util.{Calendar}
import java.util.Date


import collection.JavaConversions._

import java.io.FileOutputStream
import java.io.ObjectOutputStream
import scala.Range
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.rdd.RDD
import java.io.ObjectInputStream
import java.io.FileInputStream
import java.io._
	object GeneratePreditionDataSet2Final
	{
		val sc = new SparkContext(new SparkConf().setAppName("TodayPrediction"))

		  def generateStringDate(x: Date  ): String = {
		  		val year = x.getYear() + 1900
				val month = (x.getMonth() +1)
				var  month_Str ="";
				if (month < 10) { month_Str  = "0".concat(month.toString()) } else { month_Str = month.toString()}

				val day = x.getDate()
// TODO Update this path and the call to the ile
		  year.toString().concat(month_Str.concat(day.toString()))
		  }
		  def main(args: Array[String]) {

		  	 // var fileList =  new File("/home/vinhmau/updata/stations/").listFiles.filter(_.isDirectory).map(_.getName)
   			 val rdd = sc.textFile("hdfs:///projects/bravvfings/dataset/stationslist/stations2.txt")
   			  val fileList = rdd.toArray.toList

   			 fileList.map(x => generatePrediction(x))
   			 //generatePrediction("USC00010425")
		  }
		  def generatePrediction(stationId: String )  {
		  	try { 

		  	val path = "/home/vinhmau/models2/" + stationId +"/"
// Used to generate dynamic dates
		  	  val today = Calendar.getInstance().getTime()
				//val day = today.getDate()
		  	  //val month = today.getMonth()
		  	   val date2 = generateStringDate(today) + "0600"
		  	   today.add(Calendar.DAY_OF_YEAR, -1);
		  	   val date1 = generateStringDate(today) +"0600"
		  	   


		  	//Load the two models needed for the prediction
		  	var decisionPressuretmp = new ObjectInputStream(new FileInputStream(path + "randomForestPressureModel.ser"))
			val decisionPressure = decisionPressuretmp.readObject().asInstanceOf[PressureModel]


			// Retrieve the data related to the current date.
			//val day =30;
			//val month = 1;

			//val date1 = "201404130600" ;
			//val date2 = "201404140600" ;
			val tempdata = sc.textFile("hdfs:///projects/bravvfings/dataset/ISD/" + stationId)
			val weather_data = tempdata.map(line => line.split((" "))).collect
			// Filter to keep only the data of the previous
						val dates = Array(date1,date2)

			val weather_data_filtered  = weather_data.filter ( x => dates.contains(x(1)) ).map( x => x)


			// TODO  What if some data are missing for one day ? (OutofBoundException)
			val day1 = weather_data_filtered.filter( x => x(1) == date1 ).flatMap( x => x)
			val day2 = weather_data_filtered.filter( x => x(1) == date2 ).flatMap( x => x)

			val press = decisionPressure.predict(day1(2).toDouble,day1(3).toDouble,day2(2).toDouble,day2(3).toDouble)
			
			val path2 = "/home/vinhmau/predictions2/" + stationId +"/"

 			val theDir = new File(path2);
    		if (!theDir.exists()) theDir.mkdir();

		      val writer = new PrintWriter(new File(path2 + "predPressure-"+date2+".csv"))

		      writer.write(press.toString())
		      writer.close()

		      


		}
		 catch {
           case _: Throwable =>
	}


		
		}



			//val TempModel = new TemperatureModel(decisionTreeMin, decisionTreeMax)

		
			 
	}

