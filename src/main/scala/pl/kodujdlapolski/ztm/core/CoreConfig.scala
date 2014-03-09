package pl.kodujdlapolski.ztm.core

import com.typesafe.config.ConfigFactory

class CoreConfig {
  import CoreConfig._

  def rootConfig = ConfigFactory.load().withFallback(ConfigFactory.load("pl/kodujdlapolski/ztm/defaults.conf"))

  lazy val ztmDbHost = rootConfig.getString(s"$ZtmMysql.host")
  lazy val ztmDbUsername = rootConfig.getString(s"$ZtmMysql.username")
  lazy val ztmDbPassword = rootConfig.getString(s"$ZtmMysql.password")
}

object CoreConfig {
  private val ZtmMysql = "ztm-mysql"
}
