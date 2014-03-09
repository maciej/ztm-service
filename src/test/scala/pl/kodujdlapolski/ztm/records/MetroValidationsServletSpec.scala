package pl.kodujdlapolski.ztm.records

import org.scalatest.{FlatSpec, BeforeAndAfterEach, ShouldMatchers}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.scalatra.swagger.Swagger
import org.joda.time.LocalDate
import pl.kodujdlapolski.ztm.scalatra.ScalatraFlatSpec
import pl.kodujdlapolski.ztm.common.dateformats.NumericYearMonthDate

class MetroValidationsServletSpec extends ScalatraFlatSpec
with ShouldMatchers with MockitoSugar with BeforeAndAfterEach {

  val proc = mock[MetroValidationsProc]

  val swagger = mock[Swagger]

  addServlet(new MetroValidationsServlet(proc, swagger), "/*")

  override protected def beforeEach() = {
    super.beforeEach()

    reset(proc)
  }

  List(
    (Stations.Kabaty, new LocalDate(2013, 12, 31)),
    (Stations.Stoklosy, new LocalDate(2012, 10, 11)),
    (Stations.Marymont, new LocalDate(2014, 3, 4))
  ).foreach {
    case (station, date) =>
      val datePrinted = NumericYearMonthDate.format(date)
    s"GET /${station.id}/${datePrinted}" should
      s"call MetroValidations proc requesting validations for ${station.toString} on ${datePrinted}" in {
      get(s"/${station.id}/${datePrinted}") {
        verify(proc).forStationOnDate(station, date)
      }
    }
  }

}
