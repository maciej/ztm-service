package pl.kodujdlapolski.ztm.scrapper

import pl.kodujdlapolski.ztm.core.Beans

object ScrapperRunner extends App {
  new ScrapperActivator(Beans).init()
}