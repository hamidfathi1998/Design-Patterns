package ir.hfathi.designpattern.creationalPatterns.factory

class Euro : ICurrency {
    override fun symbol(): String {
        return "€"
    }
    override fun code(): String {
        return "EUR"
    }
}
