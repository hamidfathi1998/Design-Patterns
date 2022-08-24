package ir.hfathi.designpattern.structuralPatterns.bridge

interface DrawAPI {
    fun drawCircle(radius: Int, x: Int, y: Int)
}

class RedCircle : DrawAPI {
    override fun drawCircle(radius: Int, x: Int, y: Int) {
        println("Drawing Circle[ color: red, radius: $radius, x,y: $x, $y]")
    }
}

class BlueCircle : DrawAPI {
    override fun drawCircle(radius: Int, x: Int, y: Int) {
        println("Drawing Circle[ color: blue, radius: $radius, x,y: $x, $y]")
    }
}

abstract class Shape(drawAPI: DrawAPI) {
    private var drawAPI: DrawAPI? = drawAPI

    abstract fun draw()
}

class Circle(
    private var radius: Int,
    private var x: Int,
    private var y: Int,
    private val drawAPI: DrawAPI
) : Shape(drawAPI = drawAPI) {

    override fun draw() {
        drawAPI.drawCircle(radius, x, y)
    }
}
