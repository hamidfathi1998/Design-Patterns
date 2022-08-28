package ir.hfathi.designpattern.structuralPatterns.decorator

class ConcreteMilkShake : MilkShake {
    override fun getTaste() {
        println("It’s milk !")
    }
}