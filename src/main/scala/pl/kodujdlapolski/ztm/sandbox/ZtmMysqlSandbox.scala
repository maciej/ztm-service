package pl.kodujdlapolski.ztm.sandbox

import pl.kodujdlapolski.ztm.config.CoreConfig

import pl.kodujdlapolski.ztm.core.Beans
import pl.kodujdlapolski.ztm.common.DatabaseWrapper

import pl.kodujdlapolski.ztm.procs.EngineVersionProc


class ZtmMysqlSandbox(config: CoreConfig,
                      timetableDb: DatabaseWrapper,
                      engineVersionProc: EngineVersionProc) {

  def run() = {

    // See https://groups.google.com/forum/#!topic/scalaquery/BUB2-ryR0bY
    timetableDb.withDynSession {
      println(engineVersionProc.get())
    }
  }
}

object ZtmMysqlSandboxRunner extends App {

  val beans = Beans

  new ZtmMysqlSandbox(beans.config, beans.timetableDb, beans.engineVersionProc).run()
}
