package pl.kodujdlapolski.ztm.common.web

import org.scalatra.ScalatraServlet
import org.scalatra.json.{JValueResult, JacksonJsonSupport}
import org.json4s.{DefaultFormats, Formats}
import javax.servlet.http.HttpServletResponse
import java.util.Date

class JsonServlet extends ScalatraServlet with JacksonJsonSupport with JValueResult {

  protected implicit val jsonFormats: Formats = DefaultFormats ++ org.json4s.ext.JodaTimeSerializers.all

  before() {
    contentType = formats("json")
    applyNoCache(response)
  }

  protected def applyNoCache(response: HttpServletResponse) {
    val Expire = new Date().toString
    response.addHeader("Expires", Expire)
    response.addHeader("Last-Modified", Expire)
    response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
    response.addHeader("Pragma", "no-cache")
  }

}
