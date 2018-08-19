abstract class Food { val name: String }

abstract class Fruit extends Food
case class Banana(name: String) extends Fruit
case class Apple(name: String) extends Fruit
case class Orange(name: String) extends Fruit

abstract class Cereal extends Food
case class Granola(name: String) extends Cereal
case class Muesli(name: String) extends Cereal

val fuji = Apple("Fuji")
val alpen = Muesli("Alpen")

/** Scala Function Variance definition rules:
  * If you pass some kind of generic capable type
  * or generic defined type in to method the input
  * type parameters need to be at least what you got
  * or what you trying to pass or more general
  * and that is "Contra-Variance"
  * and the return type or outputs needs to be
  * a least what you got or more specific and that's "Co-Variance"
  */

trait Description {
  val describe: String
}
case class Taste(describe: String) extends Description
case class Texture(describe: String) extends Description

def describeAnApple(fn: Apple => Description) = fn(Apple("Fuji"))

//Create function that takes Fruit and return Taste
val juicyFruit: Fruit => Taste =
  fruit => Taste(s"This ${fruit.name} is nice and juicy")

describeAnApple(juicyFruit)

//Create function that takes Orange and return Taste
val bumpyOrange: Orange => Description =
  orange => Texture(s"This ${orange.name} is bumpy")

//def describeAFruit(fn: Orange => Description) = fn(Apple("Fuji"))
def describeAFruit(fn: Fruit => Description) = fn(Orange("Fuji"))
describeAFruit(juicyFruit)

//describeAFruit(bumpyOrange)

/**
  * https://www.scala-lang.org/api/2.11.3/index.html#scala.Function1
  * */

