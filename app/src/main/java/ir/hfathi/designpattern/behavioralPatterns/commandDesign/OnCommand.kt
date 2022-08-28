package ir.hfathi.designpattern.behavioralPatterns.commandDesign

class OnCommand(private val ce: ConsumerElectronics) : Command {

    override fun execute() {
        ce.on()
    }
}