import sbt._

object Resolvers {
  val customResolvers = Seq(
    "spray repo" at "http://repo.spray.io"
  )
}

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

  // Spray versions explained http://spray.io/project-info/current-versions/
  val sprayVersion = "1.2.0"
  val spray = Seq(
    "io.spray" % "spray-can" % sprayVersion,
    "io.spray" % "spray-routing" % sprayVersion,
    "io.spray" %% "spray-json" % "1.2.5",
    "io.spray" % "spray-testkit" % sprayVersion % "test"
  )

  val akkaVersion = "2.2.4"
  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  )

}
