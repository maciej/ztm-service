package pl.kodujdlapolski.ztm.scalatra

import org.scalatra.test.ScalatraTests
import org.scalatest.{FlatSpec, Suite, BeforeAndAfterAll}

trait ScalatraSuite extends Suite with ScalatraTests with BeforeAndAfterAll {
  override protected def beforeAll(): Unit = start()
  override protected def afterAll(): Unit = stop()
}

trait ScalatraFlatSpec extends FlatSpec with ScalatraSuite
