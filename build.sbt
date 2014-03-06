import Dependencies._
import Resolvers._

name := "ZTM service"

version := "0.1.1"

scalaVersion := "2.10.3"

resolvers += customResolvers

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0" % "test",
  "com.typesafe.slick" %% "slick" % "2.0.0-RC1",
  "mysql" % "mysql-connector-java" % "5.1.28",
  "com.typesafe" % "config" % "1.0.2"
) ++ jodaTime ++ macwire ++ logging ++ akka ++ spray

// http://stackoverflow.com/questions/21435023/how-to-change-jdk-set-by-sbt-import-in-intellij-idea
javacOptions in Compile ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  //  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)
