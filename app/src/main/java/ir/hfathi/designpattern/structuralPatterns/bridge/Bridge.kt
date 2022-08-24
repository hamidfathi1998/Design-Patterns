package ir.hfathi.designpattern.structuralPatterns.bridge

interface Switch {
    var appliance: Appliance
    fun turnOn()
}

// Implementor
interface Appliance {
    fun run()
}

// Refined Abstraction
class RemoteControl(override var appliance: Appliance) : Switch {
    override fun turnOn() = appliance.run()
}

// Concrete Implementor
class TV : Appliance {
    override fun run() = println("TV turned on")
}

class VacuumCleaner : Appliance {
    override fun run() = println("VacuumCleaner turned on")
}