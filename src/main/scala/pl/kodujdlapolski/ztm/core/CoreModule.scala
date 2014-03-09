package pl.kodujdlapolski.ztm.core

import com.softwaremill.macwire.Macwire

import scala.slick.driver.MySQLDriver.simple._
import pl.kodujdlapolski.ztm.common.{InfoDb, StatsDb, TimetableDb}

trait CoreModule extends Macwire {

  lazy val config = wire[CoreConfig]

  lazy val timetableDb = new TimetableDb(ztmDb("rozklady"))

  lazy val statsDb = new StatsDb(ztmDb("statystyki"))

  lazy val infoDb = new InfoDb(ztmDb("informacje"))

  private def ztmDb(name: String) =
    Database.forURL(url = s"jdbc:mysql://${config.ztmDbHost}/$name",
      user = config.ztmDbUsername,
      password = config.ztmDbPassword,
      driver = "com.mysql.jdbc.Driver")

}

object CoreModule extends CoreModule
