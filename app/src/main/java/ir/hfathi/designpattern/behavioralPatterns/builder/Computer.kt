package ir.hfathi.designpattern.behavioralPatterns.builder

class Computer {
    var OS: String
    var RAM: Int
    var screenSize: Double
    var externalMouse: Boolean
    var externalKeyboard: Boolean
    var battery: String

    constructor(
        OS: String, RAM: Int, screenSize: Double,
        externalMouse: Boolean, externalKeyboard: Boolean, battery: String
    ) {
        this.OS = OS
        this.RAM = RAM
        this.screenSize = screenSize
        this.externalMouse = externalMouse
        this.externalKeyboard = externalKeyboard
        this.battery = battery
    }

    override fun toString(): String {
        return ("The required configuration is :" +
                " OS : $OS " +
                " RAM : $RAM " +
                " screenSize : screenSize " +
                " externalMouse : $externalMouse " +
                " externalKeyboard : $externalKeyboard " +
                " battery : $battery"
                )
    }
}