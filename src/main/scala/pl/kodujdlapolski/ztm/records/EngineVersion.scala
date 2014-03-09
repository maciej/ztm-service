package pl.kodujdlapolski.ztm.records

import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation
import Database.dynamicSession
import pl.kodujdlapolski.ztm.common.web.JsonServlet
import pl.kodujdlapolski.ztm.common.{TimetableDb, ServletCompanion}
import org.scalatra.swagger.{Swagger, SwaggerSupport}

case class EngineVersion(value: String)

class EngineVersionProc(timetableDb: TimetableDb) {

  implicit def tuple2EngineVersion(tuple: (String, String)) = EngineVersion(tuple._2)

  private def query() = sql"{CALL pobierz_wersje_silnika_bazy()}".as[(String, String)]

  // http://blog.lunatech.com/2013/11/21/slick-case-classes
  def get(): EngineVersion = {
    timetableDb.withDynSession {
      query().first()
    }
  }

}

class EngineVersionServlet(engineVersionProc: EngineVersionProc, val swagger: Swagger) extends JsonServlet
with EngineVersionServletSwaggerDefinition {

  get("/", operation(getOperation)) {
    engineVersionProc.get()
  }

}

object EngineVersionServlet extends ServletCompanion {
  override val MappingPath: String = "engine-version"
}

trait EngineVersionServletSwaggerDefinition extends SwaggerSupport {
  override protected def applicationName = Some(EngineVersionServlet.MappingPath)

  override protected def applicationDescription = "Provides ZTM engine version"

  val getOperation = apiOperation[EngineVersion]("engineVersion").summary("returns engine version")
}