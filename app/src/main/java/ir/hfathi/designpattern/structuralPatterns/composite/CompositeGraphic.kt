package ir.hfathi.designpattern.structuralPatterns.composite

/** "Composite" */
class CompositeGraphic(val graphics: ArrayList<Graphic> = ArrayList()) : Graphic {
    //Prints the graphic.
    override fun print() = graphics.forEach(Graphic::print)
    //Adds the graphic to the composition.
    fun add(graphic: Graphic) {
        graphics.add(graphic)
    }
    //Removes the graphic from the composition.
    fun remove(graphic: Graphic) {
        graphics.remove(graphic)
    }
}