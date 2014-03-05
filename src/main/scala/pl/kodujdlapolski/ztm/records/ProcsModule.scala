package pl.kodujdlapolski.ztm.records

import com.softwaremill.macwire.Macwire

trait ProcsModule extends Macwire {
  lazy val engineVersionProc = wire[EngineVersionProc]

  lazy val metroValidationsProc = wire[MetroValidationsProc]
}