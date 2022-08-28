package ir.hfathi.designpattern.creationalPatterns.abstractFactory

class CarFactory {
    fun buildCar(type: CarType): Car? {
        var car: Car? = null
        // We can add any GPS Function here which
        // read location property somewhere from configuration
        // and use location specific car factory
        // Currently I'm just using INDIA as Location
        val location = Location.INDIA

        when (location) {
            Location.USA -> car = USACarFactory.buildCar(type)

            Location.INDIA -> car = INDIACarFactory().buildCar(type)

            else -> car = buildCar(type)
        }

        return car

    }
}