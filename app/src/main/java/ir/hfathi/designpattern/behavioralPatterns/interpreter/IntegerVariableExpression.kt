package ir.hfathi.designpattern.behavioralPatterns.interpreter

class IntegerVariableExpression(val name: Char) : IntegerExpression {
    override fun evaluate(context: IntegerContext): Int = context.lookup(name = name)

    override fun replace(character: Char, integerExpression: IntegerExpression): IntegerExpression {
        if (character == this.name) return integerExpression.copied() else return IntegerVariableExpression(name = this.name)
    }

    override fun copied(): IntegerExpression = IntegerVariableExpression(name = this.name)
}