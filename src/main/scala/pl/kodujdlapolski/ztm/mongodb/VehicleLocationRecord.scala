package pl.kodujdlapolski.ztm.mongodb

import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import net.liftweb.mongodb.record.field.{MongoCaseClassField, ObjectIdPk}
import net.liftweb.record.field.{StringField, EnumField}
import pl.kodujdlapolski.ztm.records.{VehicleLocation, VehicleTypes}
import com.foursquare.rogue.LatLong
import com.softwaremill.thegarden.mongodb.field.DateField

/*
 * While working on this source file I found this:
  * http://chimera.labs.oreilly.com/books/1234000000030/ch08.html
 */

class VehicleLocationRecord extends MongoRecord[VehicleLocationRecord] with ObjectIdPk[VehicleLocationRecord] {
  override def meta = VehicleLocationRecord

  object vehicleType extends EnumField(this, VehicleTypes)

  object loc extends MongoCaseClassField[VehicleLocationRecord, LatLong](this)

  object brigade extends StringField(this, maxLength = 20)

  object line extends StringField(this, maxLength = 20)

  object train extends StringField(this, maxLength = 20)

  object updatedAt extends DateField(this)


  def toEntity = VehicleLocationConverters.toEntity(this)
}

object VehicleLocationRecord extends VehicleLocationRecord with MongoMetaRecord[VehicleLocationRecord] {

  override val collectionName = "vehicle_locations"

  def toRecord(entity: VehicleLocation) = VehicleLocationConverters.toRecord(entity)
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