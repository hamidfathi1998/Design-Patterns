package ir.hfathi.designpattern.behavioralPatterns.commandDesign

class UniversalRemote {

    // here we will have a complex electronic circuit :-)
    // that will maintain current device
    fun getActiveDevice() : ConsumerElectronics{
        val tv = Television()
        return tv
    }
}