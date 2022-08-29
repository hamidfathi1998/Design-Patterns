package ir.hfathi.designpattern.behavioralPatterns.interpreter

class IntegerContext(var data: MutableMap<Char,Int> = mutableMapOf()) {
    fun lookup(name: Char): Int = data[name]!!

    fun assign(expression: IntegerVariableExpression, value: Int) {
        data[expression.name] = value
    }
}