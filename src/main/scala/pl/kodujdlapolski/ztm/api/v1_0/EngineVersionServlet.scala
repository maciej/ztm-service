package pl.kodujdlapolski.ztm.api.v1_0

import org.scalatra.swagger.{SwaggerSupport, Swagger}
import pl.kodujdlapolski.ztm.common.web.JsonServlet
import pl.kodujdlapolski.ztm.common.ServletCompanion
import pl.kodujdlapolski.ztm.records.{EngineVersion, EngineVersionProc}

class EngineVersionServlet(engineVersionProc: EngineVersionProc, val swagger: Swagger) extends JsonServlet
with EngineVersionSwag {

  get("/", operation(getOperation)) {
    engineVersionProc.get()
  }

}

object EngineVersionServlet extends ServletCompanion {
  override val MappingPath: String = "engine-version"
}

trait EngineVersionSwag extends SwaggerSupport {
  override protected def applicationName = Some(EngineVersionServlet.MappingPath)

  override protected def applicationDescription = "ZTM MySQL db version"

  val getOperation = apiOperation[EngineVersion]("engineVersion").summary("returns engine version")
}
