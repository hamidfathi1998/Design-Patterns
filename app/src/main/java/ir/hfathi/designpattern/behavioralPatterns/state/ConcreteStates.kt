package ir.hfathi.designpattern.behavioralPatterns.state


class Vibration : MobileAlertState {

    override fun alert(ctx: AlertStateContext) {
        println("vibration...")
    }
}

class Silent : MobileAlertState {

    override fun alert(ctx: AlertStateContext) {
        println("silent...")
    }
}

class Sound : MobileAlertState {

    override fun alert(ctx: AlertStateContext) {
        println("tu..tu..tu..tu")
    }
}