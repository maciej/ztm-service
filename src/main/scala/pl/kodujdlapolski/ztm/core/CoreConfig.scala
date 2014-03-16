package pl.kodujdlapolski.ztm.core

import com.typesafe.config.{Config, ConfigFactory}

trait ZtmConfig {

  import ZtmConfig._

  def rootConfig: Config

  lazy val ztmDbHost = rootConfig.getString(s"$ZtmMysql.host")
  lazy val ztmDbUsername = rootConfig.getString(s"$ZtmMysql.username")
  lazy val ztmDbPassword = rootConfig.getString(s"$ZtmMysql.password")
}

trait ScrapperConfig {
  def rootConfig: Config

  lazy val scrapperStartFromWebApp = rootConfig.getBoolean("scrapper.start-from-web-app")
}

trait MongodbConfig {
  def rootConfig: Config

  lazy val mongoServers = rootConfig.getString("mongodb.servers")
  lazy val mongoDatabase = rootConfig.getString("mongodb.database")
  lazy val ensureIndexes = rootConfig.getBoolean("mongodb.ensure-indexes")
}

class CoreConfig extends ZtmConfig with WebServerConfig with ScrapperConfig with MongodbConfig {
  def rootConfig = ConfigFactory.load().withFallback(ConfigFactory.load("pl/kodujdlapolski/ztm/defaults.conf"))

}

object ZtmConfig {
  private val ZtmMysql = "ztm-mysql"
}
