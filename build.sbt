import Dependencies._
import org.scalatra.sbt._

name := "ZTM service"

version := "0.1.1"

scalaVersion := "2.10.3"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0" % "test",
  "com.typesafe.slick" %% "slick" % "2.0.0-RC1",
  "mysql" % "mysql-connector-java" % "5.1.28",
  "com.typesafe" % "config" % "1.0.2",
  "org.mockito" % "mockito-core" % "1.9.5"
) ++ jodaTime ++ macwire ++ logging ++ httpStack

ScalatraPlugin.scalatraSettings

// http://stackoverflow.com/questions/21435023/how-to-change-jdk-set-by-sbt-import-in-intellij-idea
javacOptions in Compile ++= Seq("-source", "1.7", "-target", "1.7")

