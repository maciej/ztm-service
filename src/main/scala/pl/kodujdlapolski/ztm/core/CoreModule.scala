package pl.kodujdlapolski.ztm.core

import com.softwaremill.macwire.Macwire
import pl.kodujdlapolski.ztm.config.CoreConfig

import scala.slick.driver.MySQLDriver.simple._
import pl.kodujdlapolski.ztm.common.DatabaseWrapper

trait CoreModule extends Macwire {

  lazy val config = wire[CoreConfig]

  lazy val timetableDb = ztmDb("rozklady")

  lazy val statsDb = ztmDb("statystyki")

  private def ztmDb(name: String): DatabaseWrapper = new DatabaseWrapper(
    Database.forURL(url = s"jdbc:mysql://${config.ztmDbHost}/$name",
      user = config.ztmDbUsername,
      password = config.ztmDbPassword,
      driver = "com.mysql.jdbc.Driver")
  )

}

object CoreModule extends CoreModule
