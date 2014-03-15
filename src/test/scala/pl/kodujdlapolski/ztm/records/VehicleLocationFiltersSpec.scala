package pl.kodujdlapolski.ztm.records

import org.scalatest.{ShouldMatchers, FlatSpec}
import org.joda.time.DateTime


class VehicleLocationFiltersSpec extends FlatSpec with ShouldMatchers {

  behavior of "VehicleLocationFilters"

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

  it should "filter out invalid locations" in {
    InvalidVehicleLocations.filter(VehicleLocationFilters.OnlyValid) should be('empty)
  }

  it should "not filter out valid locations" in {
    val validLocationsLength = ValidVehicleLocations.length
    ValidVehicleLocations.filter(VehicleLocationFilters.OnlyValid) should not(be('empty))
  }
}