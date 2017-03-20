lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "org.longcao",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "frameless-examples",
    libraryDependencies := Seq(
      "org.apache.spark"    %% "spark-core"          % "2.0.2",
      "org.apache.spark"    %% "spark-sql"           % "2.0.2",
      "io.github.adelbertc" %% "frameless-cats"      % "0.2.0",
      "io.github.adelbertc" %% "frameless-dataset"   % "0.2.0"
    )
  )
