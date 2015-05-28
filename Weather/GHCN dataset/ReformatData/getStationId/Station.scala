import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

//Filter the active station that was active during the 2015 year

object Station {
	def main(args: Array[String]) {
	
		val conf = new SparkConf().setAppName("Get station")
        val sc = new SparkContext(new SparkConf().setAppName("Station"))

        val file = sc.textFile("hdfs:///projects/bravvfings/dataset/ghcn/2015.csv")

		val lines = file.map(x => x.split(",")).filter{ x =>  (x(0) contains "US") && (x(1) contains "20150420")  }

		val tmin = lines.filter(x => x(2) contains "TMIN").map( x => (x(0),1) )
		val prcp = lines.filter(x => x(2) contains "PRCP").map( x => (x(0),1) )

		val active_stations = tmin.join(prcp).map(_._1)

		active_stations.coalesce(1, true).saveAsTextFile("hdfs:///projects/bravvfings/active_stations_id")
	}
	
}
