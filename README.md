# Design Patterns In Kotlin

Project maintained by Hamid Fathi

This repository lists the design patterns written in the Kotlin programming language.


## Table of Contents
--------------------------------------------------
* [Behavioral Patterns](#behavioral)
	* [Listener](#listener)
	* [Observer](#observer)
	* [Interpreter](#Interpreter)
	* [Iterator](#Iterator)
	* [Strategy](#strategy)
	* [Command](#command)
	* [State](#state)
	* [Chain of Responsibility](#chain-of-responsibility)
	* [Visitor](#visitor)
	* [Mediator](#mediator)
	* [Memento](#memento)
* [Creational Patterns](#creational)
	* [Builder / Assembler](#builder--assembler)
	* [Factory Method](#factory-method)
	* [Singleton](#singleton)
	* [Abstract Factory](#abstract-factory)
	* [Prototype](#prototype)
* [Structural Patterns](#structural)
	* [Adapter](#adapter)
	* [Bridge](#Bridge)
	* [Decorator](#decorator)
	* [Facade](#facade)
	* [Protection Proxy](#protection-proxy)
	* [Composite](#composite)

Behavioral
==========

>In software engineering, behavioral design patterns are design patterns that identify common communication patterns between objects and realize these patterns. By doing so, these patterns increase flexibility in carrying out this communication.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Behavioral_pattern)


[Listener](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/listener)
--------

The observer pattern defines a one-to-many dependency between objects so that when one object changes state, all of its dependents are notified and updated automatically.The object which is being watched is called the subject. The objects which are watching the state changes are called observers or listeners.

#### Example
```kotlin
import kotlin.properties.Delegates
interface TextChangedListener {
    fun onTextChanged(newText: String)
}
class PrintingTextChangedListener : TextChangedListener {
    override fun onTextChanged(newText: String) = println("Text is changed to: $newText")
}
class TextView {
    var listener: TextChangedListener? = null
    var text: String by Delegates.observable("") { prop, old, new ->
        listener?.onTextChanged(new)
    }
}

```

### Usage
```kotlin
private fun setupListenerPattern(){
    val textView = TextView()
    textView.listener = PrintingTextChangedListener()
    textView.text = "Lorem ipsum"
    textView.text = "dolor sit amet"
}
```

### Output
```
Text is changed to: Lorem ipsum
Text is changed to: dolor sit amet
```


[Observer](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/observer)
--------

Observer Pattern is one of the behavioral design patterns. An Observer design pattern is useful when you are interested in the state of an object and want to get notified whenever there is any change.

In the observer pattern, the object that watches on the state of another object is called Observer and the object that is being watched is called Subject.

***Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically***

#### Example
Twitter Follow button: We can think about a celebrity who has many followers on twitter. Each of these followers wants to get all the latest updates of his/her favorite celebrity. So, he/she can follow the celebrity as long as his/her interest persists.

When he loses interest, he simply stops following that celebrity. Here we can think of the follower as an observer and the celebrity as a subject.

```kotlin
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Returns a property delegate for a read/write property that calls a specified callback function when changed.
 * @param initialValue the initial value of the property.
 * @param beforeChange the callback which is called before the change of the property.
 * @param afterChange the callback which is called after the change of the property is made. The value of the property
 *  has already been changed when this callback is invoked.
 */
inline fun <T> observable(initialValue: T,
                          crossinline beforeChange: (property: KProperty<*>, oldValue: T, newValue: T) -> Boolean,
                          crossinline afterChange: (property: KProperty<*>, oldValue: T, newValue: T) -> Unit): ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = afterChange(property, oldValue, newValue)
    override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T) = beforeChange(property, oldValue, newValue)
}

interface PropertyObserver {
    fun willChange(propertyName: String, newPropertyValue: Any?)
    fun didChange(propertyName: String, oldPropertyValue: Any?)
}

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

class User(val propertyObserver: PropertyObserver?) {
    var name: String by observable("<no name>", { prop, old, new ->
        println("Before change: $old -> $new")
        propertyObserver?.willChange(name, new)

        return@observable true
    }, { prop, old, new ->
        propertyObserver?.didChange(name, old)

        println("After change: $old -> $new")
    })
}


```

### Usage
```kotlin
private fun setupObserverPattern() {
    val observer = Observer()
    val user = User(observer)

    user.name = "test"
}
```

### Output
```
Before change: <no name> -> test
Okay. Look. We both said a lot of things that you're going to regret.
Sorry about the mess. I've really let the place go since you killed me.
After change: <no name> -> test
```


[Interpreter](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/interpreter)
--------

#### Example

```kotlin
interface IntegerExpression {
    fun evaluate(context: IntegerContext): Int
    fun replace(character: Char, integerExpression: IntegerExpression): IntegerExpression
    fun copied(): IntegerExpression
}

class IntegerContext(var data: MutableMap = mutableMapOf()) {
    fun lookup(name: Char): Int = data[name]!!

    fun assign(expression: IntegerVariableExpression, value: Int) {
        data[expression.name] = value
    }
}

class IntegerVariableExpression(val name: Char) : IntegerExpression {
    override fun evaluate(context: IntegerContext): Int = context.lookup(name = name)

    override fun replace(character: Char, integerExpression: IntegerExpression): IntegerExpression {
        if (character == this.name) return integerExpression.copied() else return IntegerVariableExpression(name = this.name)
    }

    override fun copied(): IntegerExpression = IntegerVariableExpression(name = this.name)
}

class AddExpression(var operand1: IntegerExpression, var operand2: IntegerExpression) : IntegerExpression {
    override fun evaluate(context: IntegerContext): Int = this.operand1.evaluate(context) + this.operand2.evaluate(context)

    override fun replace(character: Char, integerExpression: IntegerExpression): IntegerExpression = AddExpression(operand1 = operand1.replace(character = character, integerExpression = integerExpression),
            operand2 = operand2.replace(character = character, integerExpression = integerExpression))

    override fun copied(): IntegerExpression = AddExpression(operand1 = this.operand1, operand2 = this.operand2)
}
```

### Usage
```kotlin
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
```

### Output
```
6
```


[Iterator](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/iterator)
--------
Iterator Pattern is a relatively simple and frequently used design pattern. There are a lot of data structures/collections available in every language. Each collection must provide an iterator that lets it iterate through its objects.

#### Example

However, while doing so it should make sure that it does not expose its implementation.
```kotlin
class Novella(val name: String)
class Novellas(private val novellas: MutableList<Novella> = mutableListOf()) : Iterable<Novella> {
    override fun iterator(): Iterator<Novella> = NovellasIterator(novellas)
}
class NovellasIterator(private val novellas: MutableList<Novella> = mutableListOf(), var current: Int = 0) : Iterator<Novella> {
    override fun hasNext(): Boolean = novellas.size > current
    override fun next(): Novella {
        val novella = novellas[current]
        current++
        return novella
    }
}
```

### Usage
```kotlin
private fun setupIteratorPattern() {
    val novellas = Novellas(mutableListOf(Novella("Test1"), Novella("Test2")))
    novellas.forEach { println(it.name) }
}
```

### Output
```
Test1
Test2
```



[Strategy](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/strategy)
--------

In the Strategy pattern, a class behavior or its algorithm can be changed at run time. This type of design pattern comes under behavior pattern.In the Strategy pattern, we create objects which represent various strategies and a context object whose behavior varies as per its strategy object. The strategy object changes the executing algorithm of the context object.

#### Example
```kotlin
interface BookingStrategy {
    val fare: Double
}

class CarBookingStrategy : BookingStrategy {

    override val fare: Double = 12.5

    override fun toString(): String {
        return "CarBookingStrategy"
    }
}

class TrainBookingStrategy : BookingStrategy {

    override val fare: Double = 8.5

    override fun toString(): String {
        return "TrainBookingStrategy"
    }
}

class Customer(var bookingStrategy: BookingStrategy) {

    fun calculateFare(numOfPassangeres: Int): Double {
        val fare = numOfPassangeres * bookingStrategy.fare
        println("Calculating fares using " + bookingStrategy)
        return fare
    }
}


```

### Usage
```kotlin
private fun setupBookingStrategyPatternSampleOne() {
    //CarBooking Strategy
    val cust = Customer(CarBookingStrategy())
    var fare = cust.calculateFare(5)
    println(fare)

    //TrainBooking Strategy
    cust.bookingStrategy = TrainBookingStrategy()
    fare = cust.calculateFare(5)
    println(fare)
}
```

### Output
```
Calculating fares using CarBookingStrategy
62.5
Calculating fares using TrainBookingStrategy
42.5
```


[Command](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/commandDesign)
--------
The Command design pattern is used to encapsulate a request as an object and pass to an invoker, wherein the invoker does not know how to service the request but uses the encapsulated command to perform an action.

To understand ***the command design pattern*** we should understand the associated key terms like client, command, command implementation, invoker, receiver.

 - The Command is an interface with the execute method. It is the core of the contract.
 - A client creates an instance of a command implementation and associates it with a receiver.
 - An invoker instructs the command to perform an action.
 - A Command implementation’s instance creates a binding between the receiver and an action.
 - The Receiver is the object that knows the actual steps to perform the action.

#### When to use the Command Pattern
* When the requests need to be handled in certain time occurrences and according to different triggers situations
* When the client and the service provider needs to be decoupled
* When there is a need for rollback functionality for certain operations
* When the history of requests required
* When there is a need to add new commands
* When there is a need for parameterizing objects according to an action

#### Example
```kotlin
import kotlin.collections.ArrayList

interface Command {

    fun execute()
}

class OnCommand(private val ce: ConsumerElectronics) : Command {

    override fun execute() {
        ce.on()
    }
}

class MuteAllCommand(internal var ceList: List<ConsumerElectronics>) : Command {

    override fun execute() {

        for (ce in ceList) {
            ce.mute()
        }
    }
}

interface ConsumerElectronics {
    fun on()
    fun mute()
}

class Television : ConsumerElectronics {

    override fun on() {
        println("Television is on!")
    }

    override fun mute() {
        println("Television is muted!")
    }
}

class SoundSystem : ConsumerElectronics {

    override fun on() {
        println("Sound system is on!")
    }
    override fun mute() {
        println("Sound system is muted!")
    }
}

class Button(var c: Command) {

    fun click() {
        c.execute()
    }
}

class UniversalRemote {

    // here we will have a complex electronic circuit :-)
    // that will maintain current device
    fun getActiveDevice() : ConsumerElectronics{
        val tv = Television()
        return tv
    }
}
```

### Usage
```kotlin
private fun setupCommandPattern() {
    // OnCommand is instantiated based on active device supplied by Remote
    val ce = UniversalRemote.getActiveDevice()
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
```

### Output
```
Television is on!
Television is muted!
Sound system is muted!
```

[State](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/state)
--------
The State design pattern is used when an Object changes its behavior based on its internal state.

If we have to change the behavior of an object based on its state, we can have a state variable in the Object and use if-else condition block to perform different actions based on the state.

State pattern is used to provide a systematic and lose-coupled way to achieve this through Context and State implementations.

We can see a lot of real-world examples for the need of state design pattern. Think of our kitchen mixer, which has a step down motor inside and a control interface. Using that knob we can increase / decrease the speed of the mixer. Based on speed state the behavior changes.

#### Example

Let us take an example scenario using a mobile. With respect to alerts, a mobile can be in different states. For example, vibration and silent. Based on this alert state, the behavior of the mobile changes when an alert is to be done. Which is a suitable scenario for state design pattern.

 * ***MobileAlertState*** : This interface represents the different states involved. All the states should implement this interface.
 * ***AlertStateContext*** : This class maintains the current state and is the core of the state design pattern. A client should access / run the    whole setup through this class.
 * ***Vibration*** : Representation of a state implementing the abstract state object. Similar to this we have Silent and Sound classes

```kotlin
interface MobileAlertState {
    fun alert(ctx: AlertStateContext)
}

class AlertStateContext {
    private var currentState: MobileAlertState? = null

    init {
        currentState = Vibration()
    }

    fun setState(state: MobileAlertState) {
        currentState = state
    }

    fun alert() {
        currentState!!.alert(this)
    }
}

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
```

### Usage
```kotlin
private fun setUpStatePattern() {
    val stateContext = AlertStateContext()
    stateContext.alert()
    stateContext.alert()
    stateContext.setState(Silent())
    stateContext.alert()
    stateContext.alert()
    stateContext.setState(Sound())
    stateContext.alert()
}
```

### Output
```
vibration...
vibration...
silent...
silent...
tu..tu..tu..tu
```

[Chain of Responsibility](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/chainOfResponsibility)
--------
Chain of responsibility pattern is used to achieve loose coupling in software design where a request from the client is passed to a chain of objects to process them. Later, the object in the chain will decide themselves who will be processing the request and whether the request is required to be sent to the next object in the chain or not.

#### Example

```kotlin
class MoneyPile(val value: Int, var quantity: Int, var nextPile: MoneyPile?) {
    fun canTakeSomeBill(want: Int): Boolean = (want / this.value) > 0
    fun canWithdraw(withdrawAmount: Int): Boolean {
        var amount = withdrawAmount
        var quantity = this.quantity
        while (canTakeSomeBill(want = amount)) {
            if (quantity == 0) {
                break
            }
            amount -= this.value
            quantity -= 1
        }
        if (amount <= 0) {
            return true
        }
        nextPile?.let {
            return it.canWithdraw(withdrawAmount = amount)
        }
        return false
    }
}
class ATM(val moneyPile: MoneyPile) {
    fun canWithdraw(amount: Int) = println("Can withdraw: ${moneyPile.canWithdraw(withdrawAmount = amount)}")
}
```

### Usage
```kotlin
private fun setupChainOfResponsibilityPattern()  {
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
```

### Output
```
Can withdraw: false
Can withdraw: true
Can withdraw: false
Can withdraw: true
```

[Visitor](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/visitor)
--------
The Visitor pattern is used when we have to perform an operation on a group of similar kind of Objects.

#### Example
With the help of the visitor pattern, we can move the operational logic from the objects to another class.
```kotlin
interface PlanetVisitor {
    fun visit(planet: PlanetAlderaan)
    fun visit(planet: PlanetCoruscant)
    fun visit(planet: PlanetTatooine)
    fun visit(planet: MoonJedah)
}
interface Planet {
    fun accept(visitor: PlanetVisitor)
}
class MoonJedah : Planet {
    override fun accept(visitor: PlanetVisitor) = visitor.visit(planet = this)
}
class PlanetAlderaan : Planet {
    override fun accept(visitor: PlanetVisitor) = visitor.visit(planet = this)
}
class PlanetCoruscant : Planet {
    override fun accept(visitor: PlanetVisitor) = visitor.visit(planet = this)
}
class PlanetTatooine : Planet {
    override fun accept(visitor: PlanetVisitor) = visitor.visit(planet = this)
}
class NameVisitor(var name: String = "") : PlanetVisitor {
    override fun visit(planet: PlanetAlderaan) {
        name = "Alderaan"
    }
    override fun visit(planet: PlanetCoruscant) {
        name = "Coruscant"
    }
    override fun visit(planet: PlanetTatooine) {
        name = "Tatooine"
    }
    override fun visit(planet: MoonJedah) {
        name = "Jedah"
    }
}
```

### Usage
```kotlin
private fun setUpVisitorPattern() {
    val planets = mutableListOf(PlanetAlderaan(), PlanetCoruscant(), PlanetTatooine(), MoonJedah())
    val visitor = NameVisitor()
    planets.forEach {
        it.accept(visitor)
        println(visitor.name)
    }
}
```

### Output
```
Alderaan
Coruscant
Tatooine
Jedah
```

[Mediator](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/mediator)
--------
***Mediator pattern is used to reduce communication complexity between multiple objects or classes.***

This pattern provides a mediator class which normally handles all the communications between different classes and supports easy maintenance of the code by loose coupling. Mediator pattern falls under behavioral pattern category.

#### The problem
In case we have many objects that interact with each other, we end up with very complex and non-maintainable code. That’s why it is a good case to use the Mediator design pattern.

There are many times that we have interaction among objects. We usually create a sufficient amount of objects allowing to communicate with each other with the intention of building entities which act together for a specific reason.

When something happens to an object then another one has to do something or change its behavior.

Building a complex code allowing many objects (2 or more) to communicate with each other, you will end up with spaghetti code, non-maintainable, and you will face a nightmare every time you want to find and fix a bug or to enhance the code.

This problem increases if you normally have a back and forth and a complex interaction

#### How do we solve it
The solution comes using the Mediator pattern. This pattern allows you to keep the whole logic of communication in one place (object) and every object used to interact with other objects now interacts only with the one which is aware of how to act in every occasion.

So, we can understand very quickly that objects are decoupled. The code is more simple than it used to be. That’s exactly what we try to achieve using OOP and design patterns.

#### Example
Air traffic controller (ATC) is a mediator between flights. It helps in communication between flights and co-ordinates/controls landing, take-off.
Two flights need not interact directly and there is no dependency between them. This dependency is solved by the mediator ATC.
If ATC is not there all the flights have to interact with one another and managing the show will be very difficult and things may go wrong.

***In a mediator design pattern implementation we will have***

* mediator interface – an interface that defines the communication rules between objects
* concrete mediator – a mediator object which will enable communication between participating objects
* colleague – objects communicating with each other through mediator object

```kotlin
interface Command {
    fun land()
}

class Flight(private val atcMediator: IATCMediator) : Command {

    override fun land() {
        if (atcMediator.isLandingOk) {
            println("Landing done....")
            atcMediator.setLandingStatus(true)
        } else
            println("Will wait to land....")
    }

    fun getReady() {
        println("Getting ready...")
    }

}

class Runway(private val atcMediator: IATCMediator) : Command {

    init {
        atcMediator.setLandingStatus(true)
    }

    override fun land() {
        println("Landing permission granted...")
        atcMediator.setLandingStatus(true)
    }

}
interface IATCMediator {

    val isLandingOk: Boolean
    fun registerRunway(runway: Runway)
    fun registerFlight(flight: Flight)
    fun setLandingStatus(status: Boolean)
}

class ATCMediator : IATCMediator {
    private var flight: Flight? = null
    private var runway: Runway? = null

    override var isLandingOk: Boolean = false

    override fun registerRunway(runway: Runway) {
        this.runway = runway
    }

    override fun registerFlight(flight: Flight) {
        this.flight = flight
    }

    override fun setLandingStatus(status: Boolean) {
        isLandingOk = status

    }
}

```

### Usage
```kotlin
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
```

### Output
```
Getting ready...
Landing permission granted...
Landing done....
```

[Memento](/app/src/main/java/ir/hfathi/designpattern/behavioralPatterns/memento)
--------
Memento pattern is used to restore the state of an object to a previous state. As your application is progressing, you may want to save checkpoints in your application and restore back to those checkpoints later.

The purpose of the memento design pattern is to provide the ability to execute an undo action in order to restore an object to a previous state. The Memento pattern is also known as Token.

For example, when you use a text editor such as vim or emacs, do you use undo? Why do you think this undo command can perform? As one way, all statuses(sometimes not all) are recorded as a type of object. Then you can recover this object to use this recovered data in your application.

Undo or backspace or ctrl+z is one of the most used operations in an editor. The Memento design pattern is used to implement the undo operation. This is done by saving the current state of the object as it changes state.

One important point to note in implementing the memento design pattern is, the encapsulation of the object should not be compromised.


#### Example
***Memento Pattern participants***
* originator : the object for which the state is to be saved. It creates the memento and uses it in the future to undo.
* memento : the object that is going to maintain the state of the originator. It is just a POJO.
* caretaker : the object that keeps track of multiple memento. Like maintaining save points.

The originator will store the state information in the memento object and retrieve old state information when it needs to backtrack. The memento just stores what the originator gives to it. Memento object is unreachable for other objects in the application.

```kotlin
class Memento(val state: String)

class Originator {

    //this String is just for example
    //in real world application this
    //will be the object for which the state to be stored
    var state: String? = null

    fun createMemento(): Memento {
        return Memento(state!!)
    }

    fun setMemento(memento: Memento) {
        state = memento.state
    }
}

class Caretaker {
    private val statesList = ArrayList<Memento>()

    fun addMemento(m: Memento) {
        statesList.add(m)
    }

    fun getMemento(index: Int): Memento {
        return statesList.get(index)
    }
}
```

### Usage
```kotlin
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
```

### Output
```
Originator Current State: Thor
Originator restoring to previous state...
Originator Current State: Hulk
Again restoring to previous state...
Originator Current State: Ironman
```


Creational
==========

> In software engineering, creational design patterns are design patterns that deal with object creation mechanisms, trying to create objects in a manner suitable to the situation. The basic form of object creation could result in design problems or added complexity to the design. Creational design patterns solve this problem by somehow controlling this object creation.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Creational_pattern)


[Builder](/app/src/main/java/ir/hfathi/designpattern/creationalPatterns/builder)
--------

***The problem to Solve :***
* Some objects are simple and can be created using single constructor call but there are some constructor which may need more parameter to construct the object.
* Having more parameters to a constructor is not a good approach to handle the product

***How Builder Patterns Solves it***
 * We create small objects to create the final object
 * Construct an object in multiple pieces but making sure the end result is the same
 * The Builder design pattern provides an API for constructing an Object step-by-step

***When piecewise object construction is complicated, provide an API for doing it in simple & clear manner***

#### Example
In below example, We are trying to cerate a custom computer which has few components that can be modiefied.

We have a constructor which accepts the number of parameters like OS, RAM, screenSize, ExternalMouse, Battery. So when user want to provide any value
```kotlin
class Computer{
    var OS:String
    var RAM:Int
    var screenSize:Double
    var externalMouse:Boolean
    var externalKeyboard:Boolean
    var battery:String

    constructor(OS: String, RAM: Int, screenSize: Double,
                externalMouse: Boolean, externalKeyboard: Boolean, battery: String) {
        this.OS = OS
        this.RAM = RAM
        this.screenSize = screenSize
        this.externalMouse = externalMouse
        this.externalKeyboard = externalKeyboard
        this.battery = battery
    }

    override fun toString(): String {
        return ("The required configuration is :" +
                "
OS : $OS " +
                "
RAM : $RAM " +
                "
screenSize : screenSize " +
                "
externalMouse : $externalMouse " +
                "
externalKeyboard : $externalKeyboard " +
                "
battery : $battery"
        )
    }
}
```

### Usage
```kotlin
private fun setupBuilderPattern() {
    val comp = Computer("Windows", 8, 14.5, true, false, "Inbulit")
    println(comp)
}
```

### Output
```
The required configuration is : OS : Windows  RAM : 8  screenSize : screenSize  externalMouse : true  externalKeyboard : false  battery : Inbulit
```

[Factory](/app/src/main/java/ir/hfathi/designpattern/creationalPatterns/factory)
--------

A Factory Pattern or Factory Method Pattern says that just define an interface or abstract class for creating an object but let the subclasses decide which class to instantiate.

In other words, ***subclasses*** are responsible to create the instance of the class. The Factory Method Pattern is also known as Virtual Constructor.

***Problems solved by Factory Method :***
* Complex object creation, especially within several layers of class hierarchy
* Usage of the new() operator, causing complexity, duplication and inflexibility issues in application code
* When the client is unauthorized to access in-detail class implementations

***When to use Factory design pattern :***
* Factory method is used when Products don't need to know how they are created.
* We can use factory pattern where we have to create an object of any one of sub-classes depending on the data provided
* Some or all concrete products can be created in multiple ways, or we want to leave open the option that in the future there may be new ways to create the concrete product.

#### Example
The principle is simple, All the objects should be created in a subclasses and they all implement a specific function that we define in an Interface.

All the subclasses should implement the interface so that every class will have the same methods.

In below example, we will see the currency of the country and the Symbol for that currency. We will try to do it by providing sample values


```kotlin
interface ICurrency {
    fun symbol(): String
    fun code(): String
}

class Euro : ICurrency {
    override fun symbol(): String {
        return "€"
    }
    override fun code(): String {
        return "EUR"
    }
}

class UnitedStatesDollar : ICurrency {
    override fun symbol(): String {
        return "$"
    }
    override fun code(): String {
        return "USD"
    }
}

enum class Country {
    UnitedStates, Spain, UK, Greece
}

fun currency(country: Country): ICurrency? {
    when (country) {
        Country.Spain, Country.Greece -> return Euro()
        Country.UnitedStates -> return UnitedStatesDollar()
        else -> return null
    }
}
```

### Usage
```kotlin
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
```

### Output
```
EUR
EUR
USD
"I am not Creative, so Currency Code Available"
```

[Singleton](/app/src/main/java/ir/hfathi/designpattern/creationalPatterns/singleton)
--------

Singleton pattern is a design solution where an application wants to have one and only one instance of any class, in all possible scenarios without any exceptional condition.

***The singleton pattern is a design pattern that restricts the instantiation of a class to one object. This is useful when exactly one object is needed to coordinate actions across the system.***

#### Example
Kotlin doesn't have static members for class, it means that you can't create a static method and static variable in Kotlin class. So, how can we create a singleton class in Kotlin?

Fortunately, Kotlin object can handle this. Specific to class, companion object keyword is used for its member to be accessible.

```kotlin
public class Singleton private constructor() {
    init { println("This ($this) is a singleton") }

    private object Holder { val INSTANCE = Singleton() }

    companion object {
        val instance: Singleton by lazy { Holder.INSTANCE }
    }
    var b:String? = null
}
```

### Usage
```kotlin
private fun setUpSingletonPattern() {
    val first = Singleton.instance  // This (Singleton@7daf6ecc) is a
    // singleton
    first.b = "hello singleton"

    val second = Singleton.instance
    println(first.b)        // hello singleton
    println(second.b)        // hello singleton
}
```

### Output
```
This (ir.hfathi.designpattern.behavioralPatterns.singletion.Singleton@8a0cae9) is a singleton
hello singleton
hello singleton
```


[Abstract Factory](/app/src/main/java/ir/hfathi/designpattern/creationalPatterns/abstractFactory)
--------

Abstract Factory Pattern says that just define an interface or abstract class for creating families of related (or dependent) objects but without specifying their concrete sub-classes.

Abstract Factory pattern is almost similar to Factory Pattern is considered as another layer of abstraction over factory pattern. Abstract Factory patterns work around a super-factory which creates other factories.

***When to use Abstract Factory Pattern :***
* there are a number of products fall into families
* the families of products need to stay together, but only one subset of families can be used at a particular time
* the internal implementation is hidden and needs to expose via an interface
* the client does not need internal implementations and details of object creations
* the system needs more modularity and abstraction to separate the functionalities Standard Class Diagram

#### Example
```kotlin
enum class CarType {
    MICRO, MINI, LUXURY
}

abstract class Car(model: CarType, location: Location) {

    var model: CarType? = null
    var location: Location? = null

    init {
        this.model = model
        this.location = location
    }

    abstract fun construct()

    override fun toString(): String {
        return "CarModel - $model located in $location"
    }
}

class LuxuryCar(location: Location) : Car(CarType.LUXURY, location) {
    init {
        construct()
    }

     override fun construct() {
        println("Connecting to luxury car")
    }
}

class MicroCar(location: Location) : Car(CarType.MICRO, location) {
    init {
        construct()
    }

    override fun construct() {
        println("Connecting to Micro Car ")
    }
}

class MiniCar(location: Location) : Car(CarType.MINI, location) {
    init {
        construct()
    }

    override fun construct() {
        println("Connecting to Mini car")
    }
}

enum class Location {
    DEFAULT, USA, INDIA
}

class INDIACarFactory {
    fun buildCar(model: CarType): Car? {
        var car: Car? = null
        when (model) {
            CarType.MICRO -> car = MicroCar(Location.INDIA)

            CarType.MINI -> car = MiniCar(Location.INDIA)

            CarType.LUXURY -> car = LuxuryCar(Location.INDIA)

            else -> {
            }
        }
        return car
    }
}

fun buildCar(model: CarType): Car? {
    var car: Car? = null
    when (model) {
        CarType.MICRO -> car = MicroCar(Location.DEFAULT)

        CarType.MINI -> car = MiniCar(Location.DEFAULT)

        CarType.LUXURY -> car = LuxuryCar(Location.DEFAULT)

        else -> {
        }
    }
    return car
}


internal object USACarFactory {
    fun buildCar(model: CarType): Car? {
        var car: Car? = null
        when (model) {
            CarType.MICRO -> car = MicroCar(Location.USA)

            CarType.MINI -> car = MiniCar(Location.USA)

            CarType.LUXURY -> car = LuxuryCar(Location.USA)

            else -> {
            }
        }
        return car
    }
}


class CarFactory {
    fun buildCar(type: CarType): Car? {
        var car: Car? = null
        // We can add any GPS Function here which
        // read location property somewhere from configuration
        // and use location specific car factory
        // Currently I'm just using INDIA as Location
        val location = Location.INDIA

        when (location) {
            Location.USA -> car = USACarFactory.buildCar(type)

            Location.INDIA -> car = INDIACarFactory().buildCar(type)

            else -> car = buildCar(type)
        }

        return car

    }
}
```

### Usage
```kotlin
private fun setupAbstractFactoryPattern() {
    println(CarFactory().buildCar(CarType.MICRO))
    println(CarFactory().buildCar(CarType.MINI))
    println(CarFactory().buildCar(CarType.LUXURY))
}
```

### Output
```
Connecting to Micro Car
CarModel - MICRO located in INDIA
Connecting to Mini car
CarModel - MINI located in INDIA
Connecting to luxury car
CarModel - LUXURY located in INDIA
```


[Prototype](/app/src/main/java/ir/hfathi/designpattern/creationalPatterns/prototype)
--------
When creating an object is time-consuming and a costly affair and you already have a most similar object instance in hand, then you go for prototype pattern. Instead of going through a time-consuming process to create a complex object, just copy the existing similar object and modify it according to your needs.

Its a simple and straight forward design pattern. Nothing much hidden beneath it. If you don’t have much experience with the enterprise-grade huge application, you may not have experience in creating a complex /time-consuming instance. All you might have done is use the new operator or inject and instantiate.

If you are a beginner you might be wondering, why all the fuss about prototype design pattern and do we really need this design pattern? Just ignore, all the big guys requires it. For you, just understand the pattern and sleep over it. You may require it one day in the future.

Prototype pattern may look similar to builder design pattern. There is a huge difference to it. If you remember, the same construction process can create different representations is the key in builder pattern. But not in the case of prototype pattern.

So, how to implement the prototype design pattern? You just have to copy the existing instance in hand. When you say copy in kotlin, immediately cloning comes into the picture. That's why when you read about prototype pattern, all the literature invariably refers to kotlin cloning.

A simple way is, clone the existing instance in hand and then make the required update to the cloned instance so that you will get the object you need.

Another way is, tweak the cloning method itself to suit your new object creation need. Therefore whenever you clone that object you will directly get the new object of desire without modifying the created object explicitly.

#### Example

Example source code is just to demonstrate the design pattern, please don’t read too much out of it. I wanted to make things as simple as possible.

```kotlin
open class Bike : Cloneable {
    private var gears: Int = 0
    private var bikeType: String? = null
    var model: String? = null
        private set

    init {
        bikeType = "Standard"
        model = "Leopard"
        gears = 4
    }

    public override fun clone(): Bike {
        return Bike()
    }

    fun makeAdvanced() {
        bikeType = "Advanced"
        model = "Jaguar"
        gears = 6
    }
}


```

### Usage
```kotlin
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
```

### Output
```
Prototype Design Pattern: Jaguar
```

Structural
==========

>In software engineering, structural design patterns are design patterns that ease the design by identifying a simple way to realize relationships between entities.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Structural_pattern)

[Adapter](/app/src/main/java/ir/hfathi/designpattern/structuralPatterns/adapter)
--------
Adapter pattern works as a bridge between two incompatible interfaces. This type of design pattern comes under structural pattern as this pattern combines the capability of two independent interfaces.

***The adapter pattern makes two incompatible interfaces compatible without changing their existing code***

#### Example
With the help of the visitor pattern, we can move the operational logic from the objects to another class.

```kotlin
interface Temperature {
    var temperature: Double
}
class CelsiusTemperature(override var temperature: Double) : Temperature
class FahrenheitTemperature(var celsiusTemperature: CelsiusTemperature) : Temperature {
    override var temperature: Double
        get() = convertCelsiusToFahrenheit(celsiusTemperature.temperature)
        set(temperatureInF) {
            celsiusTemperature.temperature = convertFahrenheitToCelsius(temperatureInF)
        }
    private fun convertFahrenheitToCelsius(f: Double): Double = (f - 32) * 5 / 9
    private fun convertCelsiusToFahrenheit(c: Double): Double = (c * 9 / 5) + 32
}
```

### Usage
```kotlin
private fun setUpAdapterPattern() {
    val celsiusTemperature = CelsiusTemperature(0.0)
    val fahrenheitTemperature = FahrenheitTemperature(celsiusTemperature)
    celsiusTemperature.temperature = 36.6
    println("${celsiusTemperature.temperature} C -> ${fahrenheitTemperature.temperature} F")
    fahrenheitTemperature.temperature = 100.0
    println("${fahrenheitTemperature.temperature} F -> ${celsiusTemperature.temperature} C")
}
```

### Output
```
36.6 C -> 97.88000000000001 F
100.0 F -> 37.77777777777778 C
```

[Bridge](/app/src/main/java/ir/hfathi/designpattern/structuralPatterns/bridge)
--------
The bridge pattern allows the Abstraction and the Implementation to be developed independently and the client code can access only the Abstraction part without being concerned about the Implementation part.

***Decouple an abstraction from its implementation so that the two can vary independently***

***Need for Bridge Design Pattern***

When there are inheritance hierarchies creating concrete implementation, you lose flexibility because of interdependence.
Decouple implementation from the interface and hiding implementation details from the client is the essence of the bridge design pattern.


***When to use the Bridge Pattern***
* When there is a need to void the permanent binding or tight coupling between abstraction and implementation. In this case, implementation may select or switch at run-time
* When there is a need for extending the abstraction and the composite implementation separately by subclasses.
* When the change in extended implementations should not impact the client
* When there is a hierarchical propagation of several categories of classes in the system

***Advantageous of Bridge Design Pattern***
* Bridge pattern allows independent variation between two abstract and implementor systems
* It avoids the client code binding to a certain implementation
* Abstraction and implementation can be clearly separated allowing easy future extension
* Provides a good solution for cross-platform development
* Well suited for testing with stubs in the initial development cycle


#### Example
```kotlin
interface Switch {
    var appliance: Appliance
    fun turnOn()
}
interface Appliance {
    fun run()
}
class RemoteControl(override var appliance: Appliance) : Switch {
    override fun turnOn() = appliance.run()
}
class TV : Appliance {
    override fun run() = println("TV turned on")
}
class VacuumCleaner : Appliance {
    override fun run() = println("VacuumCleaner turned on")
}
```

### Usage
```kotlin
private fun setUpBridgePatternSampleOne() {
    val tvRemoteControl = RemoteControl(appliance = TV())
    tvRemoteControl.turnOn()
    val fancyVacuumCleanerRemoteControl = RemoteControl(appliance = VacuumCleaner())
    fancyVacuumCleanerRemoteControl.turnOn()
}
```

### Output
```
TV turned on
VacuumCleaner turned on
```

[Decorator](/app/src/main/java/ir/hfathi/designpattern/structuralPatterns/decorator)
--------
The Decorator pattern allows a user to add new functionality to an existing object without altering its structure. This type of design pattern comes under structural pattern as this pattern acts as a wrapper to the existing class.

That is there will be additional features added to the original object. The component, which adds these additional features is called the Decorator.

The Decorator class in this pattern act as a wrapper object which dynamically attaches additional features to the original object at run-time.

Definition:
***Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to sub-classing for extending functionality***

***Problem solved by the Decorator Pattern***
The problem occurs when an application wants to incorporate additional features and behaviours to objects at run-time. Moreover, it's not only one feature, but there can also be multiple features and functionalities.

Therefore, acquiring that situation will definitely become a lengthy and complex coding hack to a developer. You may think, inheritance and sub-classing will help to resolve the situation. However, those won’t support due to certain limitations on those approaches.

Inheritance and sub-classing effects at compile-time that is statically, you have to pre-configure the required additional features and functionalities if planning to use those approaches.

In addition, the new options will attach to all the objects from those sub-classes, which is not a definite requirement at this situation. Hence, we need a way to fill both of those gaps. Decorator comes into the play to solve this problem.

#### Example
Let’s take a juice bar which makes different kinds of juices. Their most popular product is the milkshake. There are many flavors of milkshakes to select.

Imagine three friends went there and ordered a banana milkshake, peanut butter shake, and chocolate milkshake.

We need to represent this scenario for the decorator pattern implementation. If we study the scenario further, we can identify that the base component is milk with sugar and salt. Then there are three kinds of additional features attached to this milkshake on the serving time or blending moment dynamically.

```kotlin
interface MilkShake {
    fun getTaste()
}

class ConcreteMilkShake : MilkShake {
    override fun getTaste() {
        println("It’s milk !")
    }
}
open class MilkShakeDecorator(protected var milkShake: MilkShake) : MilkShake {
    override fun getTaste() {
        this.milkShake.getTaste()
    }
}

class BananaMilkShake(m:MilkShake) : MilkShakeDecorator(m){

    override public fun getTaste(){
        super.getTaste ();
        this.addTaste();
        println(" It’s Banana milk shake !");
    }
    public fun addTaste(){
        println(" Adding Banana flavor to the milk shake !");
    }
}

public class PeanutButterMilkShake(m:MilkShake) : MilkShakeDecorator(m){

    override public fun getTaste(){
        super.getTaste ();
        this.addTaste();
        println(" It’s Peanut butter milk shake !");
    }
    public fun addTaste(){
        println(" Adding Peanut butter flavor to the milk shake !");
    }
}
```

### Usage
```kotlin
private fun setupDecoratorPattern() {
    val peanutMilkShake = PeanutButterMilkShake(ConcreteMilkShake())
    peanutMilkShake.getTaste()
    val bananaMilkShake = BananaMilkShake(ConcreteMilkShake())
    bananaMilkShake.getTaste()
}
```

### Output
```
It’s milk !
 Adding Peanut butter flavor to the milk shake !
 It’s Peanut butter milk shake !
It’s milk !
 Adding Banana flavor to the milk shake !
 It’s Banana milk shake !
```



[Facade](/app/src/main/java/ir/hfathi/designpattern/structuralPatterns/facade)
--------
Facade pattern hides the complexities of the system and provides an interface to the client using which the client can access the system. This type of design pattern comes under structural pattern as this pattern adds an interface to the existing system to hide its complexities.

#### Example

This pattern involves a single class which provides simplified methods required by client and delegates calls to methods of the existing system classes

```kotlin
class CPU {
    fun freeze() = println("Freezing.")

    fun jump(position: Long) = println("Jump to $position.")

    fun execute() = println("Executing.")
}

class HardDrive {
    fun read(lba: Long, size: Int): ByteArray = byteArrayOf()
}

class Memory {
    fun load(position: Long, data: ByteArray) = println("Loading from memory position: $position")
}

/* Facade */
class Computer(val processor: CPU = CPU(), val ram: Memory = Memory(), val hd: HardDrive = HardDrive()) {
    companion object {
        val BOOT_ADDRESS = 0L
        val BOOT_SECTOR = 0L
        val SECTOR_SIZE = 0
    }

    fun start() {
        processor.freeze()
        ram.load(BOOT_ADDRESS, hd.read(BOOT_SECTOR, SECTOR_SIZE))
        processor.jump(BOOT_ADDRESS)
        processor.execute()
    }
}
```

### Usage
```kotlin
private fun setupFacadePattern() {
    val computer = FComputer()
    computer.start()
}
```

### Output
```
Freezing.
Loading from memory position: 0
Jump to 0.
Executing.
```


[Protection Proxy](/app/src/main/java/ir/hfathi/designpattern/structuralPatterns/proxy)
--------
The Proxy Pattern is used to create a representative object that controls access to another object, which may be remote, expensive to create or in need of being secured.

One reason for controlling access to an object is to defer the full cost of its creation and initialization until we actually need to use it. Another reason could be to act as a local representative for an object that lives in a different JVM.

The Proxy can be very useful in controlling access to the original object, especially when objects should have different access rights.

In the Proxy Pattern, a client does not directly talk to the original object, it delegates it calls to the proxy object which calls the methods of the original object.

The important point is that the client does not know about the proxy, the proxy acts as an original object for the client.

#### Example

```kotlin
interface File {
    fun read(name: String)
}

class NormalFile : File {
    override fun read(name: String) = println("Reading file: $name")
}

//Proxy:
class SecuredFile : File {
    val normalFile = NormalFile()
    var password: String = ""

    override fun read(name: String) {
        if (password == "secret") {
            println("Password is correct: $password")
            normalFile.read(name)
        } else {
            println("Incorrect password. Access denied!")
        }
    }
}
```

### Usage
```kotlin
private fun setupProxyPattern() {
    val securedFile = SecuredFile()
    securedFile.read("readme.md")

    securedFile.password = "secret"
    securedFile.read("readme.md")
}
```

### Output
```
Incorrect password. Access denied!
Password is correct: secret
Reading file: readme.md
```

[Composite](/app/src/main/java/ir/hfathi/designpattern/structuralPatterns/composite)
--------
Composite pattern is a partitioning design pattern and describes a group of objects that are treated the same way as a single instance of the same type of object. The intent of a composite is to 'compose' objects into tree structures to represent part-whole hierarchies. It allows you to have a tree structure and ask each node in the tree structure to perform a task.

#### Example

```kotlin
import java.util.*
/** "Component" */
interface Graphic {
    fun print()
}
/** "Composite" */
class CompositeGraphic(val graphics: ArrayList<Graphic> = ArrayList()) : Graphic {
    //Prints the graphic.
    override fun print() = graphics.forEach(Graphic::print)
    //Adds the graphic to the composition.
    fun add(graphic: Graphic) {
        graphics.add(graphic)
    }
    //Removes the graphic from the composition.
    fun remove(graphic: Graphic) {
        graphics.remove(graphic)
    }
}
class Ellipse : Graphic {
    override fun print() = println("Ellipse")
}
class Square : Graphic {
    override fun print() = println("Square")
}
```

### Usage
```kotlin
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
```

### Output
```
Ellipse
Ellipse
Square
Ellipse
Ellipse
Square
Square
```
