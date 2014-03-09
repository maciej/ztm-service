package pl.kodujdlapolski.ztm.common.dateformats

import org.joda.time.LocalDate
import org.joda.time.format.{DateTimeFormatter, DateTimeFormat}

object SqlDate extends NumericYearMonthDate

object NumericYearMonthDate extends NumericYearMonthDate

trait NumericYearMonthDate extends NamedDateTimeFormat {

  override val Format = DateTimeFormat.forPattern("YYYY-MM-dd")

}

trait NamedDateTimeFormat {

  val Format: DateTimeFormatter

  def format(date: LocalDate): String = Format.print(date)

  def parse(string: String): LocalDate = Format.parseLocalDate(string)
}
