package ir.hfathi.designpattern.structuralPatterns.facade

class CPU {
    fun freeze() = println("Freezing.")

    fun jump(position: Long) = println("Jump to $position.")

    fun execute() = println("Executing.")
}