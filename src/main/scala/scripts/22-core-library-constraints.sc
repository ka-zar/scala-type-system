val intNumT = implicitly[Numeric[Int]]
intNumT.times(5, 8)

val doubleNumT = implicitly[Numeric[Double]]
doubleNumT.times(5.0, 8.0)


// =:= Type Equivalence
case class Cell[T](item: T) {
  def *[U: Numeric](other: Cell[U])(implicit ev: T =:= U): Cell[U] = {
    val numClass = implicitly[Numeric[U]]
    Cell(numClass.times(this.item, other.item))
  }
}

val stringCell = Cell("Hello")
val intCell = Cell(6)

intCell * Cell(7)

//stringCell * Cell("there")


List(1, 2, 3).sum
