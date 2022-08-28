package ir.hfathi.designpattern.behavioralPatterns.iterator

class Novella(val name: String)
class Novellas(private val novellas: MutableList<Novella> = mutableListOf()) : Iterable<Novella> {
    override fun iterator(): Iterator<Novella> = NovellasIterator(novellas)
}
class NovellasIterator(private val novellas: MutableList<Novella> = mutableListOf(), var current: Int = 0) : Iterator<Novella> {
    override fun hasNext(): Boolean = novellas.size > current
    override fun next(): Novella {
        val novella = novellas[current]
        current++
        return novella
    }
}