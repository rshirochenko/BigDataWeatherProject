package ModelFinal

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
import java.io.File
object GeneratePreditionDataSet1Final
{
	  def main(args: Array[String]) {
	  	val stationId = args(0);
	  	val path = "/home/vinhmau/updata/stations/" + stationId 
	  	var decisionTreeFileMin = new ObjectInputStream(new FileInputStream(+ "decisionTreeModelTMin.ser"))
		val decisionTreeMin = input.decisionTreeFileMin()

		var decisionTreeFileMax = new ObjectInputStream(new FileInputStream(+ "decisionTreeModelTMax.ser";))
		val decisionTreeMax = input.decisionTreeFileMax()

		val TempModel = new TemperatureModel(decisionTreeMin, decisionTreeMax)
	
		  }
}
