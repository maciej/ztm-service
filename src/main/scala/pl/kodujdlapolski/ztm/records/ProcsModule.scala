package pl.kodujdlapolski.ztm.records

import com.softwaremill.macwire.Macwire
import pl.kodujdlapolski.ztm.core.CoreModule

trait ProcsModule extends CoreModule {

  lazy val engineVersionProc = wire[EngineVersionProc]
  lazy val metroValidationsProc = wire[MetroValidationsProc]
  lazy val vehicleLocationsProc = wire[VehicleLocationsProc]
  lazy val vehicleLocationsProcProxy = wire[VehicleLocationsProcProxy]

}
