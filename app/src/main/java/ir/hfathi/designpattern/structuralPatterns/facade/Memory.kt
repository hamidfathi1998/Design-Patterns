package ir.hfathi.designpattern.structuralPatterns.facade

class Memory {
    fun load(position: Long, data: ByteArray) = println("Loading from memory position: $position")
}