package ir.hfathi.designpattern.behavioralPatterns.memento

class Memento(val state: String)

class Originator {

    //this String is just for example
    //in real world application this
    //will be the object for which the state to be stored
    var state: String? = null

    fun createMemento(): Memento {
        return Memento(state!!)
    }

    fun setMemento(memento: Memento) {
        state = memento.state
    }
}

class Caretaker {
    private val statesList = ArrayList<Memento>()

    fun addMemento(m: Memento) {
        statesList.add(m)
    }

    fun getMemento(index: Int): Memento {
        return statesList.get(index)
    }
}