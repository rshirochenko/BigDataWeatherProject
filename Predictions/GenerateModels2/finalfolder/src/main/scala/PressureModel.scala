package ModelFinal

import java.io.FileOutputStream
import java.io.ObjectOutputStream
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.regression._
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel

@SerialVersionUID(97236L) // useful for serialization purposes
class PressureModel(val pressureModel:RandomForestModel) extends Serializable
    {

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

    /**
     * Predict the pressure given the last 2 days measurements
     * @param pressure1 the measurement of the pressure 2 days ago
     * @param precipitation1 the measurement of the precipitation 2 day ago
     * @param pressure2 the measurement of the pressure 1 days ago
     * @param precipitation2 the measurement of the precipitation 1 day ago
     * @return the measurement prediction
     */
    def predict(pressure1:Double, precipitation1:Double, pressure2:Double, precipitation2:Double): Double =
      {
        var predictedValueDay = pressureModel.predict(Vectors.dense(barometer(pressure1), precipitation1, barometer(pressure2), precipitation2));

        predictedValueDay
      } 

    }