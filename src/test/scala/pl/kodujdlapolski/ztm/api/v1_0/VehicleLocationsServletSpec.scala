package pl.kodujdlapolski.ztm.api.v1_0

import pl.kodujdlapolski.ztm.scalatra.ScalatraFlatSpec
import org.scalatest.{BeforeAndAfterEach, ShouldMatchers}
import org.scalatest.mock.MockitoSugar
import org.scalatra.swagger.Swagger
import org.mockito.Mockito._

class VehicleLocationsServletSpec extends ScalatraFlatSpec
with ShouldMatchers with MockitoSugar with BeforeAndAfterEach{

  val swagger = mock[Swagger]

  override protected def beforeEach() = {
    super.beforeEach()
    reset(swagger)
  }

  override protected def afterEach() = super.afterEach()
}