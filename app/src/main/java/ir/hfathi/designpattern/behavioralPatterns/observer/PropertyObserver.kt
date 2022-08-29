package ir.hfathi.designpattern.behavioralPatterns.observer

interface PropertyObserver {
    fun willChange(propertyName: String, newPropertyValue: Any?)
    fun didChange(propertyName: String, oldPropertyValue: Any?)
}