package ir.hfathi.designpattern.behavioralPatterns.strategy.booking

class Customer(var bookingStrategy: BookingStrategy) {

    fun calculateFare(numOfPassengers: Int): Double {
        val fare = numOfPassengers * bookingStrategy.fare
        println("Calculating fares using $bookingStrategy")
        return fare
    }
}