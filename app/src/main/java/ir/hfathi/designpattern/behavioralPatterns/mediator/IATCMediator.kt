package ir.hfathi.designpattern.behavioralPatterns.mediator

interface IATCMediator {

    val isLandingOk: Boolean
    fun registerRunway(runway: Runway)
    fun registerFlight(flight: Flight)
    fun setLandingStatus(status: Boolean)
}