trait Food { val name: String }
trait Fruit extends Food
trait Cereal extends Food

case class Apple(name: String) extends Fruit
case class Orange(name: String) extends Fruit
case class Muesli(name: String) extends Cereal

abstract class FoodBowl2 {
  type FOOD <: Food
  val food: FOOD
  def eat: String = s"Yummy ${food.name}"
}

class AppleBowl(val food: Apple) extends FoodBowl2 {
  type FOOD = Apple
}

//In this way you don't need to use override
object BowlOfFood {
  def apply[F <: Food](f: F) = new FoodBowl2 {
    type FOOD = F
    override val food: FOOD = f
  }
}

//In this way we get correct type inside type member
val bowlOfAlpen = BowlOfFood(Muesli("alpen"))

val appleBowl = BowlOfFood(Apple("Fiji"))
val orangeBowl = BowlOfFood(Orange("Jaffa"))

val a1: Apple = appleBowl.food
// is identical too
//This we also call a path dependent type
val a2: appleBowl.FOOD = appleBowl.food

val 01: orangeBowl.FOOD = orangeBowl.food

// but does not compile:
//val o1: orangeBowl.FOOD = appleBowl.food

/**
  * - Type members available from inside and outside of the class or trait
  * - Type parameters only available from inside of class
  * - Type parameters become part of the type and must be specified
  *   def eatFruit(bowl: FoodBowl[Fruit])
  * - Type members are enclosed fully in new type
  *   class FruitBowl extends FoodBowl {type FOOD = Fruit}
  *   def eatFruit(bowl: FruitBowl) in this way member is already embedded
  *  - Type parameters can have co- and contra-variance
  *  - Type members cannot have variance and use bounds instead
  */


