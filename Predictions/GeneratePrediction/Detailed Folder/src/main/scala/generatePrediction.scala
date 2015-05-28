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
	object GeneratePreditionDataSet1Final
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
   			  val rdd = sc.textFile("hdfs:///projects/bravvfings/dataset/stationslist/stations.txt")
   			  val fileList = rdd.toArray.toList

   			 fileList.map(x => generatePrediction(x))
   			 //generatePrediction("USC00010425")
		  }
		  def generatePrediction(stationId: String )  {
		  	try { 

		  	val path = "/home/vinhmau/models/model" + stationId +"/"
// Used to generate dynamic dates
		  	  //val today = Calendar.getInstance().getTime()
		  	   //val date7 = generateStringDate(today)
		  	   //today.add(Calendar.DAY_OF_YEAR, -1);
		  	   //val date6 = generateStringDate(today)
		  	   //today.add(Calendar.DAY_OF_YEAR, -1);
		  	   //val date5 = generateStringDate(today)
		  	   //today.add(Calendar.DAY_OF_YEAR, -1);
		  	   //val date4 = generateStringDate(today)
		  	   //today.add(Calendar.DAY_OF_YEAR, -1);
		  	   //val date3 = generateStringDate(today)
		  	   //today.add(Calendar.DAY_OF_YEAR, -1);
		  	   //val date2 = generateStringDate(today)
		  	   //today.add(Calendar.DAY_OF_YEAR, -1);
		  	   //val date1 = generateStringDate(today)


		  	//Load the two models needed for the prediction
		  	var decisionTreeFileMin = new ObjectInputStream(new FileInputStream(path + "decisionTreeModelTMin.ser"))
			val decisionTreeMin = decisionTreeFileMin.readObject().asInstanceOf[TemperatureModel]



			var decisionTreeFileMax = new ObjectInputStream(new FileInputStream(path + "decisionTreeModelTMax.ser"))
			val decisionTreeMax = decisionTreeFileMax.readObject().asInstanceOf[TemperatureModel]
			// Retrieve the data related to the current date.
			val day =30;
			val month = 1;

			val date1 = "20140924" ;
			val date2 = "20140925" ;
			val date3 = "20140926" ;
			val date4 = "20140927" ;
			val date5 = "20140928" ;
			val date6 = "20140929" ;
			val date7 = "20140930" ;
			val dates = Array(date1,date2,date3,date4,date5,date6,date7)
			val tempdata = sc.textFile("hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/" + stationId)
			val weather_data = tempdata.map(line => line.split(",")).collect
			// Filter to keep only the data of the previous
			val weather_data_filtered  = weather_data.filter ( x => dates.contains(x(1)) ).map( x => x)


			// TODO  What if some data are missing for one day ? (OutofBoundException)
			val day1 = weather_data_filtered.filter( x => x(1) == date1 ).flatMap( x => x)
			val day2 = weather_data_filtered.filter( x => x(1) == date2 ).flatMap( x => x)
			val day3 = weather_data_filtered.filter( x => x(1) == date3 ).flatMap( x => x)
			val day4 = weather_data_filtered.filter( x => x(1) == date4 ).flatMap( x => x)
			val day5 = weather_data_filtered.filter( x => x(1) == date5 ).flatMap( x => x)
			val day6 = weather_data_filtered.filter( x => x(1) == date6 ).flatMap( x => x)
			val day7 = weather_data_filtered.filter( x => x(1) == date7 ).flatMap( x => x)

			val tminpred = decisionTreeMin.predict(day,month,day1(2).toDouble,day2(2).toDouble,day3(2).toDouble,day4(2).toDouble,day5(2).toDouble,day6(2).toDouble,day7(2).toDouble )
			val tmaxpred = decisionTreeMax.predict(day,month,day1(3).toDouble,day2(3).toDouble,day3(3).toDouble,day4(3).toDouble,day5(3).toDouble,day6(3).toDouble,day7(3).toDouble )
			
			val path2 = "/home/vinhmau/predictions/" + stationId +"/"

 			val theDir = new File(path2);
    		if (!theDir.exists()) theDir.mkdir();

		      val writer = new PrintWriter(new File(path2 + "predtmin-"+date7+".csv"))

		      writer.write("("+tminpred._1.toString()+"," + tminpred._2.toString()+","+ tminpred._3.toString()+")" )
		      writer.close()

		      val writer2 = new PrintWriter(new File(path2 + "predtmax-"+date7+".csv"))

		      writer2.write("("+tmaxpred._1.toString()+"," + tmaxpred._2.toString()+","+ tmaxpred._3.toString()+")" )
		      writer2.close()


		}
		 catch {
		case e: Exception => { }
	}


		
		}



			//val TempModel = new TemperatureModel(decisionTreeMin, decisionTreeMax)

		
			 
	}

