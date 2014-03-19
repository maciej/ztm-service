package pl.kodujdlapolski.ztm.api.v1_0

import org.scalatra.swagger.{SwaggerSupport, Swagger}
import pl.kodujdlapolski.ztm.common.web.JsonServlet
import org.joda.time.DateTime
import pl.kodujdlapolski.ztm.common.ServletCompanion
import pl.kodujdlapolski.ztm.records.{VehicleLocationFilters, VehicleLocationsProc}

class VehicleLocationsServlet(proc: VehicleLocationsProc, val swagger: Swagger) extends JsonServlet
with VehicleLocationsSwag {

  get("/", operation(getOperation)) {
    proc.latestSince(DateTime.now().minusMinutes(10)).filter(VehicleLocationFilters.OnlyValid)
  }
}

object VehicleLocationsServlet extends ServletCompanion {
  override val MappingPath = "vehicle-locations"
}

trait VehicleLocationsSwag extends SwaggerSupport {

  override protected def applicationName = Some(VehicleLocationsServlet.MappingPath)

  override protected def applicationDescription = "Vehicle locations"

  // Why "String" is returned here https://github.com/scalatra/scalatra/issues/343
  val getOperation = apiOperation[String]("vehicleLocations")
    .summary("returns vehicle locations")
}
