package ir.hfathi.designpattern.structuralPatterns.bridge

// Abstraction
abstract class MShape     //constructor with implementor as input argument
    (  //Composition - implementor
    protected var color: Color?
) {
    abstract fun applyColorShape()
}

// Refined Abstraction
class Triangle(c: Color?) : MShape(c) {
    override fun applyColorShape() {
        print("Triangle filled with color ")
        color?.applyColor()
    }
}

class Pentagon(c: Color?) : MShape(c) {
    override fun applyColorShape() {
        print("Pentagon filled with color ")
        color?.applyColor()
    }
}

