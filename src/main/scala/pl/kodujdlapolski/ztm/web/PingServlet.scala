package pl.kodujdlapolski.ztm.web

import org.scalatra.ScalatraServlet
import org.scalatra.Ok

class PingServlet extends ScalatraServlet {

  get("/ping") {
    Ok("pong")
  }
}
