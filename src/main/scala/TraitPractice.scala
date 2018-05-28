import scala.collection.mutable.ArrayBuffer
// クラス間でインターフェイスとフィールドを共有する時に使う

trait HairColor

// ジェネリック型や抽象メソッドで便利に使える
trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}

class IntIterator(to: Int) extends Iterator[Int] {
  private var current = 0
  override def hasNext: Boolean = current < to
  override def next(): Int =  {
    if (hasNext) {
      val t = current
      current += 1
      t
    } else 0
  }
}

object CallTrait {
  def main(args: Array[String]): Unit = {
    val iterator = new IntIterator(10)
    println(iterator.next())
    println(iterator.next())
  }
}

// Trait abstructの使い分け
// https://gist.github.com/gakuzzzz/10081860
trait Pet {
  val name: String
}

class Cat(val name: String) extends Pet

class Dog(val name: String) extends Pet

object CallPet {
  def main(args: Array[String]): Unit = {
    val dog = new Dog("Harry")
    val cat = new Cat("Sally")
//    val animals = ArrayBuffer.empty[Pet]
//    animals.append(dog)
//    animals.append(cat)
//    animals.foreach(pet => println(pet.name))
  }
}
