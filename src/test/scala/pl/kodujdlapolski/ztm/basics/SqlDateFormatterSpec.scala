package pl.kodujdlapolski.ztm.basics

import org.scalatest.{ShouldMatchers, FlatSpec}
import org.joda.time.LocalDate
import pl.kodujdlapolski.ztm.common.SqlDateFormatter

class SqlDateFormatterSpec extends FlatSpec with ShouldMatchers {

  List(
    (new LocalDate(2014, 3, 4), "2014-03-04"),
    (new LocalDate(2000, 2, 29), "2000-02-29"),
    (new LocalDate(2013, 12, 31), "2013-12-31")
  ).foreach {
    case (date, dateStr) =>
      it should s"format date $date in the same fashion as CURDATE() would" in {
        SqlDateFormatter.format(date) should equal(dateStr)
      }
  }

}
