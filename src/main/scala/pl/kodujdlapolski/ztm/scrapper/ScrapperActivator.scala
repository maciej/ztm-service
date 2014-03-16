package pl.kodujdlapolski.ztm.scrapper

import pl.kodujdlapolski.ztm.core.{ZtmMongoInit, Beans}
import akka.actor.{Props, ActorSystem}


class ScrapperActivator(beans: Beans) {

  def init() = {
    val system = ActorSystem.create("scrapper")

    ZtmMongoInit.initializeBlocking(beans.config)

    system.actorOf(Props(classOf[ScrapperSupervisor], beans))
  }
}
