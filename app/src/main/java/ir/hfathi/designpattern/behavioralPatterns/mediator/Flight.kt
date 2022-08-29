package ir.hfathi.designpattern.behavioralPatterns.mediator

class Flight(private val atcMediator: IATCMediator) : Command {

    override fun land() {
        if (atcMediator.isLandingOk) {
            println("Landing done....")
            atcMediator.setLandingStatus(true)
        } else
            println("Will wait to land....")
    }

    fun getReady() {
        println("Getting ready...")
    }

}