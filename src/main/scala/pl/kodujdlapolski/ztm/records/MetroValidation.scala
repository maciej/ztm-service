package pl.kodujdlapolski.ztm.records

import scala.slick.driver.JdbcDriver.backend.Database
import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation
import Database.dynamicSession

import Stations.Station
import org.joda.time.{DateTime, LocalDate}
import pl.kodujdlapolski.ztm.common.{StatsDb, ServletCompanion}
import java.sql.Date
import com.typesafe.scalalogging.slf4j.Logging
import org.scalatra.swagger.{SwaggerSupport, Swagger}
import pl.kodujdlapolski.ztm.common.web.JsonServlet
import pl.kodujdlapolski.ztm.common.dateformats.{NumericYearMonthDate, SqlDate}

/*
 * From fb communcation with wojtek:
 *  Przykład wywołania: call pobierz_metro_skasowania_minutowe (23, curdate());
 */

case class MetroValidation(timestamp: DateTime, count: Int)

class MetroValidationsProc(db: StatsDb) extends Logging {

  type MVTuple = (Int, Date, Int, Int, Int)

  implicit def tuple2MetroValidations(tuple: MVTuple) = MetroValidation(
    new DateTime(tuple._2).withHourOfDay(tuple._3).withMinuteOfHour(tuple._4), tuple._5)

  def forStationOnDate(station: Station, date: LocalDate): List[MetroValidation] = {
    logger.info(s"Retrieving metro validations for stations $station on $date")

    db.withDynSession {
      val q = sql"{CALL pobierz_metro_skasowania_minutowe(${station.id}, ${SqlDate.format(date)})}".as[MVTuple]
      // TODO figure out how to avoid the map operation
      q.list.map(e => tuple2MetroValidations(e))
    }
  }
}

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

  override protected def applicationDescription = "Provides metro validations"

  private val stationId = pathParam[Int]("stationId").description("Station ID").required

  private val date = pathParam[DateTime]("date").description("Date").required

  val getOperation = apiOperation[MetroValidation]("metroValidations")
    .summary("returns metro validations")
    .parameter(stationId)
    .parameter(date)

}
