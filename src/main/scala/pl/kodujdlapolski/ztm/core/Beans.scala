package pl.kodujdlapolski.ztm.core

import pl.kodujdlapolski.ztm.records.ProcsModule
import pl.kodujdlapolski.ztm.common.web.AppSwagger

trait Beans extends CoreModule with ProcsModule {

  lazy val swagger = new AppSwagger

}

object Beans extends Beans
