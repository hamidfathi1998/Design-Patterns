package ir.hfathi.designpattern.creationalPatterns.abstractFactory

class MiniCar(location: Location) : Car(CarType.MINI, location) {
    init {
        construct()
    }

    override fun construct() {
        println("Connecting to Mini car")
    }
}
