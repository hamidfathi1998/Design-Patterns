package ir.hfathi.designpattern.behavioralPatterns.state

interface MobileAlertState {
    fun alert(ctx: AlertStateContext)
}