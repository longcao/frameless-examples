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

      artists.select("genre").show // hmm, still throws an exception...
    } catch {
      case ex: Exception => ex.printStackTrace()
    } finally {
      spark.stop()
    }
  }
}

