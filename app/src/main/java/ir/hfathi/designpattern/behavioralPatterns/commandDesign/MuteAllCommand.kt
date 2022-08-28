package ir.hfathi.designpattern.behavioralPatterns.commandDesign

class MuteAllCommand(internal var ceList: List<ConsumerElectronics>) : Command {

    override fun execute() {

        for (ce in ceList) {
            ce.mute()
        }
    }
}