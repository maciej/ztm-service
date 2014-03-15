package pl.kodujdlapolski.ztm.scrapper

import pl.kodujdlapolski.ztm.core.Beans
import akka.actor.{Props, ActorSystem}


class ScrapperActivator(beans: Beans) {

  def init() = {
    val system = ActorSystem.create("scrapper")

    system.actorOf(Props(classOf[ScrapperSupervisor], beans))
  }
}
