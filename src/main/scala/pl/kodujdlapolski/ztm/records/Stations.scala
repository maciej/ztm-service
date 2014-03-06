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

  /* I'm too lazy, add others yourself */
}
