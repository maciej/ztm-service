package pl.kodujdlapolski.ztm.basics

import org.scalatest.{ShouldMatchers, FlatSpec}
import org.joda.time.LocalDate
import pl.kodujdlapolski.ztm.common.dateformats.SqlDate

class SqlDateSpec extends FlatSpec with ShouldMatchers {

  private val dateTuples = List(
    (new LocalDate(2014, 3, 4), "2014-03-04"),
    (new LocalDate(2000, 2, 29), "2000-02-29"),
    (new LocalDate(2013, 12, 31), "2013-12-31")
  )

  dateTuples.foreach {
    case (date, dateStr) =>
      it should s"format date $date in the same fashion as CURDATE() would" in {
        SqlDate.format(date) should equal(dateStr)
      }
  }

  dateTuples.foreach {
    case (date, dateStr) =>
    it should s"parse date $dateStr into a respective LocalDate object" in {
      SqlDate.parse(dateStr) should equal(date)
    }
  }

}
