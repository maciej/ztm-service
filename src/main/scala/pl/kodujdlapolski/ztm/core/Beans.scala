package pl.kodujdlapolski.ztm.core

import pl.kodujdlapolski.ztm.records.ProcsModule

trait Beans extends CoreModule with ProcsModule

object Beans extends Beans
