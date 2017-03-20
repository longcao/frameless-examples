package examples

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{ DataFrame, SparkSession }

object DataFrameExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate

    val artists: DataFrame = spark.createDataFrame(Seq(
      Artist("Offset", 25),
      Artist("Kanye West", 39),
      Artist("Frank Ocean", 29),
      Artist("John Mayer", 39),
      Artist("Aretha Franklin", 74),
      Artist("Kendrick Lamar", 29),
      Artist("Carly Rae Jepsen", 31)))

    try {
      artists.agg(avg("age")).show
      artists.select("genre").show // throws an exception
    } finally {
      spark.stop()
    }
  }
}
