trait Food {
  def name: String
}

case class Fruit(name: String) extends Food

case class Cereal(name: String) extends Food

case class Meat(name: String) extends Food

trait Eater {
  def name: String
}

case class Vegan(name: String)

case class Vegetarian(name: String)

case class Paleo(name: String)

import scala.annotation.implicitNotFound

@implicitNotFound(msg = "Illegal Feeding: No Eats rule from ${EATER} to ${FOOD}")
trait Eats[EATER <: Eater, FOOD <: Food] {
  def feed(food: FOOD, eater: EATER) = s"${eater.name} eats ${food.name}"
}

def feedTo[EATER <: Eater, FOOD <: Food](food: FOOD, eater: EATER)
                                        (implicit ev: Eats[EATER, FOOD]) = {
  ev.feed(food, eater)
}


val apple = Fruit("Apple")
val alpen = Cereal("Alpen")
val beef = Meat("Beef")
val alice = Vegan("Alice")
val bob = Vegetarian("Bob")
val charlie = Paleo("Charlie")

object VeganRules {
  implicit object veganEatsFruit extends Eats[Vegan, Fruit]
}

import VeganRules._

feedTo(apple, alice)
//feedTo(alpen, alice)

object VegetarianRules {
  implicit object veganEatsFruit extends (Vegetarian Eats Fruit)

  implicit object veganEatsCereal extends (Vegetarian Eats Cereal)
}

import VegetarianRules._
feedTo(alpen, bob)

object PaleoRules {
  implicit object paleoEatsFruit extends (Paleo Eats Fruit)

  implicit object paleoEatsMeat extends (Paleo Eats Meat)
}

