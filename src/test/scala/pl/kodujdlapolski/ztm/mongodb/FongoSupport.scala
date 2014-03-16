package pl.kodujdlapolski.ztm.mongodb

import org.scalatest.{BeforeAndAfterAll, Suite}
import com.github.fakemongo.Fongo
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}

trait FongoSupport extends BeforeAndAfterAll {
  this: Suite =>

  private def newUuid() = java.util.UUID.randomUUID.toString

  override protected def beforeAll() = {
    super.beforeAll()

    val fongo = new Fongo(newUuid())
    val mongo = fongo.getMongo
    MongoDB.defineDb(DefaultMongoIdentifier, mongo, newUuid())
  }

  override protected def afterAll() = {
    MongoDB.close
    super.afterAll()
  }

}