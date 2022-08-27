package ir.hfathi.designpattern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.hfathi.designpattern.behavioralPatterns.singletion.Singleton
import ir.hfathi.designpattern.behavioralPatterns.state.AlertStateContext
import ir.hfathi.designpattern.behavioralPatterns.state.Silent
import ir.hfathi.designpattern.behavioralPatterns.state.Sound
import ir.hfathi.designpattern.behavioralPatterns.visitor.*
import ir.hfathi.designpattern.structuralPatterns.bridge.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setUpSingleton()
//        setUpVisitor()

//        setUpBridge()
//        setUpBridge2()
//        setUpBridge3()

//        setUpState()
    }

    private fun setUpState() {
        val stateContext = AlertStateContext()
        stateContext.alertState()
        stateContext.alertState()
        stateContext.setState(Silent())
        stateContext.alertState()
        stateContext.alertState()
        stateContext.setState(Sound())
        stateContext.alertState()
    }

    private fun setUpBridge() {
        val tvRemoteControl = RemoteControl(appliance = TV())
        tvRemoteControl.turnOn()
        val fancyVacuumCleanerRemoteControl = RemoteControl(appliance = VacuumCleaner())
        fancyVacuumCleanerRemoteControl.turnOn()
    }

    private fun setUpBridge2() {
        val redCircle: Shape = Circle(100, 100, 10, RedCircle())
        redCircle.draw()

        val blueCircle: Shape = Circle(100, 100, 10, BlueCircle())
        blueCircle.draw()
    }

    private fun setUpBridge3() {
        val tri: MShape = Triangle(RedColor())
        tri.applyColorShape()

        val tri2: MShape = Triangle(GreenColor())
        tri2.applyColorShape()

        val pen : MShape = Pentagon(RedColor())
        pen.applyColorShape()

        val pen2 : MShape = Pentagon(GreenColor())
        pen2.applyColorShape()
    }

    private fun setUpVisitor() {
        val planets =
            mutableListOf(PlanetAlderaan(), PlanetCoruscant(), PlanetTatooine(), MoonJedah())
        val visitor = NameVisitor()
        planets.forEach {
            it.accept(visitor)
            println(visitor.name)
        }
    }

    private fun setUpSingleton() {
        val first = Singleton.instance  // This (Singleton@7daf6ecc) is a
        // singleton
        first.b = "hello singleton"

        val second = Singleton.instance
        println(first.b)        // hello singleton
        println(second.b)        // hello singleton
    }
}