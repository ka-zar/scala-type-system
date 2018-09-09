import scala.annotation.implicitNotFound


/**
  * A type-class is another name for a trait or abstract class
  * with a single type parameter and abstract method(s) defined
  * for that type
  */
@implicitNotFound("You need to define a CompareT for ${T}")
abstract class CompareT[T] {
  def isSmaller(i1: T, i2: T): Boolean

  def isLarger(i1: T, i2: T): Boolean
}

def genInsertT[T](item: T, rest: List[T])(implicit cmp: CompareT[T]): List[T] = {
  rest match {
    case Nil => List(item)
    case head :: _ if cmp.isSmaller(item, head) => item :: rest
    case head :: tail => head :: genInsertT(item, tail)
  }
}

def genSortT[T](xs: List[T])(implicit cmp: CompareT[T]): List[T] = xs match {
  case Nil => Nil
  case head :: tail => genInsertT(head, genSortT(tail))
}

val comparableNums = List(4, 6, 9, 7, 0)

implicit object CompareTInt extends CompareT[Int] {
  override def isSmaller(i1: Int, i2: Int) = i1 < i2

  override def isLarger(i1: Int, i2: Int) = i1 > i2
}

println(genSortT(comparableNums))

case class Distance(meters: Int)

val dists = List(Distance(2), Distance(7), Distance(0))

implicit val compareTDistance = new CompareT[Distance] {
  override def isSmaller(i1: Distance, i2: Distance) = i1.meters < i2.meters

  override def isLarger(i1: Distance, i2: Distance) = i1.meters > i2.meters
}

println(genSortT(dists))

def genInsert[T: Ordering](item: T, rest: List[T]): List[T] = {
  val cmp = implicitly[Ordering[T]]
  rest match {
    case Nil => List(item)
    case head :: _ if cmp.lt(item, head) => item :: rest
    case head :: tail => head :: genInsert(item, tail)
  }
}

def genSort[T: Ordering](xs: List[T]): List[T] = xs match {
  case Nil => Nil
  case head :: tail => genInsert(head, genSort(tail))
}

val nums = List(1, 4, 3, 2, 6, 5)

genSort(nums)

case class Person(first: String, age: Int)

object Person {

  implicit object PersonOrdering extends Ordering[Person] {
    override def compare(x: Person, y: Person): Int = x.age - y.age
  }

}

implicit object POrdering2 extends Ordering[Person] {
  override def compare(x: Person, y: Person) = 0
}

val people = List(
  Person("Fred", 25),
  Person("Sally", 22),
  Person("George", 53)
)

genSort(people)


trait Printable[T] {
  def print(c: T) = c.toString
}

sealed trait Foo

case class Foo1(c: String) extends Foo

case class Foo2(k: String) extends Foo

implicit object FooPrintable extends Printable[Foo2] {
  override def print(x: Foo2) = x.toString
}

def printable[T <: Foo : Printable](foo: T) = implicitly[Printable[T]].print(foo)

printable(Foo2("hello"))


