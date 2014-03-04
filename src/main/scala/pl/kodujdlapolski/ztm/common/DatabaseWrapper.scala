package pl.kodujdlapolski.ztm.common

import scala.slick.driver.MySQLDriver.simple._

class DatabaseWrapper(db : Database) {
  def withSession[T](f: Session => T): T  = db.withSession(f)

  def withTransaction[T](f: Session => T): T = db.withTransaction(f)

  def withDynSession[T](f: => T): T = db.withDynSession(f)
}
