package ir.hfathi.designpattern.behavioralPatterns.listener

class PrintingTextChangedListener : TextChangedListener {
    override fun onTextChanged(newText: String) = println("Text is changed to: $newText")
}