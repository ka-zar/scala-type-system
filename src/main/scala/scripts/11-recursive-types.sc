trait CompareT[T] {
  def >(other: T): Boolean
  def <(other: T): Boolean
}

def genInsert[T <: CompareT[T]](item: T, rest: List[T]): List[T] = rest match {
  case Nil                      => List(item)
  case head :: _ if item < head => item :: rest
  case head :: tail             => head :: genInsert(item, tail)
}

def genSort[T <: CompareT[T]](xs: List[T]): List[T] = xs match {
  case Nil => Nil
  case head :: tail => genInsert(head, genSort(tail))
}

/**
  * - We call recursive type definition because it uses trait
  *   that defines something about this class in terms of the class type itself
  * - F-Bounded Polymorphism
  * - Since the type T refers to itself in the definition, it is also called a Recursive Type
  */
case class Distance(meters: Int) extends CompareT[Distance] {
  def >(other: Distance) = this.meters > other.meters
  def <(other: Distance) = this.meters < other.meters
}

val dists = List(Distance(10), Distance(12), Distance(4))
genSort(dists)

case class EngineSize(ci: Int) extends CompareT[EngineSize] {
  def >(other: EngineSize) = this.ci > other.ci
  def <(other: EngineSize) = this.ci < other.ci
}
val engines = List(EngineSize(454), EngineSize(232), EngineSize(356))
genSort(engines)

// something other than numbers
case class Person(first: String, last: String, age: Int) extends CompareT[Person] {
  def >(other: Person) =
    if (last > other.last) true else first > other.first

  def <(other: Person) =
    if (last < other.last) true else first < other.first
}

genSort(List(
  Person("Harry", "Potter", 20),
  Person("Charlie", "Potter", 20),
  Person("Harry", "Dumbledore", 45)
))
