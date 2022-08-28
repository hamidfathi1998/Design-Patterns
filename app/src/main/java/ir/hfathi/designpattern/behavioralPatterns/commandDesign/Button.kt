package ir.hfathi.designpattern.behavioralPatterns.commandDesign

class Button(var c: Command) {
    fun click() {
        c.execute()
    }
}