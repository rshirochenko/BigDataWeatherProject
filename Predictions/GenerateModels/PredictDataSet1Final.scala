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
import scala.io.Source

object PredictDataSet1Final
{ 
    val sc = new SparkContext(new SparkConf().setAppName("TemperatureAnalysis"))
    
    def listeMobile7forDayTMin(list: List[Tuple5[Double,Double,Double,Double,Double]], nextDayId:Int, decisionTreeModelAvg:DecisionTreeModel) : List[LabeledPoint] =
      {
        var templist=list;
        val size=nextDayId-1;
        var output:List[LabeledPoint]=Range(0, list.size-size).map(x => 
          {
           var subList=templist.take(size+1 /*on va dropper le premier elt*/);
           templist=templist.drop(1);
           new LabeledPoint(subList(size)._3, Vectors.dense(
               
               (
                   decisionTreeModelAvg.predict(Vectors.dense(subList(size)._2)) // la prediction "moyenne"
               ::
                 subList.dropRight(nextDayId-7)/*drop le label*/.map(tuple5 => tuple5._3)
                 ).toArray
               ));
          }).toList 
          
          output
      }
    def listeMobile7forDayTMax(list: List[Tuple5[Double,Double,Double,Double,Double]], nextDayId:Int, decisionTreeModelAvg:DecisionTreeModel) : List[LabeledPoint] =
      {
        var templist=list;
        val size=nextDayId-1;
        var output:List[LabeledPoint]=Range(0, list.size-size).map(x => 
          {
           var subList=templist.take(size+1 /*on va dropper le premier elt*/);
           templist=templist.drop(1);
           new LabeledPoint(subList(size)._4, Vectors.dense(
               
               (
                   decisionTreeModelAvg.predict(Vectors.dense(subList(size)._2)) // la prediction "moyenne"
               ::
                 subList.dropRight(nextDayId-7)/*drop le label*/.map(tuple5 => tuple5._4)
                 ).toArray
               ));
          }).toList 
          
          output
      }
    def listeMobile7forDayPrec(list: List[Tuple5[Double,Double,Double,Double,Double]], nextDayId:Int, decisionTreeModelAvg:DecisionTreeModel) : List[LabeledPoint] =
      {
        var templist=list;
        val size=nextDayId-1;
        var output:List[LabeledPoint]=Range(0, list.size-size).map(x => 
          {
           var subList=templist.take(size+1 /*on va dropper le premier elt*/);
           templist=templist.drop(1);
           new LabeledPoint(subList(size)._5, Vectors.dense(
               
               (
                   decisionTreeModelAvg.predict(Vectors.dense(subList(size)._2)) // la prediction "moyenne"
               ::
                 subList.dropRight(nextDayId-7)/*drop le label*/.map(tuple5 => tuple5._5)
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

  def main(args: Array[String]) {
   
//
   // hadoop fs -ls "hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/" | sed '1d;s/  */ /g' | cut -d\  -f8 | xargs -n 1 basename > derp.txt

   // var fileList =  new File("/home/vinhmau/ML/derp.txt").listFiles.filter(_.isDirectory).map(_.getName)
    //hadoop fs -ls "hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/" | sed '1d;s/  */ /g' | cut -d\  -f8 | xargs -n 1 basename > derp.txt
   // val fileList = scala.io.Source.fromFile("/home/vinhmau/ML/derp.txt").mkString
   // val sc = new SparkContext(new SparkConf().setAppName("TemperatureAnalysis"))
    //var fileList =  new File("hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/*").listFiles.filter(_.isDirectory).map(_.getName)
   // var fileList =  new File("hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/").listFiles.filter(_.isDirectory).map(_.getName)
  // val s = Source.fromFile("hdfs://projects/bravvfings/dataset/ghcn_by_active_station/").getLines() 

    //hadoop fs -ls "hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/" | sed '1d;s/  */ /g' | cut -d\  -f8 | xargs -n 1 basename > derp2.txt

//for(line <- Source.fromFile("/home/vinhmau/ML/stathadoop").getLines())
  //println(line)
  val rdd = sc.textFile("hdfs:///projects/bravvfings/dataset/stationslist/stations.txt")
val fileList = rdd.toArray.toList

    fileList.map(x => generateModel(x))
  //generateModel("USC00020288",sc)
       sc.stop()

  }

  def generateModel(stationId: String ) {
    try {
      val ceckfolder = "/home/vinhmau/models/model" + stationId+"/"; 
      
    

    val theDir2 = new File(ceckfolder);
   // if (!theDir2.exists()) {

       val inputFolder="hdfs:///projects/bravvfings/dataset/ghcn_by_active_station/" + stationId+"/" //dossier de la station
    //val inputFile=inputFolder+"data_one_station.csv"; // fichier de la station
    val inputFile=inputFolder
    //val sc = new SparkContext(new SparkConf().setAppName("TemperatureAnalysis").setMaster("local[2]"))
    //val lines = sc.textFile("hdfs:///datasets/clusterlogs/*")
    val lines = sc.textFile(inputFile)

    
    
    val parsedData:RDD[(Double, Double, Double, Double, Double)] = lines.flatMap(ligne => 
      {
        try
        {
          val split=ligne.split(",");//warning separator for first dataset is a COMMA (",")
          val date = split(1);
          val year= date.substring(0, 4).toInt;
          val month= date.substring(4, 6).toInt;
          val day= date.substring(6, 8).toInt;
          
          var dateSur366 = convert(day,month).toDouble
          List(new Tuple5(year.toDouble, dateSur366, split(2).toDouble, split(3).toDouble, split(4).toDouble))
          /*year, day_in_year, tmin, tmax, precipitation_leve*/
        }
        catch
        {
          case e: Exception => List()//la première ligne parsée
        }
      }
      ).cache().sortBy(x=> x._1*366+x._2, true, 1);
    
      

      
      
     
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "variance"
    val maxDepth = 30
    val maxBins = 365/7
    
    
    var dataTMinAvg:RDD[LabeledPoint]= sc.parallelize(parsedData.toArray().toList.map
      {
             x => new LabeledPoint(x._2,  Vectors.dense(x._3));
      });
    val decisionTreeModelTMinAvg = DecisionTree.trainRegressor(dataTMinAvg, categoricalFeaturesInfo, impurity, maxDepth, maxBins)
    var dataTMin:RDD[LabeledPoint]= sc.parallelize(listeMobile7forDayTMin(parsedData.toArray().toList, 8, decisionTreeModelTMinAvg));
    
    val decisionTreeModelTMin = DecisionTree.trainRegressor(dataTMin, categoricalFeaturesInfo, impurity, maxDepth, maxBins)
    
    
    
    
      
    var dataTMaxAvg:RDD[LabeledPoint]= sc.parallelize(parsedData.toArray().toList.map
      {
             x => new LabeledPoint(x._2,  Vectors.dense(x._4));
      });
    val decisionTreeModelTMaxAvg = DecisionTree.trainRegressor(dataTMaxAvg, categoricalFeaturesInfo, impurity, maxDepth, maxBins)
    var dataTMax:RDD[LabeledPoint]= sc.parallelize(listeMobile7forDayTMax(parsedData.toArray().toList, 8, decisionTreeModelTMaxAvg));
    
    val decisionTreeModelTMax = DecisionTree.trainRegressor(dataTMax, categoricalFeaturesInfo, impurity, maxDepth, maxBins)
    
    
    
    
    
    var tminMergedModel = new TemperatureModel(decisionTreeModelTMinAvg, decisionTreeModelTMin);
    var tmaxMergedModel = new TemperatureModel(decisionTreeModelTMaxAvg, decisionTreeModelTMax);
    
    val outputfolder = "/home/vinhmau/models/model" + stationId+"/"; 
      
    

    val theDir = new File(outputfolder);
    if (!theDir.exists()) theDir.mkdir();


    val oos1 = new ObjectOutputStream(new FileOutputStream(outputfolder+"decisionTreeModelTMin.ser"))
    oos1.writeObject(tminMergedModel)
    oos1.close
    
    
    val oos2 = new ObjectOutputStream(new FileOutputStream(outputfolder+"decisionTreeModelTMax.ser"))
    oos2.writeObject(tmaxMergedModel)
    oos2.close
    println("written to "+outputfolder+"decisionTreeModelTMax.ser")
    
    
    
    
   /*
    
    //prediction sample for the Temperature MAX
    
    val ois = new ObjectInputStream(new FileInputStream("/home/vinhmau/updata/stations/USC00281335"+"decisionTreeModelTMax.ser"))
    var modelTMAX:TemperatureModel = ois.readObject().asInstanceOf[TemperatureModel];
    ois.close
    println("the predicted TMAX for next 3 days are "+
    modelTMAX.predict(day, month, meas1, meas2, meas3, meas4, meas5, meas6, meas7))
    
     */
    
    }
    
  } catch
        {
           case _: Throwable =>
        }
//}
}

