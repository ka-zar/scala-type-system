import language.existentials
import  language.reflectiveCalls
/**
  * Existential types allow us to skip the definition of a type parameter in the method
  * The T forSome {type T} means `I know there is a type parameter here, I just don't care about it`
  * Short hand for it `[_]`
  **/
def lengthOfList(xs: List[T forSome {type T}]) = xs.length

// using shorthand

def lengthOfList2(xs: List[_]) = xs.length

trait Food { val name: String }
trait Fruit extends Food

case class Apple(name: String) extends Fruit

val fruits = List(Apple("Fiji"), Apple("Granny Smith"))

def fruitNames[T <: Fruit](fruits: List[T]) = fruits.map(_.name)
def fruitNames2(fruits: List[T forSome {type T <: Fruit}]) = fruits.map(_.name)
def fruitNames3(fruits: List[_ <: Fruit]) = fruits.map(_.name)

fruitNames(fruits)
fruitNames2(fruits)
fruitNames3(fruits)

/**
  * Structural Types
  **/

//Reflection is used to invoke size method, so even it's compile time statically checked it won't failed
def maxSizeInSeq(xs: Seq[_ <: {def size: Int}]) = xs.map(_.size).max

case class Person(name: String, age: Int) {
  def size = name.length
}

maxSizeInSeq(Seq(
  List(1,2,3),
  List(4,5),
  List(6,7,8,9),
  List(10,11)
))

maxSizeInSeq(Seq(
  Person("Harry", 20),
  Person("Fred", 21)
))

val s: Any = "hello"

// This will not compile, because scala can't type check it
//s.charAt(1)

// but this will, "easy" reflection
s.asInstanceOf[{def charAt(x: Int): Char}].charAt(1)

val apple: Any = Apple("Fiji")

//apple.asInstanceOf[{def charAt(x: Int): Char}].charAt(1)
