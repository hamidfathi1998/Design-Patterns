package ir.hfathi.designpattern.behavioralPatterns.observer

class Observer : PropertyObserver {
    override fun willChange(propertyName: String, newPropertyValue: Any?) {
        if (newPropertyValue is String && newPropertyValue == "test") {
            println("Okay. Look. We both said a lot of things that you're going to regret.")
        }
    }

    override fun didChange(propertyName: String, oldPropertyValue: Any?) {
        if (oldPropertyValue is String && oldPropertyValue == "<no name>") {
            println("Sorry about the mess. I've really let the place go since you killed me.")
        }
    }
}