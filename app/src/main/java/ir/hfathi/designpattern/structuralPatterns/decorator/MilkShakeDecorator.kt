package ir.hfathi.designpattern.structuralPatterns.decorator

open class MilkShakeDecorator(protected var milkShake: MilkShake) : MilkShake {
    override fun getTaste() {
        this.milkShake.getTaste()
    }
}