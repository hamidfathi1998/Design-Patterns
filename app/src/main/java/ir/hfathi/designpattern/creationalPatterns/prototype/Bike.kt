package ir.hfathi.designpattern.creationalPatterns.prototype

open class Bike : Cloneable {
    private var gears: Int = 0
    private var bikeType: String? = null
    var model: String? = null
        private set

    init {
        bikeType = "Standard"
        model = "Leopard"
        gears = 4
    }

    public override fun clone(): Bike {
        return Bike()
    }

    fun makeAdvanced() {
        bikeType = "Advanced"
        model = "Jaguar"
        gears = 6
    }
}