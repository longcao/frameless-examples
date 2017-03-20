package examples

import org.apache.spark.sql.functions.avg
import org.apache.spark.sql.{ Dataset, SparkSession }

object DatasetExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate

    import spark.implicits._ // import default Encoders

    val artists: Dataset[Artist] = spark.createDataset(Artist.defaultArtists)

    try {
      artists
        .filter(_.age > 30)
        .agg(avg("age")).show
    } finally {
      spark.stop()
    }
  }
}

