package pl.kodujdlapolski.ztm.web

import spray.routing.SimpleRoutingApp
import akka.actor.ActorSystem

object ZtmWeb extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  startServer(interface = "localhost", port = 8080) {
    path("hello") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      }
    }
  }
}
