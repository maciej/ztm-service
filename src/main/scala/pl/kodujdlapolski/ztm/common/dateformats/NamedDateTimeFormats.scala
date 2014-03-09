package pl.kodujdlapolski.ztm.common.dateformats

import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.{DateTimeZone, DateTime}

object SqlDateTime extends NumericLocalSecondsPrecisionDateTime

object NumericLocalSecondsPrecisionDateTime extends NumericLocalSecondsPrecisionDateTime

trait NumericLocalSecondsPrecisionDateTime extends NamedDateTimeFormat {
  override val Format = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss")
}

trait NamedDateTimeFormat {
  val Format: DateTimeFormatter

  def format(date: DateTime) = Format.print(date)
  
  def parseWithTimeZone(string : String, timeZone : DateTimeZone) =
    Format.parseLocalDateTime(string).toDateTime(timeZone)
}
