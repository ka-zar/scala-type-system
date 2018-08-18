name := "scala-type-system"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions in ThisBuild ++= Seq("-deprecation", "-feature")

libraryDependencies in ThisBuild ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalactic" %% "scalactic" % "3.0.4",
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)
