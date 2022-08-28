package ir.hfathi.designpattern.creationalPatterns.abstractFactory

class LuxuryCar(location: Location) : Car(CarType.LUXURY, location) {
    init {
        construct()
    }

    override fun construct() {
        println("Connecting to luxury car")
    }
}