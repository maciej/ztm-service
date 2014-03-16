package pl.kodujdlapolski.ztm.records

import org.joda.time.DateTime

object VehicleLocationRepository {
  val BaseVehicleLocation = VehicleLocation(VehicleTypes.Tram, "123", "12", "123", 54.2f, 12.3f, new DateTime())

  val ZeroVehicleLineLocation = BaseVehicleLocation.copy(line = "0")
  val NotTrimmedZeroVehicleLineLocation = BaseVehicleLocation.copy(line = " 0 ")

  val InvalidVehicleLocations = List(
    ZeroVehicleLineLocation,
    NotTrimmedZeroVehicleLineLocation
  )

  val ValidVehicleLocations = List(
    BaseVehicleLocation
  )
}
