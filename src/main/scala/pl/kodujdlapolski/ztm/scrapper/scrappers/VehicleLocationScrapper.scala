package pl.kodujdlapolski.ztm.scrapper.scrappers

import akka.actor.Actor
import pl.kodujdlapolski.ztm.records.VehicleLocationsProcProxy
import pl.kodujdlapolski.ztm.scrapper.{ScheduleProvider, ScrapCommand}


class VehicleLocationScrapper(proxy : VehicleLocationsProcProxy) extends Actor {

  override def receive = {
    case ScrapCommand =>
      proxy.latestValidData()

  }
}

object VehicleLocationScrapper extends ScheduleProvider {
  import scala.concurrent.duration._

  override val scheduleDelay = 10.seconds
}
