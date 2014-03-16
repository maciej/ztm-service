package pl.kodujdlapolski.ztm.mongodb

import org.scalatest.{ShouldMatchers, FlatSpec}
import pl.kodujdlapolski.ztm.records.VehicleLocationRepository

class VehicleLocationRecordSpec extends FlatSpec with FongoSupport with ShouldMatchers {

  import VehicleLocationRepository._

  val className = classOf[VehicleLocationRecord].getSimpleName


  def dropCollection() {
    VehicleLocationRecord.drop
    assert(VehicleLocationRecord.count == 0)
  }

  it should "be persistable" in {
    val countBeforeSave = VehicleLocationRecord.count

    val record = VehicleLocationRecord.fromEntity(BaseVehicleLocation)
    record.save

    val countAfterSave = VehicleLocationRecord.count

    (countAfterSave - countBeforeSave) should equal(1)
  }

  s"$className.addUpdate" should "add a record if one does not exist" in {
    // Setup, Given
    dropCollection()

    // When
    VehicleLocationRecord.addUpdate(BaseVehicleLocation)
    val countAfterAddingFirstUpdate = VehicleLocationRecord.count

    VehicleLocationRecord.addUpdate(BaseVehicleLocation)
    val countAfterAddingSecondUpdate = VehicleLocationRecord.count

    // Then
    countAfterAddingFirstUpdate should equal(1)
    countAfterAddingSecondUpdate should equal(1)
  }

  s"$className.addUpdate" should "persist all information from the location object" in {
    dropCollection()

    // When
    println("Base loc: " + BaseVehicleLocation.loc)
    VehicleLocationRecord.fromEntity(BaseVehicleLocation).save
    VehicleLocationRecord.addUpdate(BaseVehicleLocation)

    println("Loc: " + VehicleLocationRecord.findAll(0).loc.value)

    val entityFromPersistedRecord = VehicleLocationRecord.findAll(0).toEntity
    entityFromPersistedRecord should equal(BaseVehicleLocation)
  }
}