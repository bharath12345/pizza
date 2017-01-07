import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "in.bharathwrites",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT",
      javaOptions += "-Xmx8G",
      fork in Test := true
    )),
    name := "Pizza",
    libraryDependencies += scalaTest % Test
  )
