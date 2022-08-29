package ir.hfathi.designpattern.creationalPatterns.factory

class UnitedStatesDollar : ICurrency {
    override fun symbol(): String {
        return "$"
    }
    override fun code(): String {
        return "USD"
    }
}