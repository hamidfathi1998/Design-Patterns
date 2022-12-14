package ir.hfathi.designpattern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.hfathi.designpattern.creationalPatterns.builder.Computer
import ir.hfathi.designpattern.behavioralPatterns.chainOfResponsibility.ATM
import ir.hfathi.designpattern.behavioralPatterns.chainOfResponsibility.MoneyPile
import ir.hfathi.designpattern.behavioralPatterns.commandDesign.*
import ir.hfathi.designpattern.behavioralPatterns.interpreter.AddExpression
import ir.hfathi.designpattern.behavioralPatterns.interpreter.IntegerContext
import ir.hfathi.designpattern.behavioralPatterns.interpreter.IntegerVariableExpression
import ir.hfathi.designpattern.behavioralPatterns.iterator.Novella
import ir.hfathi.designpattern.behavioralPatterns.iterator.Novellas
import ir.hfathi.designpattern.behavioralPatterns.listener.PrintingTextChangedListener
import ir.hfathi.designpattern.behavioralPatterns.listener.TextView
import ir.hfathi.designpattern.behavioralPatterns.mediator.ATCMediator
import ir.hfathi.designpattern.behavioralPatterns.mediator.Flight
import ir.hfathi.designpattern.behavioralPatterns.mediator.Runway
import ir.hfathi.designpattern.behavioralPatterns.memento.Caretaker
import ir.hfathi.designpattern.behavioralPatterns.memento.Originator
import ir.hfathi.designpattern.behavioralPatterns.observer.Observer
import ir.hfathi.designpattern.behavioralPatterns.observer.User
import ir.hfathi.designpattern.creationalPatterns.singletion.Singleton
import ir.hfathi.designpattern.behavioralPatterns.state.AlertStateContext
import ir.hfathi.designpattern.behavioralPatterns.state.Silent
import ir.hfathi.designpattern.behavioralPatterns.state.Sound
import ir.hfathi.designpattern.behavioralPatterns.strategy.booking.CarBookingStrategy
import ir.hfathi.designpattern.behavioralPatterns.strategy.booking.Customer
import ir.hfathi.designpattern.behavioralPatterns.strategy.booking.TrainBookingStrategy
import ir.hfathi.designpattern.behavioralPatterns.strategy.printer.Printer
import ir.hfathi.designpattern.behavioralPatterns.visitor.*
import ir.hfathi.designpattern.creationalPatterns.abstractFactory.CarFactory
import ir.hfathi.designpattern.creationalPatterns.abstractFactory.CarType
import ir.hfathi.designpattern.creationalPatterns.factory.Country
import ir.hfathi.designpattern.creationalPatterns.factory.Euro
import ir.hfathi.designpattern.creationalPatterns.factory.ICurrency
import ir.hfathi.designpattern.creationalPatterns.factory.UnitedStatesDollar
import ir.hfathi.designpattern.creationalPatterns.prototype.Bike
import ir.hfathi.designpattern.structuralPatterns.adapter.CelsiusTemperature
import ir.hfathi.designpattern.structuralPatterns.adapter.FahrenheitTemperature
import ir.hfathi.designpattern.structuralPatterns.bridge.*
import ir.hfathi.designpattern.structuralPatterns.composite.CompositeGraphic
import ir.hfathi.designpattern.structuralPatterns.composite.Ellipse
import ir.hfathi.designpattern.structuralPatterns.composite.Square
import ir.hfathi.designpattern.structuralPatterns.decorator.BananaMilkShake
import ir.hfathi.designpattern.structuralPatterns.decorator.ConcreteMilkShake
import ir.hfathi.designpattern.structuralPatterns.decorator.PeanutButterMilkShake
import ir.hfathi.designpattern.structuralPatterns.facade.FComputer
import ir.hfathi.designpattern.structuralPatterns.proxy.SecuredFile


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Behavioral Patterns
        // -----------------------------------------------------------------------------
        setupListenerPattern()

        setupObserverPattern()

        setupInterpreterPattern()

        setupIteratorPattern()

        setupBookingStrategyPatternSampleOne()
        setupPrinterStrategyPatternSampleTwo()

        setupCommandPattern()

        setUpStatePattern()

        setupChainOfResponsibilityPattern()

        setUpVisitorPattern()

        setupMediatorPattern()

        setupMementoPattern()

        // Creational  Patterns
        // -----------------------------------------------------------------------------
        setupBuilderPattern()

        setupFactoryPattern()

        setUpSingletonPattern()

        setupAbstractFactoryPattern()

        setupPrototypePattern()

        // Structural Patterns
        // -----------------------------------------------------------------------------
        setUpAdapterPattern()

        setUpBridgePatternSampleOne()
        setUpBridgePatternSampleTwo()
        setUpBridgePatternSampleThree()

        setupDecoratorPattern()

        setupFacadePattern()

        setupProxyPattern()

        setupCompositePattern()
    }

    //region Behavioral Patterns
    private fun setupListenerPattern() {
        val textView = TextView()
        textView.listener = PrintingTextChangedListener()
        textView.text = "Lorem ipsum"
        textView.text = "dolor sit amet"
    }

    private fun setupObserverPattern() {
        val observer = Observer()
        val user = User(observer)

        user.name = "test"
    }

    private fun setupInterpreterPattern() {
        val context = IntegerContext()

        val a = IntegerVariableExpression(name = 'A')
        val b = IntegerVariableExpression(name = 'B')
        val c = IntegerVariableExpression(name = 'C')

        val expression = AddExpression(operand1 = a, operand2 = AddExpression(operand1 = b, operand2 = c)) // a + (b + c)
        context.assign(expression = a, value = 2)
        context.assign(expression = b, value = 1)
        context.assign(expression = c, value = 3)

        println(expression.evaluate(context))
    }

    private fun setupIteratorPattern() {
        val novellas = Novellas(mutableListOf(Novella("Test1"), Novella("Test2")))
        novellas.forEach { println(it.name) }
    }

    private fun setupPrinterStrategyPatternSampleTwo() {
        val lowerCaseFormatter: (String) -> String = String::toLowerCase
        val upperCaseFormatter: (String) -> String = String::toUpperCase

        val lower = Printer(strategy = lowerCaseFormatter)
        println(lower.print("O tempora, o mores!"))
        val upper = Printer(strategy = upperCaseFormatter)
        println(upper.print("O tempora, o mores!"))
    }

    private fun setupBookingStrategyPatternSampleOne() {
        //CarBooking Strategy
        val customer = Customer(CarBookingStrategy())
        var fare = customer.calculateFare(5)
        println(fare)

        //TrainBooking Strategy
        customer.bookingStrategy = TrainBookingStrategy()
        fare = customer.calculateFare(5)
        println(fare)
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

    private fun setUpStatePattern() {
        val stateContext = AlertStateContext()
        stateContext.alertState()
        stateContext.alertState()
        stateContext.setState(Silent())
        stateContext.alertState()
        stateContext.alertState()
        stateContext.setState(Sound())
        stateContext.alertState()
    }

    private fun setupChainOfResponsibilityPattern() {
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

    private fun setUpVisitorPattern() {
        val planets =
            mutableListOf(PlanetAlderaan(), PlanetCoruscant(), PlanetTatooine(), MoonJedah())
        val visitor = NameVisitor()
        planets.forEach {
            it.accept(visitor)
            println(visitor.name)
        }
    }

    private fun setupMediatorPattern() {
        val atcMediator = ATCMediator()
        val sparrow101 = Flight(atcMediator)
        val mainRunway = Runway(atcMediator)
        atcMediator.registerFlight(sparrow101)
        atcMediator.registerRunway(mainRunway)
        sparrow101.getReady()
        mainRunway.land()
        sparrow101.land()
    }

    private fun setupMementoPattern() {
        val originator = Originator()
        originator.state = "Ironman"
        var memento = originator.createMemento()
        val caretaker = Caretaker()
        caretaker.addMemento(memento)

        originator.state = "Captain America"
        originator.state = "Hulk"
        memento = originator.createMemento()
        caretaker.addMemento(memento)
        originator.state = "Thor"
        println("Originator Current State: " + originator.state!!)
        println("Originator restoring to previous state...")
        memento = caretaker.getMemento(1)
        originator.setMemento(memento)
        println("Originator Current State: " + originator.state!!)
        println("Again restoring to previous state...")
        memento = caretaker.getMemento(0)
        originator.setMemento(memento)
        println("Originator Current State: " + originator.state!!)
    }
    //endregion

    //region Creational  Patterns
    private fun setupBuilderPattern() {
        val comp = Computer("Windows", 8, 14.5, true, false, "Inbulit")
        println(comp)
    }

    //region Factory Pattern setup
    private fun setupFactoryPattern() {
        val noCurrencyCode = "I am not Creative, so Currency Code Available"

        println(currency(Country.Greece)?.code() ?: noCurrencyCode)
        println(currency(Country.Spain)?.code() ?: noCurrencyCode)
        println(currency(Country.UnitedStates)?.code() ?: noCurrencyCode)
        println(currency(Country.UK)?.code() ?: noCurrencyCode)
    }

    private fun currency(country: Country): ICurrency? {
        return when (country) {
            Country.Spain, Country.Greece -> Euro()
            Country.UnitedStates -> UnitedStatesDollar()
            else -> null
        }
    }
    //endregion

    private fun setUpSingletonPattern() {
        val first = Singleton.instance  // This (Singleton@7daf6ecc) is a
        // singleton
        first.b = "hello singleton"

        val second = Singleton.instance
        println(first.b)        // hello singleton
        println(second.b)        // hello singleton
    }

    private fun setupAbstractFactoryPattern() {
        println(CarFactory().buildCar(CarType.MICRO))
        println(CarFactory().buildCar(CarType.MINI))
        println(CarFactory().buildCar(CarType.LUXURY))
    }

    //region Prototype pattern setup
    private fun setupPrototypePattern() {
        val bike = Bike()
        val basicBike = bike.clone()
        val advancedBike = makeJaguar(basicBike)
        println("Prototype Design Pattern: " + advancedBike.model!!)
    }

    private fun makeJaguar(basicBike: Bike): Bike {
        basicBike.makeAdvanced()
        return basicBike
    }
    //endregion
    //endregion

    //region Structural Patterns
    private fun setUpAdapterPattern() {
        val celsiusTemperature = CelsiusTemperature(0.0)
        val fahrenheitTemperature = FahrenheitTemperature(celsiusTemperature)
        celsiusTemperature.temperature = 36.6
        println("${celsiusTemperature.temperature} C -> ${fahrenheitTemperature.temperature} F")
        fahrenheitTemperature.temperature = 100.0
        println("${fahrenheitTemperature.temperature} F -> ${celsiusTemperature.temperature} C")
    }

    private fun setUpBridgePatternSampleOne() {
        val tvRemoteControl = RemoteControl(appliance = TV())
        tvRemoteControl.turnOn()
        val fancyVacuumCleanerRemoteControl = RemoteControl(appliance = VacuumCleaner())
        fancyVacuumCleanerRemoteControl.turnOn()
    }

    private fun setUpBridgePatternSampleTwo() {
        val redCircle: Shape = Circle(100, 100, 10, RedCircle())
        redCircle.draw()

        val blueCircle: Shape = Circle(100, 100, 10, BlueCircle())
        blueCircle.draw()
    }

    private fun setUpBridgePatternSampleThree() {
        val tri: MShape = Triangle(RedColor())
        tri.applyColorShape()

        val tri2: MShape = Triangle(GreenColor())
        tri2.applyColorShape()

        val pen : MShape = Pentagon(RedColor())
        pen.applyColorShape()

        val pen2 : MShape = Pentagon(GreenColor())
        pen2.applyColorShape()
    }

    private fun setupDecoratorPattern() {
        val peanutMilkShake = PeanutButterMilkShake(ConcreteMilkShake())
        peanutMilkShake.getTaste()
        val bananaMilkShake = BananaMilkShake(ConcreteMilkShake())
        bananaMilkShake.getTaste()
    }

    private fun setupFacadePattern() {
        val computer = FComputer()
        computer.start()
    }

    private fun setupProxyPattern() {
        val securedFile = SecuredFile()
        securedFile.read("readme.md")

        securedFile.password = "secret"
        securedFile.read("readme.md")
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
    //endregion
}