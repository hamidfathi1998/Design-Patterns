package ir.hfathi.designpattern.behavioralPatterns.strategy.printer

class Printer(val strategy: (String) -> String) {
    fun print(string: String): String = strategy(string)
}