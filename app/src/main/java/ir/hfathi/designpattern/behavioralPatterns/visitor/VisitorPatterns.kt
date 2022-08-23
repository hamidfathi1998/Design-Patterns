package ir.hfathi.designpattern.behavioralPatterns.visitor

interface PlanetVisitor {
    fun visit(planet: PlanetAlderaan)
    fun visit(planet: PlanetCoruscant)
    fun visit(planet: PlanetTatooine)
    fun visit(planet: MoonJedah)
}
interface Planet {
    fun accept(visitor: PlanetVisitor)
}
class MoonJedah : Planet {
    override fun accept(visitor: PlanetVisitor) = visitor.visit(planet = this)
}
class PlanetAlderaan : Planet {
    override fun accept(visitor: PlanetVisitor) = visitor.visit(planet = this)
}
class PlanetCoruscant : Planet {
    override fun accept(visitor: PlanetVisitor) = visitor.visit(planet = this)
}
class PlanetTatooine : Planet {
    override fun accept(visitor: PlanetVisitor) = visitor.visit(planet = this)
}

class NameVisitor(var name: String = "") : PlanetVisitor {
    override fun visit(planet: PlanetAlderaan) {
        name = "Alderaan"
    }
    override fun visit(planet: PlanetCoruscant) {
        name = "Coruscant"
    }
    override fun visit(planet: PlanetTatooine) {
        name = "Tatooine"
    }
    override fun visit(planet: MoonJedah) {
        name = "Jedah"
    }
}