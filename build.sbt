ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"


libraryDependencies  ++= Seq(
  "org.scalanlp" %% "breeze" % "1.2",
  "org.scalanlp" %% "breeze-natives" % "1.2",
  "org.scalanlp" %% "breeze-viz" % "1.2"
)

lazy val root = (project in file("."))
  .settings(
    name := "MLBD_HW3"
  )