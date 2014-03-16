package pl.kodujdlapolski.ztm.mongodb

import org.scalatest.{ShouldMatchers, FlatSpec}
import pl.kodujdlapolski.ztm.records.VehicleLocationRepository

class VehicleLocationRecordSpec extends FlatSpec with FongoSupport with ShouldMatchers {

  it should "be persistable" in {
    val countBeforeSave = VehicleLocationRecord.count

    val record = VehicleLocationRecord.toRecord(VehicleLocationRepository.BaseVehicleLocation)
    record.save

    val countAfterSave = VehicleLocationRecord.count

    (countAfterSave - countBeforeSave) should equal(1)
  }
}