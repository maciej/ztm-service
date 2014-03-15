import sbt._
import sbt.Keys._
import sbtassembly.Plugin.assemblySettings
import sbtassembly.Plugin.AssemblyKeys._
import Dependencies._
import org.scalatra.sbt._
import com.earldouglas.xsbtwebplugin.PluginKeys.webappResources

object ZtmBuild extends Build {
  //  override lazy val settings = super.settings

  System.setProperty("config.file", "application.conf")

  lazy val slf4jExclusionHack = Seq(
    ivyXML :=
      <dependencies>
        <exclude org="org.slf4j" artifact="slf4j-log4j12"/>
        <exclude org="log4j" artifact="log4j"/>
      </dependencies>
  )

  // Remember about the default settings:
  // http://grokbase.com/t/gg/simple-build-tool/132336h2nz/sbt-help-with-scopes-and-undefined-setting-error
  lazy val commonSettings = Defaults.defaultSettings ++ Seq(isSnapshot <<= isSnapshot or version(_ endsWith "-SNAPSHOT")) ++ slf4jExclusionHack ++
    Seq(
      mainClass in assembly := Some("pl.kodujdlapolski.ztm.ZtmWeb")
    ) ++ assemblySettings ++ ScalatraPlugin.scalatraSettings

  lazy val commonDependencies = Seq(
    "org.scalatest" %% "scalatest" % "2.0" % "test",
    "com.typesafe.slick" %% "slick" % "2.0.0-RC1",
    "mysql" % "mysql-connector-java" % "5.1.28",
    "com.typesafe" % "config" % "1.0.2",
    "org.mockito" % "mockito-core" % "1.9.5"
  ) ++ jodaTime ++ macwire ++ logging ++ httpStack

  lazy val root = Project(
    id = "ztm-service",
    base = file("."),
    settings = commonSettings
  ).settings(
      libraryDependencies ++= commonDependencies,
      // HACK
      /*
       * I couldn't get this directory to be mapped to webapp, so we have a structure web/webapp
       * Here are some links where to start researching this topic:
       * http://www.scala-sbt.org/release/docs/Howto/defaultpaths.html
       * http://www.scala-sbt.org/release/docs/Detailed-Topics/Paths.html
       * http://www.scala-sbt.org/release/docs/Detailed-Topics/Mapping-Files.html
       */
      unmanagedResourceDirectories in Compile <++= baseDirectory { base =>
        Seq( base / "web" )
      },
      webappResources in Compile := Seq(baseDirectory.value / "web" / "webapp")
    )

}
