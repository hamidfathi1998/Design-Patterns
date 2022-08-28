package ir.hfathi.designpattern.creationalPatterns.abstractFactory

class MicroCar(location: Location) : Car(CarType.MICRO, location) {
    init {
        construct()
    }

    override fun construct() {
        println("Connecting to Micro Car ")
    }
}