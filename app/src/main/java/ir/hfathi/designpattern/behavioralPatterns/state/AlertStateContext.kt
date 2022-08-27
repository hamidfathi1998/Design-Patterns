package ir.hfathi.designpattern.behavioralPatterns.state

class AlertStateContext {
    private var currentState: MobileAlertState? = null

    init {
        currentState = Vibration()
    }

    fun setState(state: MobileAlertState) {
        currentState = state
    }

    fun alertState() {
        currentState!!.alert(this)
    }
}