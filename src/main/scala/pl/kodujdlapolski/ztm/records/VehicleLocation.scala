package pl.kodujdlapolski.ztm.records

import VehicleTypes.VehicleType
import org.joda.time.{DateTimeZone, DateTime}
import pl.kodujdlapolski.ztm.common.InfoDb
import com.typesafe.scalalogging.slf4j.Logging
import org.joda.time.Minutes.minutes

import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation
import Database.dynamicSession
import pl.kodujdlapolski.ztm.common.dateformats.SqlDateTime
import java.sql.Timestamp
import com.foursquare.rogue.LatLong

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

case class VehicleLocation(vehicleType: VehicleType, train: String, line: String, brigade: String,
                           latitude: Float, longitude: Float, lastUpdate: DateTime) {

  def loc = LatLong(latitude, longitude)
}

class VehicleLocationsProc(db: InfoDb) extends Logging {

  val WarsawDateTimeZone = DateTimeZone.forID("Europe/Warsaw")

  type VLTuple = (String, String, String, String, Float, Float, Timestamp)

  private def tuple2VehicleLocations(tuple: VLTuple) = {
    VehicleLocation(
      VehicleTypes.withName(tuple._1), tuple._2, tuple._3, tuple._4, tuple._5, tuple._6, new DateTime(tuple._7, WarsawDateTimeZone)
    )
  }

  def latestSince(date: DateTime): List[VehicleLocation] = {
    db.withDynSession {
      val q = sql"{CALL pobierz_lokalizacje_najnowsze(${SqlDateTime.format(date)})}".as[VLTuple]

      // TODO filter records from the future
      // Yes, there are some like that returned from the DB
      q.list().map(e => tuple2VehicleLocations(e))
    }
  }
}

class VehicleLocationsProcProxy(proc: VehicleLocationsProc) {

  import VehicleLocationsProcProxy._

  def latestValidData() = proc.latestSince(DateTime.now().minus(LatestDataInterval))
    .filter(VehicleLocationFilters.OnlyValid)

}

object VehicleLocationsProcProxy {
  val LatestDataInterval = minutes(10)
}

object VehicleLocationFilters {

  import pl.kodujdlapolski.ztm.mongodb.VehicleLocationRecord.FutureLocationsEpsilon

  val InvalidLine = {
    location: VehicleLocation =>
      location.line.trim == "0"
  }

  val FutureLocations = {
    location: VehicleLocation =>
      location.lastUpdate.isAfter(new DateTime().plus(FutureLocationsEpsilon))
  }

  val OnlyValid = {
    location: VehicleLocation =>
      !InvalidLine(location) && !FutureLocations(location)
  }

}
