/**
  * Variance Problems
  */

abstract class Food extends Product with Serializable { val name: String }

abstract class Fruit extends Food
case class Banana(name: String) extends Fruit
case class Apple(name: String) extends Fruit

abstract class Cereal extends Food
case class Granola(name: String) extends Cereal
case class Muesli(name: String) extends Cereal

val fuji = Apple("Fuji")
val alpen = Muesli("Alpen")

//Functional term for this type is Semigroup
trait CombineWith[T] {
  val item: T
  def combineWith(another: T): T
}

//When we work with invariance everything nice
case class CombineWithInt(item: Int) extends CombineWith[Int] {
  def combineWith(another: Int) = item + another
}
val cwi: CombineWith[Int] = CombineWithInt(10)

cwi.combineWith(10)

/* invariant behavior

val cwo: CombineWith[Any] = CombineWithInt(10)
cwo.combineWith("ten")

trait CombineWith2[+T] {
  val item: T
  def combineWith(another: T): T
}
*/

// Bounds revisited

//Least Upper Bounds
val ints = List(1,2,3,4)

val anyvals = true :: ints

val anys = "hello" :: anyvals

case class MixedFoodBowl[+F <: Food](food1: F, food2: F) {
  override def toString: String = s"${food1.name} mixed with ${food2.name}"
}

case class FoodBowl[+F <: Food](food: F) {
  override def toString: String = s"A bowl of ${food.name}"

 // def mix(other: F): MixedFoodBowl[F] = MixedFoodBowl(food, other)
  def mix[M >: F <: Food](other: M) = MixedFoodBowl[M](food, other)
}

val apple = Apple("Fuji")
val banana = Banana("Chiquita")

//Scala looks for LUB(Least upper bound) now of banana and apple
FoodBowl(banana).mix[Food](apple)
FoodBowl(apple).mix(alpen)
