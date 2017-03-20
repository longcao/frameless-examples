package examples

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RddExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate

    val artists: RDD[Artist] = spark.sparkContext.parallelize(Seq(
      Artist("Offset", 25),
      Artist("Kanye West", 39),
      Artist("Frank Ocean", 29),
      Artist("John Mayer", 39),
      Artist("Aretha Franklin", 74),
      Artist("Kendrick Lamar", 29),
      Artist("Carly Rae Jepsen", 31)))

    val (totalAge, totalCount) = artists
      .map(a => (a.age, 1))
      .reduce { case ((a1, c1), (a2, c2)) => (a1 + a2, c1 + c2) }

    println(s"Average age: ${totalAge.toDouble / totalCount.toDouble}")

    spark.stop()
  }
}
