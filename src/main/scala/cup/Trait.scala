package cup

// トレイトはScalaのコード再利用の基本単位
// トイレ意図はメソッドとフィールドの定義をカプセル化したものであり
// クラスにミックスインすることによって再利用できる
// クラスの継承では、クラスは1つのスーパークラスしか持てないが
// クラスは任意の個数のトレイトをミックスインできる

object Trait {
  def main(args: Array[String]): Unit = {
    // スーパークラスではないので、クラスと同様にデフォルトのAnyRefがスーパークラスとなる
    trait Philosophical {
      def philosophize(): Unit = {
        println("I consume memory, therefore I am!")
      }
    }
    class Frog extends Philosophical {
      override def toString = "green"
    }

    val frog = new Frog
    frog.philosophize()

    class Animal
    class Frog2 extends Animal with Philosophical {
      override def toString = "green"

      override def philosophize() {
        println("It ain't easy begin")
      }
    }

    val frog2 = new Frog2
    frog2.philosophize()
  }
}

// シンインターフェイスとシンインターフェイス
// トレイトの大きな用途の一つは、クラスが既に持っているメソッドを使って
// 自動的にメソッドを増やすというものである
// つまり、トレイトはシン（貧弱な）インターフェイスを豊かなリッチインターフェイスに変えることが出来る
object Trait2 {
  def main(args: Array[String]): Unit = {
    class Point(val x: Int, val y: Int)

    class Rectangle(val topLeft: Point, val bottomRight: Point) {
      def left: Int = topLeft.x

      def right: Int = bottomRight.x

      def width: Int = right - left

      // いろいろ
    }
    // 以下のような感じのものを
    //    abstract class Componet {
    //      def topLeft: Point
    //      def BottomRight: Point
    //      def left: Int = topLeft.x
    //      def right: Int = BottomRight.x
    //      def width: Int = right - left
    //    }

    trait Rectangular {
      def topLeft: Point

      def BottomRight: Point

      def left: Int = topLeft.x

      def right: Int = BottomRight.x

      def width: Int = right - left
    }

    abstract class Component extends Rectangular {
      // いろいろその他
    }
  }
}


object Trait3 {
  def main(args: Array[String]): Unit = {
    case class Version(major: Int, middle: Int, minor: Int) extends Ordered[Version] {

      import scala.math.Ordered._

      def compare(that: Version): Int = {
        (major, middle, minor).compare((that.major, that.middle, that.minor))
      }
    }
    println(Version(1, 0, 0) > Version(0, 9, 0))
  }
}
