package ir.hfathi.designpattern.creationalPatterns.abstractFactory

fun buildCar(model: CarType): Car? {
    var car: Car? = null
    when (model) {
        CarType.MICRO -> car = MicroCar(Location.DEFAULT)

        CarType.MINI -> car = MiniCar(Location.DEFAULT)

        CarType.LUXURY -> car = LuxuryCar(Location.DEFAULT)

        else -> {
        }
    }
    return car
}