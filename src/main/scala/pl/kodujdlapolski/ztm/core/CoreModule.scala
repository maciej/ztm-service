package pl.kodujdlapolski.ztm.core

import com.softwaremill.macwire.Macwire
import pl.kodujdlapolski.ztm.config.CoreConfig

import scala.slick.driver.MySQLDriver.simple._
import pl.kodujdlapolski.ztm.common.DatabaseWrapper

trait CoreModule extends Macwire {

  lazy val config = wire[CoreConfig]

  private lazy val timetablesDbInternal: Database = ztmDb("rozklady")

  lazy val timetableDb = new DatabaseWrapper(timetablesDbInternal)

  private lazy val statsDbInternal : Database = ztmDb("statystyki")

  private def ztmDb(name : String) : Database =
    Database.forURL(url = s"jdbc:mysql://${config.ztmDbHost}/$name",
      user = config.ztmDbUsername,
      password = config.ztmDbPassword,
      driver = "com.mysql.jdbc.Driver")

}

object CoreModule extends CoreModule
