package pl.kodujdlapolski.ztm.api.v1_0

import pl.kodujdlapolski.ztm.records.VehicleTypes._
import org.joda.time.DateTime
import pl.kodujdlapolski.ztm.records.VehicleLocation


case class VehicleLocationView(vehicleType: VehicleType, taborowy: String, line: String, brigade: String,
                               latitude: Float, longitude: Float, lastUpdate: DateTime)

object VehicleLocationView {
  def fromEntity(vl: VehicleLocation) = VehicleLocationView(
    vehicleType = vl.vehicleType,
    taborowy = vl.train,
    line = vl.line,
    brigade = vl.brigade,
    latitude = vl.latitude,
    longitude = vl.longitude,
    lastUpdate = vl.lastUpdate
  )
}
