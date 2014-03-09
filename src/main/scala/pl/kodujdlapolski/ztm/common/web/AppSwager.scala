package pl.kodujdlapolski.ztm.common.web

import org.scalatra.swagger.{JacksonSwaggerBase, Swagger}
import org.scalatra.ScalatraServlet
import org.json4s.{DefaultFormats, Formats}
import pl.kodujdlapolski.ztm.common.ServletCompanion

class SwaggerApiDoc(val swagger: Swagger) extends ScalatraServlet with JacksonSwaggerBase {
  override implicit val jsonFormats: Formats = DefaultFormats
}

object SwaggerApiDoc extends ServletCompanion {
  override val MappingPath = "api-docs"
}

class AppSwagger extends Swagger("1.0", "1")
