package examples

case class Artist(name: String, age: Int)

object Artist {
  val defaultArtists: Seq[Artist] = Seq(
    Artist("Offset", 25),
    Artist("Kanye West", 39),
    Artist("Frank Ocean", 29),
    Artist("John Mayer", 39),
    Artist("Aretha Franklin", 74),
    Artist("Kendrick Lamar", 29),
    Artist("Carly Rae Jepsen", 31))
}
