package ModelFinal

import java.io.FileOutputStream
import java.io.ObjectOutputStream
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.regression._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import java.io.ObjectInputStream
import java.io.FileInputStream
import java.io.File
import scala.io.Source

object PredictDataSet2Final
{
      val sc = new SparkContext(new SparkConf().setAppName("PressureModel").setMaster("local[2]"))

  def barometer(pression:Double):Int=
  {
    if(pression<=980)
      0
    else if(980 < pression && pression <= 1000)
      1
    else if(1000 < pression && pression <= 1025)
      2
    else if(1025 < pression && pression <= 1040)
      3
    else
      4
  }
     
    def listeMobileForPressure(list: List[Tuple4[Double,Double,Double,Double]], amountOfPrevDays:Int, labelFeatureTransform:Double=>Double):List[LabeledPoint]=
      {
        var templist=list;
        var output:List[LabeledPoint]=Range(0, list.size-amountOfPrevDays).map(x => 
          {
           var subList=templist.take(amountOfPrevDays+1 /*on va dropper le premier elt*/);
           templist=templist.drop(1);
           new LabeledPoint(labelFeatureTransform(subList(amountOfPrevDays)._3), Vectors.dense(
               (
                 (
                   subList.take(amountOfPrevDays).map(tuple3 => tuple3._3)
                 )               
                 ++
                 (
                   subList.take(amountOfPrevDays).map(tuple3 => tuple3._4)
                 )
               ).toArray
               ));
          }).toList 
          
          output
      }
        
    def convert(day:Int, month:Int):Int=
    {
      val nbreJour=List(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31); // on force le mapping du 29 février
      var pos=0;
      for(i <- Range(0, month-1))
      {
         pos += nbreJour(i); 
      }
      pos+(day-1);
    }
    
    def main(args: Array[String])
    {

    
    //val octaveFolderName="/home/darkstar/Documents/BigDataProject/octave/"

    
    //val lines = sc.textFile("hdfs:///datasets/clusterlogs/*")
  val rdd = sc.textFile("hdfs:///projects/bravvfings/dataset/stationslist/stations2.txt")
val fileList = rdd.toArray.toList

    fileList.map(x => generateModel(x)) 
    sc.stop()
       }
    def generateModel(stationId : String) {
      try {
      val inputFolder="hdfs:///projects/bravvfings/dataset/ISD/" +stationId +"/";
      val inputFile=inputFolder+stationId+"_final.out";
        val lines = sc.textFile(inputFile)

    val parsedData:RDD[(Double, Double, Double,Double)] = lines.flatMap(ligne => 
      {
        try
        {
          var ligne2= ligne.replace("  ", " ").replace("  ", " ")
          val split=ligne2.split(" ");//warning separator for second dataset is a SPACE (" ")
          val date = split(1);
          val year= date.substring(0, 4).toInt;
          val month= date.substring(4, 6).toInt;
          val day= date.substring(6, 8).toInt;
          
          val pressure = split(2).replace(",", ".").toLowerCase().replace("t", "")
          val precipitation = split(3).replace(",", ".").toLowerCase().replace("t", "")
          
          var dateSur366 = convert(day,month).toDouble
          
          List(new Tuple4(year.toDouble, dateSur366, pressure.toDouble, precipitation.toDouble))
          /*annee, jour_in_year, pressure, prcp*/
        }
        catch
        {
          case e: Exception =>
            {
            System.err.println("Unable to parse the line ["+ligne+"]");
              List()//la première ligne parsée
            }
        }
      }
      ).cache().sortBy(x=> x._1*366+x._2, true, 1);
    
    val numClasses = 5
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 5 // Use more in practice.
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"
    val maxDepth = 5;
    val maxBins = 5;

    var inputData:RDD[LabeledPoint]= sc.parallelize(listeMobileForPressure(parsedData.toArray().toList, 1, pressure => barometer(pressure)));
    val pressureModel = RandomForest.trainClassifier(inputData, numClasses, categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

    var serPressureModel = new PressureModel(pressureModel);
    val outputfolder = "/home/vinhmau/models2/"+stationId+"/"
    val theDir = new File(outputfolder);
    if (!theDir.exists()) theDir.mkdir();

    val yourFile = new File(outputfolder+"randomForestPressureModel.ser");
if(!yourFile.exists()) {
    yourFile.createNewFile();
} 

    
    val oos = new ObjectOutputStream(new FileOutputStream(outputfolder+"randomForestPressureModel.ser"))
    oos.writeObject(serPressureModel)
    oos.close
      } catch
        {
           case _: Throwable =>
        }
    
  }
}


