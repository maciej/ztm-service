package pl.kodujdlapolski.ztm.records

import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation
import Database.dynamicSession

import Stations.Station
import org.joda.time.{DateTime, LocalDate}
import pl.kodujdlapolski.ztm.common.SqlDateFormatter
import java.sql.Date
import com.typesafe.scalalogging.slf4j.Logging

/*
 * From fb communcation with wojtek:
 *  Przykład wywołania: call pobierz_metro_skasowania_minutowe (23, curdate());
 */

case class MetroValidations(timestamp: DateTime, count: Int)

class MetroValidationsProc extends Logging {

  type MVTuple = (Int, Date, Int, Int, Int)

  implicit def tuple2MetroValidations(tuple: MVTuple) = MetroValidations(
    new DateTime(tuple._2).withHourOfDay(tuple._3).withMinuteOfHour(tuple._4), tuple._5)

  def forStationOnDate(station: Station, date: LocalDate): List[MetroValidations] = {
    logger.info(s"Retrieving metro validations for stations $station on $date")

    val q = sql"{CALL pobierz_metro_skasowania_minutowe(${station.id}, ${SqlDateFormatter.format(date)})}".as[MVTuple]
    // TODO figure out how to avoid the map operation
    q.list.map(e => tuple2MetroValidations(e))
  }
}
