import scala.reflect._

val s: String = "hello"

classOf[String]

val stringClassTag = classTag[String]

def getClassTag[T: ClassTag](x: T): ClassTag[T] = classTag[T]

val intCT = getClassTag(10)

intCT.runtimeClass

getClassTag("10").runtimeClass


def isA[T: ClassTag](x: Any): Boolean = x match {
  case _: T => true
  case _ => false
}

isA[Int](7)
isA[Int]("hello")

isA[Map[String, Int]](List(1, 2, 4))
isA[Map[String, Int]](Map("hello" -> 7))
isA[Map[String, Int]](Map("hello" -> "foo"))

import reflect.runtime.universe._

val tt = typeTag[Map[String, Int]]

val theType = tt.tpe

theType.baseClasses

theType.typeArgs

theType.=:=(typeTag[Map[String, Int]].tpe)

case class Tagged[A](value: A)(implicit val tag: TypeTag[A])

val tag1 = Tagged(Map("hello" -> 7))
val tag2 = Tagged(Map("hello" -> "foo"))


def taggedIs[A, B](x: Tagged[Map[A, B]]): Boolean = x.tag.tpe match {
  case t if t =:= typeOf[Map[Int, String]] => true
  case _ => false
}


taggedIs(tag1)
taggedIs(tag2)
