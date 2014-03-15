package pl.kodujdlapolski.ztm.records

import VehicleTypes.VehicleType
import org.joda.time.{DateTimeZone, DateTime}
import pl.kodujdlapolski.ztm.common.{ServletCompanion, InfoDb}
import com.typesafe.scalalogging.slf4j.Logging

import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation
import Database.dynamicSession
import pl.kodujdlapolski.ztm.common.dateformats.SqlDateTime
import java.sql.Date
import org.scalatra.swagger.{SwaggerSupport, Swagger}
import pl.kodujdlapolski.ztm.common.web.JsonServlet

/*
 * Params:
 * - vehicle type
 * - taborowy?
 * - line
 * - brygada?
 * - latitude (gps_szer)
 * - longitude (gps_dlug)
 * - updated_at
 */

/*
 * Important implementation note: cutoff requests that are asking for data >21min old
 */

case class VehicleLocation(vehicleType: VehicleType, taborowy: String, line: String, brigade: String,
                           latitude: Float, longitude: Float, lastUpdate: DateTime)

class VehicleLocationsProc(db: InfoDb) extends Logging {

  val WarsawDateTimeZone = DateTimeZone.forID("Europe/Warsaw")

  type VLTuple = (String, String, String, String, Float, Float, Date)

  private def tuple2VehicleLocations(tuple: VLTuple) = VehicleLocation(
    VehicleTypes.withName(tuple._1), tuple._2, tuple._3, tuple._4, tuple._5, tuple._6, new DateTime(tuple._7, WarsawDateTimeZone)
  )

  def latestSince(date: DateTime): List[VehicleLocation] = {
    db.withDynSession {
      val q = sql"{CALL pobierz_lokalizacje_najnowsze(${SqlDateTime.format(date)})}".as[VLTuple]

      // TODO filter records from the future
      // Yes, there are some like that returned from the DB
      q.list().map(e => tuple2VehicleLocations(e))
    }
  }
}

object VehicleLocationFilters {
  val InvalidLine = {
    location: VehicleLocation =>
    location.line.trim == "0"
  }

  val OnlyValid = InvalidLine.andThen(!_)

}

class VehicleLocationsServlet(proc: VehicleLocationsProc, val swagger: Swagger) extends JsonServlet
with VehicleLocationsSwag {

  get("/", operation(getOperation)) {
    proc.latestSince(DateTime.now().minusMinutes(5)).filter(VehicleLocationFilters.OnlyValid)
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
