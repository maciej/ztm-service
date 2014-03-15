package pl.kodujdlapolski.ztm.scrapper

import akka.actor.Actor
import pl.kodujdlapolski.ztm.records.VehicleLocationsProc


class VehicleLocationScrapper(proc : VehicleLocationsProc) extends Actor {

  override def receive = {
    case ScrapCommand =>
      println("aaaa")
  }
}

object VehicleLocationScrapper extends ScheduleProvider {
  import scala.concurrent.duration._

  override val scheduleDelay = 10.seconds
}
