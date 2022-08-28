package ir.hfathi.designpattern.behavioralPatterns.listener

import kotlin.properties.Delegates

class TextView {
    var listener: TextChangedListener? = null
    var text: String by Delegates.observable("") { prop, old, new ->
        listener?.onTextChanged(new)
    }
}