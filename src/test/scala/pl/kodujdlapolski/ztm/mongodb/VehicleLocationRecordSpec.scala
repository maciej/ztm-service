package pl.kodujdlapolski.ztm.mongodb

import org.scalatest.{BeforeAndAfterEach, ShouldMatchers, FlatSpec}
import pl.kodujdlapolski.ztm.records.VehicleLocationRepository
import org.joda.time.DateTime
import com.softwaremill.thegarden.mongodbtest.FongoSupport

class VehicleLocationRecordSpec extends FlatSpec with FongoSupport with ShouldMatchers with BeforeAndAfterEach {

  import VehicleLocationRepository._
  import VehicleLocationRecord.FutureLocationsEpsilon

  val className = classOf[VehicleLocationRecord].getSimpleName

  val findFutureLocations = s"$className.findFutureLocations"

  override val clearDataBeforeEachTest = true

  it should "be persistable" in {
    val countBeforeSave = VehicleLocationRecord.count

    val record = VehicleLocationRecord.fromEntity(BaseVehicleLocation)
    record.save

    val countAfterSave = VehicleLocationRecord.count

    (countAfterSave - countBeforeSave) should equal(1)
  }

  s"$className.addUpdate" should "add a record if one does not exist" in {
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
    // When
    VehicleLocationRecord.fromEntity(BaseVehicleLocation).save
    VehicleLocationRecord.addUpdate(BaseVehicleLocation)

    val entityFromPersistedRecord = VehicleLocationRecord.findAll(0).toEntity
    entityFromPersistedRecord should equal(BaseVehicleLocation)
  }

  findFutureLocations should "not find locations that have been updated in the past" in {
    // Given
    val testLocation = BaseVehicleLocation.copy(lastUpdate = new DateTime().minusMinutes(2))
    VehicleLocationRecord.addUpdate(testLocation)

    // When
    val locations = VehicleLocationRecord.findFutureLocations()

    // Then
    try {
      locations.isEmpty should be(true)
    } catch {
      case e: Exception =>
        Console.err.println(s"Future locations count ${locations.size}")
        throw e
    }
  }

  findFutureLocations should s"not treat locations within ${FutureLocationsEpsilon.getMinutes} minutes as future ones" in {
    // Given
    val testLocation = BaseVehicleLocation.copy(lastUpdate = new DateTime().plus(FutureLocationsEpsilon).minusSeconds(10))
    VehicleLocationRecord.addUpdate(testLocation)

    // When
    val locations = VehicleLocationRecord.findFutureLocations()

    // Then
    locations.isEmpty should be(true)
  }

  findFutureLocations should "find location updates that come from the future" in {
    // Given
    val testLocation = BaseVehicleLocation.copy(lastUpdate = new DateTime().plus(FutureLocationsEpsilon).plusSeconds(10))
    VehicleLocationRecord.addUpdate(testLocation)

    // When
    val locations = VehicleLocationRecord.findFutureLocations()

    // Then
    locations.isEmpty should be(false)
  }
}
