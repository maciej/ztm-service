package pl.kodujdlapolski.ztm.records

import org.scalatest.{ShouldMatchers, FlatSpec}

class VehicleLocationFiltersSpec extends FlatSpec with ShouldMatchers {

  import VehicleLocationRepository._

  behavior of "VehicleLocationFilters"

  it should "filter out invalid locations" in {
    InvalidVehicleLocations.filter(VehicleLocationFilters.OnlyValid) should be('empty)
  }

  it should "not filter out valid locations" in {
    ValidVehicleLocations.filter(VehicleLocationFilters.OnlyValid) should not(be('empty))
  }
}
