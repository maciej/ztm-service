package pl.kodujdlapolski.ztm.web

import org.scalatra.ScalatraServlet
import org.scalatra.Ok
import pl.kodujdlapolski.ztm.common.ServletCompanion

class PingServlet extends ScalatraServlet {

  get("/") {
    Ok("pong")
  }
}

object PingServlet extends ServletCompanion {
  val MappingPath = "ping"
}