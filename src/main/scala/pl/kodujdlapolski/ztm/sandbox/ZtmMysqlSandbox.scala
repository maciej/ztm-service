package pl.kodujdlapolski.ztm.sandbox

import pl.kodujdlapolski.ztm.config.CoreConfig

import pl.kodujdlapolski.ztm.core.CoreModule
import pl.kodujdlapolski.ztm.common.DatabaseWrapper

import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Q.interpolation


class ZtmMysqlSandbox(config : CoreConfig, timetableDb : DatabaseWrapper) {

  def run() = {

    // See https://groups.google.com/forum/#!topic/scalaquery/BUB2-ryR0bY
    timetableDb.withSession{
     implicit session =>
        val query = sql"{call pobierz_wersje_silnika_bazy()}".as[(String, String)]
        println(query.first())
    }
  }
}

object ZtmMysqlSandboxRunner extends App {

  val beans = CoreModule

  new ZtmMysqlSandbox(CoreModule.config, CoreModule.timetableDb).run()
}
