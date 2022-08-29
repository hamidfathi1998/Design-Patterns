package ir.hfathi.designpattern.structuralPatterns.proxy

class NormalFile : File {
    override fun read(name: String) = println("Reading file: $name")
}