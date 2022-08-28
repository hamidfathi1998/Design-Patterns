package ir.hfathi.designpattern.behavioralPatterns.chainOfResponsibility

class ATM(val moneyPile: MoneyPile) {
    fun canWithdraw(amount: Int) = println("Can withdraw: ${moneyPile.canWithdraw(withdrawAmount = amount)}")
}