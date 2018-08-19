trait Food {
  val name: String
  override def toString = s"Yummy $name"
}
trait Fruit extends Food
case class Apple(val name: String) extends Fruit

case class Orange(val name: String) extends Fruit
trait Sink[T] {
  def send(item: T): String
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

//should be able to send Fruit
//sinkAnApple(FruitSink)   // but it would be nice if this did

trait Sink2[-T] {
  def send(item: T): String
}
// Note the [-T]
object AppleSink2 extends Sink2[Apple] {
  def send(item: Apple) = s"Coring and eating ${item.name}"
}
object OrangeSink2 extends Sink2[Orange] {
  def send(item: Orange) = s"Juicing and drinking ${item.name}"
}
object FruitSink2 extends Sink2[Fruit] {
  def send(item: Fruit) = s"Eating a healthy ${item.name}"
}
object AnySink2 extends Sink2[Any] {
  def send(item: Any) = s"Sending ${item.toString}"
}

def sinkAnApple(sink: Sink2[Apple]): String = {
  sink.send(Apple("Fuji"))
}

sinkAnApple(AppleSink2)
sinkAnApple(FruitSink2)
sinkAnApple(AnySink2)

// Because they are siblings
// sinkAnApple(OrangeSink2)

/**
  * Any supplied type T is invariant, covariant and contravariant to itself!
  * Only type T is all three of these things to type T
  * Any type T also satisfies lower and upper bounds to itself
  * Class/Trait type parameters are the only place you can use variance modifiers
    - not in method type parameters
    - not in any usage of a type parameter
  *We often say a class or trait is co- or contra-variant (List is co variance example),
   but in fact
   -- Co-variant in type parameters --
   or
   -- List is container that is co-variant in it single type parameter --
  *And a class or trait can have both co- and contra-variant type parameters in its definition
  */
