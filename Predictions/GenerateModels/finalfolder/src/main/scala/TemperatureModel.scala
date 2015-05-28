package ModelFinal

import org.apache.spark.mllib.tree.model.DecisionTreeModel


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
import org.apache.spark.mllib.regression._

@SerialVersionUID(97239L) // useful for serialization purposes
class TemperatureModel(val firstModel:DecisionTreeModel, val secondModel:DecisionTreeModel) extends Serializable
    {
      /**
       * Predict the temperature for a given day based on the date and the 7 previous day measurements.
     * @param day the day of month (starting at 1)
     * @param month the month year (starting at 1)
     * @param meas1 the measurement of the 7th day before the wanted day of prediction
     * @param meas2 the measurement of the 6th day before the wanted day of prediction
     * @param meas3 the measurement of the 5th day before the wanted day of prediction
     * @param meas4 the measurement of the 4th day before the wanted day of prediction
     * @param meas5 the measurement of the 3rd day before the wanted day of prediction
     * @param meas6 the measurement of the 2sd day before the wanted day of prediction
     * @param meas7 the measurement of the 1st day before the wanted day of prediction
     * @return the measurement prediction
     * 
     * @see The temperature are expressed multiplied by 10: e.g. 6.1 degrees has to be mapped to 61.
     * @see The output temperature is a Double in raw degree: e.g a standard output could be 6.1 degree celsius (and not 61).
     */
    def predict(day:Int, month:Int, meas1:Double, meas2:Double, meas3:Double, meas4:Double, meas5:Double, meas6:Double, meas7:Double): Tuple3[Double, Double, Double] =
      {
        var currDayId=convert(day, month);
        var avgDay1:Double=firstModel.predict(Vectors.dense(currDayId));
        var predictedValueDay1 = secondModel.predict(Vectors.dense(avgDay1, meas1, meas2, meas3, meas4, meas5, meas6, meas7));
        
        currDayId=(currDayId+1)%366;
        var avgDay2:Double=firstModel.predict(Vectors.dense(currDayId));
        var predictedValueDay2 = secondModel.predict(Vectors.dense(avgDay2, meas2, meas3, meas4, meas5, meas6, meas7, predictedValueDay1));
        
        currDayId=(currDayId+1)%366;
        var avgDay3:Double=firstModel.predict(Vectors.dense(currDayId));
        var predictedValueDay3 = secondModel.predict(Vectors.dense(avgDay3, meas3, meas4, meas5, meas6, meas7, predictedValueDay1, predictedValueDay2));
        
        
        Tuple3(predictedValueDay1, predictedValueDay2, predictedValueDay3);
      } 
    private def convert(day:Int, month:Int):Int=
    {
      val nbreJour=List(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
      var pos=0;
      for(i <- Range(0, month-1))
      {
         pos += nbreJour(i); 
      }
      pos+(day-1);
    }
    }