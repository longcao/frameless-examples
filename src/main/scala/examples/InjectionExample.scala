package examples

import frameless._

import org.apache.spark.sql.{ Dataset, SparkSession }

import shapeless.test.illTyped

sealed abstract class Genre
object Genre {
  case object HipHop extends Genre
  case object RnB    extends Genre
  case object Soul   extends Genre
  case object Pop    extends Genre
  case object Rock   extends Genre
}

case class ArtistWithGenre(artist: Artist, genre: Genre)

object InjectionExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate
    implicit val sqlContext = spark.sqlContext // required for frameless

    // won't compile:
    // could not find implicit value for parameter encoder: frameless.TypedEncoder[examples.ArtistWithGenre]
    illTyped { """
    val artistsWithGenre: TypedDataset[ArtistWithGenre] = TypedDataset.create(Seq(
      ArtistWithGenre(Artist("Offset",            25), Genre.HipHop),
      ArtistWithGenre(Artist("Kanye West",        39), Genre.HipHop),
      ArtistWithGenre(Artist("Frank Ocean",       29), Genre.RnB),
      ArtistWithGenre(Artist("John Mayer",        39), Genre.Rock),
      ArtistWithGenre(Artist("Aretha Franklin",   74), Genre.Soul),
      ArtistWithGenre(Artist("Kendrick Lamar",    29), Genre.HipHop),
      ArtistWithGenre(Artist("Carly Rae Jepsen",  31), Genre.Pop)))
    """ }

    // define an implicit Injection and frameless will use it to encode
    implicit val genreInjection = new Injection[Genre, Int] {
      def apply(genre: Genre): Int = genre match {
        case Genre.HipHop => 1
        case Genre.RnB    => 2
        case Genre.Soul   => 3
        case Genre.Pop    => 4
        case Genre.Rock   => 5
      }

      def invert(i: Int): Genre = i match {
        case 1 => Genre.HipHop
        case 2 => Genre.RnB
        case 3 => Genre.Soul
        case 4 => Genre.Pop
        case 5 => Genre.Rock
      }
    }

    // Compiles!
    val artistsWithGenre: TypedDataset[ArtistWithGenre] = TypedDataset.create(Seq(
      ArtistWithGenre(Artist("Offset",            25), Genre.HipHop),
      ArtistWithGenre(Artist("Kanye West",        39), Genre.HipHop),
      ArtistWithGenre(Artist("Frank Ocean",       29), Genre.RnB),
      ArtistWithGenre(Artist("John Mayer",        39), Genre.Rock),
      ArtistWithGenre(Artist("Aretha Franklin",   74), Genre.Soul),
      ArtistWithGenre(Artist("Kendrick Lamar",    29), Genre.HipHop),
      ArtistWithGenre(Artist("Carly Rae Jepsen",  31), Genre.Pop)))

    artistsWithGenre.filter(_.genre == Genre.HipHop).show().run

    spark.stop()
  }
}


