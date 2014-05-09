package pl.kodujdlapolski.ztm.scrapper.scrappers

import akka.actor.Actor
import pl.kodujdlapolski.ztm.records.VehicleLocationsProcProxy
import pl.kodujdlapolski.ztm.scrapper.{ScheduleProvider, ScrapCommand}
import pl.kodujdlapolski.ztm.mongodb.VehicleLocationRecord
import com.typesafe.scalalogging.slf4j.Logging


class VehicleLocationScrapper(proxy: VehicleLocationsProcProxy) extends Actor with Logging {

  override def receive = {
    case ScrapCommand =>
      val beforeUpdateCount = VehicleLocationRecord.count
      proxy.latestValidData().foreach(VehicleLocationRecord.addUpdate)
      val afterUpdateCount = VehicleLocationRecord.count
      logger.debug(s"Vehicle Location Scrapper. Updated ${afterUpdateCount - beforeUpdateCount} records.")
  }
}

object VehicleLocationScrapper extends ScheduleProvider {

  import scala.concurrent.duration._

  override val scheduleDelay = 10.seconds
}
