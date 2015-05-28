import java.io.StringReader
import java.io.StringWriter

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

//import au.com.bytecode.opencsv.CSVReader
//import au.com.bytecode.opencsv.CSVWriter

import collection.JavaConversions._
object ReformatData {
  def main(args: Array[String]) {
    	val sc = new SparkContext(new SparkConf().setAppName("reformatOpenStreetData"))
    	// hdfs://projects/bravvfings/dataset/openstreet/north-america-latest.osm.pbf
///projects/bravvfings/dataset/openstreet/cleandata/placesRAW.csv
    //	val openStreet_data_file = sc.textFile("file:///home/vinhmau/openstreet/places.csv")
    val openStreet_data_file = sc.textFile("hdfs:///projects/bravvfings/dataset/openstreet/cleandata/placesRAW.csv")
      	val openStreet_data =( openStreet_data_file.mapPartitions(_.drop(1))).map(line => line.split(",",-1))
     .
        val clean_lines = openStreet_data.filter( str => (str(8).nonEmpty || str(9).nonEmpty ||str(10).nonEmpty ))

		//val lines = clean_lines.map(str => (str(0),str(1), str(2), str(3),str(4),str(5),str(6),str(7),str(8),str(9),str(10),str(11),str(12)+";"+str(13)+";"+str(14)  ) )
		val lines = clean_lines.map(str => (str(0),str(1), str(2), str(3),str(4),str(5),str(6),str(7),,str(8)+";"+str(9)+";"+str(10)  ) )
 		// Produce Empty Files?
 		//ines.coalesce(1, true).saveAsTextFile("file:///home/vinhmau/openstreet/cleanplaces14.csv")
    lines.coalesce(1, true).saveAsTextFile("hdfs:///projects/bravvfings/dataset/openstreet/cleandata/places.csv")
// "@id @lat @lon name addr:full wikipedia website image tourism amenity sport"
//   0    1    2    3    4         5          6      7      8        9     10    
   }
}