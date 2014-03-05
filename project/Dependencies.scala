import sbt._

object Dependencies {

  // See http://stackoverflow.com/questions/13856266/class-broken-error-with-joda-time-using-scala
  val jodaTime = Seq(
    "joda-time" % "joda-time" % "2.1",
    "org.joda" % "joda-convert" % "1.2")

  val macwire = Seq(
    "com.softwaremill.macwire" %% "macros" % "0.5",
    "com.softwaremill.macwire" %% "runtime" % "0.5"
  )

  val logging = Seq(
    "org.slf4j" % "slf4j-simple" % "1.7.6",
    "com.typesafe" %% "scalalogging-slf4j" % "1.0.1"
  )

}