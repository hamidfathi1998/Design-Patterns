package ir.hfathi.designpattern.structuralPatterns.bridge

// Implementor
interface Color {
    fun applyColor()
}


// Concrete Implementor
class RedColor : Color {
    override fun applyColor() {
        println("red.")
    }
}

class GreenColor : Color {
    override fun applyColor() {
        println("green.")
    }
}