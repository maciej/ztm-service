import sbt._

object Resolvers {

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

}
