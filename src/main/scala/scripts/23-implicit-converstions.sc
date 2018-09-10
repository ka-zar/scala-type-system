import language.implicitConversions

case class Complex(real: Double, imaginary: Double = 0.0) {
  override def toString: String = s"$real $sign ${imaginary.abs}"
  private def sign = if (imaginary >= 0.0) "+" else "-"
  def +(other: Complex) =
    Complex(real + other.real, imaginary + other.imaginary)
}

/**
  * If you define implicit in companion object scala compiler
  * and scala compiler can't find implicit definition it will
  * automatically look into companion object
  */
object Complex {
  implicit def intToComplex(i: Int): Complex = Complex(i)
}

val c1 = Complex(5, 6)
val c2 = Complex(-3, -6)

c1 + c2

c1 + 3

Complex.intToComplex(6) + c1

class TimesInt(i: Int) {
  def times(fn: => Unit): Unit = {
    var x = 0
    while (x < i) {
      x += 1
      fn
    }
  }
}

implicit def intToTimesInt(i: Int): TimesInt = new TimesInt(i)

5 times { println("hello") }

intToTimesInt(5).times { println("hello") }

/**
  * If you extend with AnyVal you will get static method
  * without instantiation of class, can't do in repl
  */
implicit class AnotherTimesInt(i: Int) {
  def anotherTimes(fn: => Unit): Unit = {
    var x = 0
    while (x < i) {
      x += 1
      fn
    }
  }
}

5 anotherTimes { println("hello") }