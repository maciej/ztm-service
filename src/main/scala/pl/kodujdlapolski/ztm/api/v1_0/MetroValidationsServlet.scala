package pl.kodujdlapolski.ztm.api.v1_0

import org.scalatra.swagger.{SwaggerSupport, Swagger}
import pl.kodujdlapolski.ztm.common.web.JsonServlet
import pl.kodujdlapolski.ztm.common.dateformats.NumericYearMonthDate
import pl.kodujdlapolski.ztm.common.ServletCompanion
import org.joda.time.DateTime
import pl.kodujdlapolski.ztm.records.{Stations, MetroValidation, MetroValidationsProc}

class MetroValidationsServlet(proc: MetroValidationsProc, val swagger: Swagger) extends JsonServlet
with MetroValidationsSwag {

  get("/:stationId/:date", operation(getOperation)) {
    proc.forStationOnDate(Stations(params("stationId").toInt),
      NumericYearMonthDate.parse(params("date")))
  }
}

object MetroValidationsServlet extends ServletCompanion {
  override val MappingPath: String = "metro-validations"
}

trait MetroValidationsSwag extends SwaggerSupport {
  override protected def applicationName = Some(MetroValidationsServlet.MappingPath)

  override protected def applicationDescription = "Metro validations"

  private val stationId = pathParam[Int]("stationId").description("Station ID").required

  private val date = pathParam[DateTime]("date").description("Date").required

  val getOperation = apiOperation[MetroValidation]("metroValidations")
    .summary("returns metro validations")
    .parameter(stationId)
    .parameter(date)

}
