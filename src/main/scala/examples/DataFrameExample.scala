package examples

import org.apache.spark.sql.functions.avg
import org.apache.spark.sql.{ DataFrame, SparkSession }

object DataFrameExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate

    val artists: DataFrame = spark.createDataFrame(Artist.defaultArtists)

    try {
      artists.agg(avg("age")).show
      artists.select("genre").show // throws an exception
    } finally {
      spark.stop()
    }
  }
}
