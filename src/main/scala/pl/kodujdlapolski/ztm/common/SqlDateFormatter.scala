package pl.kodujdlapolski.ztm.common

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

object SqlDateFormatter {
  private val JodaFormat = DateTimeFormat.forPattern("YYYY-MM-dd")

  def format(date: LocalDate): String = JodaFormat.print(date)
}