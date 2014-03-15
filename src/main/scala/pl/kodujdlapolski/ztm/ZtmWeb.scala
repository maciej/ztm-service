package pl.kodujdlapolski.ztm

import com.typesafe.scalalogging.slf4j.Logging
import java.net.InetSocketAddress
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import pl.kodujdlapolski.ztm.core.{CoreModule, WebServerConfig}

object ZtmWeb extends App with Logging {
  new EmbeddedJetty(CoreModule.config).startJetty()
}

class EmbeddedJetty(config: WebServerConfig) {

  lazy val jetty = {
    val server = new Server(new InetSocketAddress(config.webServerHost, config.webServerPort))
    server.setHandler(prepareContext())
    server
  }

  def startJetty() {
    jetty.start()
  }

  protected def prepareContext() = {
    val webContext = new WebAppContext()
    webContext.setContextPath("/")
    val webappDirInsideJar = webContext.getClass.getClassLoader.getResource("webapp").toExternalForm
    webContext.setWar(webappDirInsideJar)
    webContext
  }

  def stopJetty() {
    jetty.stop()
  }

}
