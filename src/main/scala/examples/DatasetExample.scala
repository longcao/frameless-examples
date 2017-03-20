package examples

import org.apache.spark.sql.functions.avg
import org.apache.spark.sql.{ Dataset, SparkSession }

object DatasetExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate

    import spark.implicits._ // import default Encoders

    val artists: Dataset[Artist] = spark.createDataset(Seq(
      Artist("Offset", 25),
      Artist("Kanye West", 39),
      Artist("Frank Ocean", 29),
      Artist("John Mayer", 39),
      Artist("Aretha Franklin", 74),
      Artist("Kendrick Lamar", 29),
      Artist("Carly Rae Jepsen", 31)))

    try {
      artists
        .filter(_.age > 30)
        .agg(avg("age")).show
    } finally {
      spark.stop()
    }
  }
}

