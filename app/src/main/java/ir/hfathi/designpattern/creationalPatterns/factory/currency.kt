package ir.hfathi.designpattern.creationalPatterns.factory


fun currency(country: Country): ICurrency? {
    when (country) {
        Country.Spain, Country.Greece -> return Euro()
        Country.UnitedStates -> return UnitedStatesDollar()
        else -> return null
    }
}