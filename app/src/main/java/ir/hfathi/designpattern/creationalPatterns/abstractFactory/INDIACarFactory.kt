package ir.hfathi.designpattern.creationalPatterns.abstractFactory

class INDIACarFactory {
    fun buildCar(model: CarType): Car? {
        var car: Car? = null
        when (model) {
            CarType.MICRO -> car = MicroCar(Location.INDIA)

            CarType.MINI -> car = MiniCar(Location.INDIA)

            CarType.LUXURY -> car = LuxuryCar(Location.INDIA)

            else -> {
            }
        }
        return car
    }
}