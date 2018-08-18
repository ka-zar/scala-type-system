trait Food {
  val name: String
  override def toString = s"Yummy $name"
}
trait Fruit extends Food
case class Apple(name: String) extends Fruit
case class Orange(name: String) extends Fruit
trait Sink[-T] { outer =>
  def send(item: T): String

  def andThenSink[U <: T](other: Sink[U]): Sink[U] = {
    (item: U) => outer.send(item) + " and then " + other.send(item)
  }
}
object AppleSink extends Sink[Apple] {
  def send(item: Apple) = s"Coring and eating ${item.name}"
}
object OrangeSink extends Sink[Orange] {
  def send(item: Orange) = s"Juicing and drinking ${item.name}"
}
object FruitSink extends Sink[Fruit] {
  def send(item: Fruit) = s"Eating a healthy ${item.name}"
}
object AnySink extends Sink[Any] {
  def send(item: Any) = s"Sending ${item.toString}"
}

def sinkAnApple(sink: Sink[Apple]): String = {
  sink.send(Apple("Fuji"))
}
sinkAnApple(AppleSink)

//sinkAnApple(OrangeSink)  // this shouldn't work

sinkAnApple(FruitSink)

val newSink: Sink[Apple] = FruitSink.andThenSink(AppleSink)
sinkAnApple(newSink)

/**
  * - Variance is used on type parameters in class and trait definitions only
  * - Bound are used everywhere else they are needed
  * - For co-variance you need a method generic lower bounded by the class
  *   generic for incoming method parameters ''def incoming[U >: T](item: U)''
  *
  * - For contra-variance you need a method generic upper bounded by the class
  *   generic for incoming method parameters 'def outgoing[U <: T](item: U)'
  *
  *  - Any type T is co-variant, contra-variant and variant to itself
  *  - Any type T is lower bound and an upper bound of itself
  */
