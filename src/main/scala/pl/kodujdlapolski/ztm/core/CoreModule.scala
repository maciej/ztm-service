package pl.kodujdlapolski.ztm.core

import com.softwaremill.macwire.Macwire
import pl.kodujdlapolski.ztm.config.CoreConfig

trait CoreModule extends Macwire {

  lazy val config = wire[CoreConfig]
}

object CoreModule extends CoreModule
