package pl.kodujdlapolski.ztm.core

import com.typesafe.scalalogging.slf4j.Logging
import com.mongodb.{Mongo, MongoClient, ServerAddress}
import scala.collection.JavaConversions
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import com.softwaremill.thegarden.mongodb.MongodbIndexProvider
import pl.kodujdlapolski.ztm.mongodb.VehicleLocationRecord

trait MongoInit extends Logging {

  def mongoIndexProviders: Seq[MongodbIndexProvider]

  def initializeBlocking(mongoConfig: MongodbConfig) = {
    logger.info(s"Using Mongo servers: ${mongoConfig.mongoServers}, database: ${mongoConfig.mongoDatabase}")
    connectAndRegisterLiftRecordDb(mongoConfig)
    if (mongoConfig.ensureIndexes) {
      ensureIndexes()
    }
  }

  private def createMongo(serverList: List[ServerAddress]) = {
    // We need to use a different constructor if there's only 1 server to avoid startup exceptions where Mongo thinks
    // it's in a replica set.
    if (serverList.size == 1) {
      new MongoClient(serverList.head)
    } else {
      import JavaConversions._
      new MongoClient(serverList)
    }
  }

  def ensureIndexes() {
    logger.info("Ensuring Mongo indexes")

    mongoIndexProviders.foreach(_.ensureIndexes())
  }

  def clearIndexCache() {
    mongoIndexProviders.foreach {
      provider =>
        MongoDB.useCollection(provider.collectionName) {
          coll =>
            coll.resetIndexCache()
        }
    }
  }

  private def serversStringToServerAddresses(servers: String): scala.List[ServerAddress] = {
    servers.split(",").map(new ServerAddress(_)).toList
  }

  private def connectAndRegisterLiftRecordDb(mongoConfig: MongodbConfig): Mongo = {
    val mongo = createMongo(serversStringToServerAddresses(mongoConfig.mongoServers))
    MongoDB.defineDb(DefaultMongoIdentifier, mongo, mongoConfig.mongoDatabase)
    MongoDB.use(db => db.getStats) // MongoDB.defineDb won't crash if cannot connect to DB, so we need to execute something
    mongo
  }
}

object ZtmMongoInit extends MongoInit {
  override val mongoIndexProviders = List(VehicleLocationRecord)
}