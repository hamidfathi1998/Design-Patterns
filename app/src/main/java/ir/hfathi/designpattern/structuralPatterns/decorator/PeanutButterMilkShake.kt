package ir.hfathi.designpattern.structuralPatterns.decorator

public class PeanutButterMilkShake(m:MilkShake) : MilkShakeDecorator(m){

    override public fun getTaste(){
        super.getTaste ();
        this.addTaste();
        println(" It’s Peanut butter milk shake !");
    }
    public fun addTaste(){
        println(" Adding Peanut butter flavor to the milk shake !");
    }
}