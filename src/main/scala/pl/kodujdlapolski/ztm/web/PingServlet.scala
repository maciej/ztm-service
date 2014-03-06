package pl.kodujdlapolski.ztm.web

import org.scalatra.ScalatraServlet
import org.scalatra.{BadRequest, Ok, FlashMapSupport}

class PingServlet extends ScalatraServlet {

  get("/ping") {
    Ok("pong")
  }
}
