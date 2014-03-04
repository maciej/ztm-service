package pl.kodujdlapolski.ztm.procs

import com.softwaremill.macwire.Macwire

trait ProcsModule extends Macwire {
  lazy val engineVersionProc = wire[EngineVersionProc]
}