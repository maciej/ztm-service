package pl.kodujdlapolski.ztm

import com.typesafe.scalalogging.slf4j.Logging
import java.net.InetSocketAddress
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import pl.kodujdlapolski.ztm.core.{Beans, CoreModule, WebServerConfig}
import pl.kodujdlapolski.ztm.scrapper.ScrapperActivator

object ZtmWeb extends App with Logging {
  val beans = Beans

  private val jetty = new EmbeddedJetty(CoreModule.config, Map("beans" -> beans))
  jetty.startJetty()

  if (beans.config.scrapperStartFromWebApp)
    new ScrapperActivator(beans).init()
}

class EmbeddedJetty(config: WebServerConfig, contextAttributes: Map[String, AnyRef] = Map()) {

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

    contextAttributes.foreach {
      case (k, v) =>
        webContext.setAttribute(k, v)
    }

    webContext
  }

  def stopJetty() {
    jetty.stop()
  }

}
