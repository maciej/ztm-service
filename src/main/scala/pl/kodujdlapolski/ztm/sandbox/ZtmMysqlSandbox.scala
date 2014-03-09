package pl.kodujdlapolski.ztm.sandbox


import pl.kodujdlapolski.ztm.core.{CoreConfig, Beans}
import pl.kodujdlapolski.ztm.common.DatabaseWrapper

import pl.kodujdlapolski.ztm.records.{Stations, MetroValidationsProc, EngineVersionProc}
import org.joda.time.LocalDate


class ZtmMysqlSandbox(config: CoreConfig,
                      statsDb: DatabaseWrapper,
                      engineVersionProc: EngineVersionProc,
                      metroValidationsProc: MetroValidationsProc) {

  def run() = {

    // See https://groups.google.com/forum/#!topic/scalaquery/BUB2-ryR0bY
    statsDb.withDynSession {
      //      println(engineVersionProc.get())
      println(metroValidationsProc.forStationOnDate(Stations.Kabaty, new LocalDate(2014, 3, 2)))
    }
  }
}

object ZtmMysqlSandboxRunner extends App {

  val beans = Beans

  new ZtmMysqlSandbox(beans.config, beans.statsDb, beans.engineVersionProc, beans.metroValidationsProc).run()
}
