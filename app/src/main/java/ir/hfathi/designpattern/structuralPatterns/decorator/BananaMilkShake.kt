package ir.hfathi.designpattern.structuralPatterns.decorator

class BananaMilkShake(m:MilkShake) : MilkShakeDecorator(m){

    override public fun getTaste(){
        super.getTaste ();
        this.addTaste();
        println(" It’s Banana milk shake !");
    }
    public fun addTaste(){
        println(" Adding Banana flavor to the milk shake !");
    }
}