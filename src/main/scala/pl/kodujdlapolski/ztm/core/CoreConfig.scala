package pl.kodujdlapolski.ztm.core

import com.typesafe.config.{Config, ConfigFactory}

trait ZtmConfig {

  import ZtmConfig._

  def rootConfig: Config

  lazy val ztmDbHost = rootConfig.getString(s"$ZtmMysql.host")
  lazy val ztmDbUsername = rootConfig.getString(s"$ZtmMysql.username")
  lazy val ztmDbPassword = rootConfig.getString(s"$ZtmMysql.password")
}

class CoreConfig extends ZtmConfig with WebServerConfig {
  def rootConfig = ConfigFactory.load().withFallback(ConfigFactory.load("pl/kodujdlapolski/ztm/defaults.conf"))

}

object ZtmConfig {
  private val ZtmMysql = "ztm-mysql"
}
