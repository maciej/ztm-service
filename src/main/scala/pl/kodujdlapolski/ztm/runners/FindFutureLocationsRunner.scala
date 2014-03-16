package pl.kodujdlapolski.ztm.runners

import pl.kodujdlapolski.ztm.core.{Beans, ZtmMongoInit}
import pl.kodujdlapolski.ztm.mongodb.VehicleLocationRecord

object FindFutureLocationsRunner extends App {
  ZtmMongoInit.initializeBlocking(Beans.config)
  private val locations = VehicleLocationRecord.findFutureLocations()
  println(s"Found ${locations.size} future locations.")
  println(locations.mkString("\n"))
}