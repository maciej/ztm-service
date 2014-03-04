package pl.kodujdlapolski.ztm.procs

import scala.slick.driver.JdbcDriver.backend.Database

import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation
import Database.dynamicSession

case class EngineVersion(value: String)

class EngineVersionProc {

  implicit def tuple2EngineVersion(tuple: (String, String)) = EngineVersion(tuple._2)

  def call() = sql"{call pobierz_wersje_silnika_bazy()}".as[(String, String)]

  // http://blog.lunatech.com/2013/11/21/slick-case-classes
  def get(): EngineVersion = call().first()
}
