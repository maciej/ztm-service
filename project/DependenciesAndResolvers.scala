import sbt._

object Resolvers {
  val customResolvers = Seq(
    "SoftwareMill Public Releases" at "https://nexus.softwaremill.com/content/repositories/releases/"
  )
}

object Dependencies {
  val baseDependencies = Seq(
    "org.scalatest" %% "scalatest" % "2.0" % "test",
    "com.typesafe.slick" %% "slick" % "2.0.0-RC1",
    "mysql" % "mysql-connector-java" % "5.1.28",
    "com.typesafe" % "config" % "1.0.2",
    "org.mockito" % "mockito-core" % "1.9.5" % "test"
  )

  // See http://stackoverflow.com/questions/13856266/class-broken-error-with-joda-time-using-scala
  val jodaTime = Seq(
    "joda-time" % "joda-time" % "2.1",
    "org.joda" % "joda-convert" % "1.2")

  val macwire = Seq(
    "com.softwaremill.macwire" %% "macros" % "0.5",
    "com.softwaremill.macwire" %% "runtime" % "0.5"
  )

  val slf4jVersion = "1.7.6"
  val logging = Seq(
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "org.slf4j" % "log4j-over-slf4j" % slf4jVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.1",
    "com.typesafe" %% "scalalogging-slf4j" % "1.1.0"
  )

  val scalatraVersion = "2.2.2"
  val swaggerVersion = "1.3.2"
  val json4s = "org.json4s" %% "json4s-jackson" % "3.1.0"
  val jettyVersion = "8.1.8.v20121106"
  /* The container scope requires the scalatra plugin */
  val servletApi = "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" %
    "container;provided;test" artifacts Artifact("javax.servlet", "jar", "jar")

  val httpStack = Seq(
    "org.scalatra" %% "scalatra" % scalatraVersion,
    "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test",
    "org.scalatra" %% "scalatra-json" % scalatraVersion,
    "com.wordnik" %% "swagger-project" % swaggerVersion,
    "org.scalatra" %% "scalatra-swagger" % scalatraVersion,
    "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
    "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container",
    servletApi,
    json4s
  )

  /* Mongo, Lift-Record, Rogue, Casbah */
  val casbah = "org.mongodb" %% "casbah" % "2.6.5" exclude(org = "org.scala-lang", name = "scala-library")
  val rogueVersion = "2.2.0"
  val gardenVersion = "0.0.3"
  val mongodbStack = Seq(
    "com.foursquare" %% "rogue-core" % rogueVersion intransitive(),
    "com.foursquare" %% "rogue-field" % rogueVersion intransitive(),
    "com.foursquare" %% "rogue-lift" % rogueVersion intransitive(),
    "com.foursquare" %% "rogue-index" % rogueVersion intransitive(),
    "net.liftweb" %% "lift-mongodb-record" % "2.5.1",
    "com.github.fakemongo" % "fongo" % "1.3.7" % "test",
    "com.softwaremill.thegarden" %% "mongodb" % gardenVersion,
    "com.softwaremill.thegarden" %% "mongodb-test" % gardenVersion % "test"
  ) ++ Seq(
    casbah
  )

  val akkaVersion = "2.3.0"
  val akkaActors = "com.typesafe.akka" %% "akka-actor" % akkaVersion force()
  val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test" force()
  val akka = Seq(akkaActors, akkaTestKit)

}
