package ir.hfathi.designpattern.behavioralPatterns.interpreter

class AddExpression(var operand1: IntegerExpression, var operand2: IntegerExpression) : IntegerExpression {
    override fun evaluate(context: IntegerContext): Int = this.operand1.evaluate(context) + this.operand2.evaluate(context)

    override fun replace(character: Char, integerExpression: IntegerExpression): IntegerExpression = AddExpression(operand1 = operand1.replace(character = character, integerExpression = integerExpression),
        operand2 = operand2.replace(character = character, integerExpression = integerExpression))

    override fun copied(): IntegerExpression = AddExpression(operand1 = this.operand1, operand2 = this.operand2)
}