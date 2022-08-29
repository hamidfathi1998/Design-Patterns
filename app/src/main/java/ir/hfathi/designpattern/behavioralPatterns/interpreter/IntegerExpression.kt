package ir.hfathi.designpattern.behavioralPatterns.interpreter

interface IntegerExpression {
    fun evaluate(context: IntegerContext): Int
    fun replace(character: Char, integerExpression: IntegerExpression): IntegerExpression
    fun copied(): IntegerExpression
}