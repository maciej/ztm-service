package pl.kodujdlapolski.ztm.records

/*
 * +------------+------------------+
 * | oznaczenie | opis             |
 * +------------+------------------+
 * |          1 | Kabaty           |
 * |          2 | Natolin          |
 * |          3 | Imielin          |
 * |          4 | Stoklosy         |
 * |          5 | Ursynow          |
 * |          6 | Sluzew           |
 * |          7 | Wilanowska       |
 * |          8 | Wierzbno         |
 * |          9 | Raclawicka       |
 * |         10 | Pole Mokotowskie |
 * |         11 | Politechnika     |
 * |         13 | Centrum          |
 * |         14 | Swietokrzyska    |
 * |         15 | Ratusz Arsenal   |
 * |         17 | Dworzec Gdanski  |
 * |         18 | Plac Wilsona     |
 * |         19 | Marymont         |
 * |         20 | Slodowiec        |
 * |         21 | Stare Bielany    |
 * |         22 | Wawrzyszew       |
 * |         23 | Mlociny          |
 * +------------+------------------+
 */

object Stations extends Enumeration {
  type Station  = Value

  val Kabaty = Value(1, "Kabaty")
  val Natolin = Value(2, "Natolin")
  val Imielin = Value(3, "Imielin")
  val Stoklosy = Value(4, "Stoklosy")
  val Ursynow = Value(5, "Ursynów")
  val Sluzew = Value(6, "Służew")
  val Wilanowska = Value(7, "Wilanowska")
  val Wierzbno = Value(8, "Wierzbno")
  val Raclawicka = Value(9, "Racławicka")
  val PoleMokotowskie = Value(10, "Pole Mokotowskie")
  val Politechnika = Value(11, "Politechnika")
  val Centrum = Value(13, "Centrum")
  val Swietokrzyska = Value(14, "Świetokrzyska")
  val RatuszArsenal = Value(15, "Ratusz Arsenał")
  val DworzecGdanski = Value(17, "Dworzec Gdański")
  val PlacWilsona = Value(18, "Plac Wilsona")
  val Marymont = Value(19, "Marymont")
  val Slodowiec = Value(20, "Słodowiec")
  val StareBielany = Value(21, "Stare Bielany")
  val Wawrzysze = Value(22, "Wawrzyszew")
  val Mlociny = Value(23, "Młociny")

}
