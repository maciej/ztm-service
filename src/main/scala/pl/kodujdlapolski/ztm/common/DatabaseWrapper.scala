package pl.kodujdlapolski.ztm.common

import scala.slick.driver.MySQLDriver.simple._

class TimetableDb(val db : Database) extends DatabaseWrapper
class StatsDb(val db : Database) extends DatabaseWrapper
class InfoDb(val db: Database) extends DatabaseWrapper

trait DatabaseWrapper {

  val db : Database

  def withSession[T](f: Session => T): T  = db.withSession(f)

  def withTransaction[T](f: Session => T): T = db.withTransaction(f)

  def withDynSession[T](f: => T): T = db.withDynSession(f)
}
