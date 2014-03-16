package pl.kodujdlapolski.ztm.mongodb

import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import net.liftweb.mongodb.record.field.{MongoCaseClassField, ObjectIdPk}
import net.liftweb.record.field.{StringField, EnumField}
import pl.kodujdlapolski.ztm.records.{VehicleLocation, VehicleTypes}
import com.foursquare.rogue.LatLong
import com.softwaremill.thegarden.mongodb.field.DateField
import com.softwaremill.thegarden.mongodb.MongodbIndexProvider
import org.joda.time.Minutes.{minutes}
import org.joda.time.{Minutes, DateTime}

/*
 * While working on this source file I found this:
  * http://chimera.labs.oreilly.com/books/1234000000030/ch08.html
 */

class VehicleLocationRecord extends MongoRecord[VehicleLocationRecord] with ObjectIdPk[VehicleLocationRecord] {
  override def meta = VehicleLocationRecord

  /* Elements */
  object vehicleType extends EnumField(this, VehicleTypes)

  object loc extends MongoCaseClassField[VehicleLocationRecord, LatLong](this)

  object brigade extends StringField(this, maxLength = 20)

  object line extends StringField(this, maxLength = 20)

  /* Numer boczny */
  object train extends StringField(this, maxLength = 20)

  object updatedAt extends DateField(this)

  /* End of elements */

  def toEntity = VehicleLocationConverters.toEntity(this)
}

object VehicleLocationRecord extends VehicleLocationRecord with MongoMetaRecord[VehicleLocationRecord] with MongodbIndexProvider {

  override val collectionName = "vehicle_locations"

  val FutureLocationsEpsilon: Minutes = minutes(5)

  def fromEntity(entity: VehicleLocation) = VehicleLocationConverters.toRecord(entity)

  override def ensureIndexes() = {
    import net.liftweb.json.JsonDSL._
    ensureIndex((vehicleType.name -> 1) ~ (train.name -> 1) ~ (updatedAt.name -> 1),
      "unique" -> 1)
  }

  def addUpdate(location: VehicleLocation) = {
    import com.mongodb.casbah.commons.{MongoDBObject => DBObject}
    val query = DBObject(
      vehicleType.name -> location.vehicleType.id,
      train.name -> location.train,
      updatedAt.name -> location.lastUpdate.toDate
    )
    val updateClause = DBObject(
      line.name -> location.line,
      loc.name -> DBObject("lat" -> location.loc.lat, "long" -> location.loc.long),
      brigade.name -> location.brigade
    )
    upsert(query, new DBObject(query) ++ updateClause)
  }

  def findFutureLocations(): Stream[VehicleLocation] = {
    import com.foursquare.rogue.LiftRogue._
    this.query.where(_.updatedAt after new DateTime().plus(FutureLocationsEpsilon)).fetch().toStream.map(_.toEntity)
  }
}

private[mongodb] object VehicleLocationConverters {

  def toEntity(record: VehicleLocationRecord) =
    new VehicleLocation(record.vehicleType.get,
      record.train.get,
      record.line.get,
      record.brigade.get,
      record.loc.get.lat.toFloat,
      record.loc.get.long.toFloat,
      record.updatedAt.toDateTime
    )

  def toRecord(entity: VehicleLocation) = VehicleLocationRecord.createRecord.
    vehicleType(entity.vehicleType).
    loc(LatLong(entity.latitude, entity.longitude)).
    brigade(entity.brigade).line(entity.line).
    train(entity.taborowy).
    updatedAt(entity.lastUpdate)
}