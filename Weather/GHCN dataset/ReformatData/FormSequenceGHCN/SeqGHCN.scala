import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._


object FormSeq {
	def main(args: Array[String]) {
	
		val conf = new SparkConf().setAppName("Form seq")
        	val sc = new SparkContext(new SparkConf().setAppName("SeqGHCN"))

		val file = sc.textFile("hdfs:///projects/bravvfings/dataset/ghcn/*.csv")
   		val lines = file.map(x=> (x.split(",")(0), x)).groupByKey
      		lines.mapValues(x => x.mkString(";")).saveAsSequenceFile("hdfs:///projects/bravvfings/dataset/ghcn_seq/")
	}
	
}
