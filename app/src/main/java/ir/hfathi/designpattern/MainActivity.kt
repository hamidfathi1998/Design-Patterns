package ir.hfathi.designpattern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.hfathi.designpattern.behavioralPatterns.builder.Computer
import ir.hfathi.designpattern.behavioralPatterns.chainOfResponsibility.ATM
import ir.hfathi.designpattern.behavioralPatterns.chainOfResponsibility.MoneyPile
import ir.hfathi.designpattern.behavioralPatterns.commandDesign.*
import ir.hfathi.designpattern.behavioralPatterns.iterator.Novella
import ir.hfathi.designpattern.behavioralPatterns.iterator.Novellas
import ir.hfathi.designpattern.behavioralPatterns.listener.PrintingTextChangedListener
import ir.hfathi.designpattern.behavioralPatterns.listener.TextView
import ir.hfathi.designpattern.behavioralPatterns.singletion.Singleton
import ir.hfathi.designpattern.behavioralPatterns.state.AlertStateContext
import ir.hfathi.designpattern.behavioralPatterns.state.Silent
import ir.hfathi.designpattern.behavioralPatterns.state.Sound
import ir.hfathi.designpattern.behavioralPatterns.strategy.booking.CarBookingStrategy
import ir.hfathi.designpattern.behavioralPatterns.strategy.booking.Customer
import ir.hfathi.designpattern.behavioralPatterns.strategy.booking.TrainBookingStrategy
import ir.hfathi.designpattern.behavioralPatterns.strategy.printer.Printer
import ir.hfathi.designpattern.behavioralPatterns.visitor.*
import ir.hfathi.designpattern.structuralPatterns.adapter.CelsiusTemperature
import ir.hfathi.designpattern.structuralPatterns.adapter.FahrenheitTemperature
import ir.hfathi.designpattern.structuralPatterns.bridge.*
import ir.hfathi.designpattern.structuralPatterns.composite.CompositeGraphic
import ir.hfathi.designpattern.structuralPatterns.composite.Ellipse
import ir.hfathi.designpattern.structuralPatterns.composite.Square
import ir.hfathi.designpattern.structuralPatterns.facade.FComputer


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

//        setUpAdapter()

//        setupBookingStrategy()
//        setupPrinterStrategy()

//        setupIteratorPattern()

//        setupListenerPattern()
//        setupBuilderPattern()

//        setupChainOfResponsibility()

//        setupCommandPattern()

//        setupCompositePattern()

        setupFacadePattern()
    }

    private fun setupFacadePattern() {
        val computer = FComputer()
        computer.start()
    }

    private fun setupCompositePattern() {
        //Initialize four ellipses
        val ellipse1 = Ellipse()
        val ellipse2 = Ellipse()
        val ellipse3 = Ellipse()
        val ellipse4 = Ellipse()
        //Initialize four squares
        val square1 = Square()
        val square2 = Square()
        val square3 = Square()
        val square4 = Square()
        //Initialize three composite graphics
        val graphic = CompositeGraphic()
        val graphic1 = CompositeGraphic()
        val graphic2 = CompositeGraphic()
        //Composes the graphics
        graphic1.add(ellipse1)
        graphic1.add(ellipse2)
        graphic1.add(square1)
        graphic1.add(ellipse3)
        graphic2.add(ellipse4)
        graphic2.add(square2)
        graphic2.add(square3)
        graphic2.add(square4)
        graphic.add(graphic1)
        graphic.add(graphic2)
        //Prints the complete graphic
        graphic.print()
    }

    private fun setupCommandPattern() {
        // OnCommand is instantiated based on active device supplied by Remote
        val ce = UniversalRemote().getActiveDevice()
        val onCommand = OnCommand(ce)
        val onButton = Button(onCommand)
        onButton.click()

        val tv = Television()
        val ss = SoundSystem()
        val all = ArrayList<ConsumerElectronics>()
        all.add(tv)
        all.add(ss)
        val muteAll = MuteAllCommand(all)
        val muteAllButton = Button(muteAll)
        muteAllButton.click()
    }

    private fun setupChainOfResponsibility() {
        val ten = MoneyPile(value = 10, quantity = 6, nextPile = null) // 60
        val twenty = MoneyPile(value = 20, quantity = 2, nextPile = ten) // 40
        val fifty = MoneyPile(value = 50, quantity = 2, nextPile = twenty) // 100
        val hundred = MoneyPile(value = 100, quantity = 1, nextPile = fifty) // 100
        val atm = ATM(moneyPile = hundred)
        atm.canWithdraw(amount = 310) // Cannot because behavioral.ATM has only 300
        atm.canWithdraw(amount = 100) // Can withdraw - 1x100
        atm.canWithdraw(amount = 165) // Cannot withdraw because behavioral.ATM doesn't has bill with value of 5
        atm.canWithdraw(amount = 30)  // Can withdraw - 1x20, 2x10
    }

    private fun setupBuilderPattern() {
        val comp = Computer("Windows", 8, 14.5, true, false, "Inbulit")
        println(comp)
    }

    private fun setupListenerPattern() {
        val textView = TextView()
        textView.listener = PrintingTextChangedListener()
        textView.text = "Lorem ipsum"
        textView.text = "dolor sit amet"
    }

    private fun setupIteratorPattern() {
        val novellas = Novellas(mutableListOf(Novella("Test1"), Novella("Test2")))
        novellas.forEach { println(it.name) }
    }

    private fun setupPrinterStrategy() {
        val lowerCaseFormatter: (String) -> String = String::toLowerCase
        val upperCaseFormatter: (String) -> String = String::toUpperCase

        val lower = Printer(strategy = lowerCaseFormatter)
        println(lower.print("O tempora, o mores!"))
        val upper = Printer(strategy = upperCaseFormatter)
        println(upper.print("O tempora, o mores!"))
    }

    private fun setupBookingStrategy() {
        //CarBooking Strategy
        val customer = Customer(CarBookingStrategy())
        var fare = customer.calculateFare(5)
        println(fare)

        //TrainBooking Strategy
        customer.bookingStrategy = TrainBookingStrategy()
        fare = customer.calculateFare(5)
        println(fare)
    }

    private fun setUpAdapter() {
        val celsiusTemperature = CelsiusTemperature(0.0)
        val fahrenheitTemperature = FahrenheitTemperature(celsiusTemperature)
        celsiusTemperature.temperature = 36.6
        println("${celsiusTemperature.temperature} C -> ${fahrenheitTemperature.temperature} F")
        fahrenheitTemperature.temperature = 100.0
        println("${fahrenheitTemperature.temperature} F -> ${celsiusTemperature.temperature} C")
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