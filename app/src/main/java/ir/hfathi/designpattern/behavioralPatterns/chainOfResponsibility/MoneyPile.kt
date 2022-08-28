package ir.hfathi.designpattern.behavioralPatterns.chainOfResponsibility

class MoneyPile(val value: Int, var quantity: Int, var nextPile: MoneyPile?) {

    private fun canTakeSomeBill(want: Int): Boolean = (want / this.value) > 0

    fun canWithdraw(withdrawAmount: Int): Boolean {
        var amount = withdrawAmount
        var quantity = this.quantity
        while (canTakeSomeBill(want = amount)) {
            if (quantity == 0) {
                break
            }
            amount -= this.value
            quantity -= 1
        }
        if (amount <= 0) {
            return true
        }
        nextPile?.let {
            return it.canWithdraw(withdrawAmount = amount)
        }
        return false
    }

}