trait Food { val name: String }
trait Fruit extends Food
trait Cereal extends Food

case class Apple(name: String) extends Fruit
case class Orange(name: String) extends Fruit
case class Muesli(name: String) extends Cereal

/**
  * We can have fields as members of a class
  * we can types as members of a class
  */
abstract class FoodBowl2 {
  //It's generic type but it's not generic parameter any more
  //It's generic field
  type FOOD <: Food
  val food: FOOD
  def eat: String = s"Yummy ${food.name}"
}

class AppleBowl(val food: Apple) extends FoodBowl2 {
  //you must override
  type FOOD = Apple
}

val appleBowl = new AppleBowl(Apple("Fiji"))

val apple1 = appleBowl.food

val apple2: Apple = appleBowl.food

val apple3: appleBowl.FOOD = appleBowl.food

//type projection refers to type definition in that class
//and all point to same type
val apple4: AppleBowl#FOOD = appleBowl.food
