package examples

import frameless._
import frameless.functions.aggregate.{ avg, count }

import org.apache.spark.sql.{ Dataset, SparkSession }

case class AgeCount(age: Int, count: Long)

object TypedDatasetExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate
    implicit val sqlContext = spark.sqlContext // required for frameless

    val artists: TypedDataset[Artist] = TypedDataset.create(Artist.defaultArtists)

    artists
      .filter(_.age > 30)
      .select(avg(artists('age))).show().run // typechecked column name!

    val nameCol = artists('name) // can bind TypedColumn to a val for convenience
    artists
      .filter(_.age > 30)
      .select(nameCol)
      .show().run

    shapeless.test.illTyped { """artists.select(artists('blah))""" } // shouldn't compile

    artists
      .groupBy(artists('age))
      .agg(count(artists('age)))
      .as[AgeCount] // compile-time .as!
      .filter(_.count > 1)
      .show().run

    spark.stop()
  }
}

