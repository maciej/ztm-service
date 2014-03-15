package pl.kodujdlapolski.ztm.scrapper

import akka.actor.{Props, Actor}
import pl.kodujdlapolski.ztm.core.Beans

class ScrapperSupervisor(beans: Beans) extends Actor {
  @throws(classOf[Exception])
  override def preStart() = {
    super.preStart()

    val system = context.system

    val vehicleLocationScrapper = system.actorOf(Props(classOf[VehicleLocationScrapper], beans.vehicleLocationsProc))

    import system.dispatcher

    system.scheduler.schedule(VehicleLocationScrapper.scheduleInitialDelay, VehicleLocationScrapper.scheduleDelay,
      vehicleLocationScrapper, ScrapCommand)
  }

  override def receive = {
    /* Ignore all input */
    case _ => Nil
  }

}

trait ScheduleProvider {

  import scala.concurrent.duration._

  val scheduleInitialDelay = 0.seconds

  val scheduleDelay: FiniteDuration
}
