package pl.kodujdlapolski.ztm.core

import com.typesafe.config.Config

trait WebServerConfig {
  def rootConfig: Config

  lazy val webServerHost: String = rootConfig.getString("web.host")
  lazy val webServerPort: Int = rootConfig.getInt("web.port")
}
