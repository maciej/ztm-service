package pl.kodujdlapolski.ztm.records

import org.scalatest.{ShouldMatchers, FlatSpec}
import org.joda.time.DateTime
import pl.kodujdlapolski.ztm.mongodb.VehicleLocationRecord

class VehicleLocationFiltersSpec extends FlatSpec with ShouldMatchers {

  import VehicleLocationRepository._

  import VehicleLocationFilters._

  behavior of "VehicleLocationFilters"

  it should "filter out invalid locations" in {
    InvalidVehicleLocations.filter(OnlyValid) should be('empty)
  }

  it should "not filter out valid locations" in {
    ValidVehicleLocations.filter(OnlyValid) should not(be('empty))
  }

  it should "filter out locations that have update times from the future" in {
    Some(BaseVehicleLocation.copy(lastUpdate =
      new DateTime().plus(VehicleLocationRecord.FutureLocationsEpsilon).plusSeconds(10))).
      filter(FutureLocations.andThen(!_)) should be('empty)
  }
}
