import Dependencies._

name := "ZTM service"

version := "0.1.1"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0" % "test",
  "com.typesafe.slick" %% "slick" % "2.0.0-RC1",
  "mysql" % "mysql-connector-java" % "5.1.28",
  "com.typesafe" % "config" % "1.0.2"
) ++ jodaTime ++ macwire ++ logging
