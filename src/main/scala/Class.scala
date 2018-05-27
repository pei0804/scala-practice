
// クラス
// アクセス修飾子 class クラス名 {
//   ここにメソッドやフィールドを定義する
// }
object Class {
  def main(args: Array[String]): Unit = {
    class Hello {
    }

    val instance = new Hello

    // インナークラス
    // インナークラスのprivateメンバーは外部クラスから参照できない
    // 外部ラクスのインスタンスが異なり、それぞれ生成したインナークラスは別クラスとなる
    class Outer {

      class Inner {
        private val innerf = "inner"
      }

    }

    val outer1 = new Outer
    val outer2 = new Outer

    val inner = new outer1.Inner

    // outer1のinnerクラスとouter2のinnerクラスは別クラス
    //inner = new outer2.Inner エラーとなる

    // 上記のようなものを使いたい場合は
    var inner2: Outer#Inner = new outer1.Inner
    inner2 = new outer2.Inner // OK
  }
}

// メソッド定義
// アクセス修飾子 def メソッド名(引数名: 引数の型,...): 戻り値の型 = {
// 複数の式
// }

object Class2 {
  def main(args: Array[String]): Unit = {
    class Hello1 {
      def hello(arg: String): Unit = {
        println("Hello")
      }

      // 式が一つの場合、一行でもかける
      def greet(arg: String): String = "Hello World" + arg

      // 通常、戻り値の型は推論できるので省略もできる
      def greet2(arg: String) = "Hello World" + arg

      // 引数なし
      def greet3() = println("Hello WOrld")

      // 引数なし、かつ副作用がない（値取得を目的としていない）場合、丸括弧を省略するのが慣習
      def hello = "Hello World"

      // メソッドの引数はvalなので、再代入できません
      // 戻り値は、最後の式の値になります
      // returnキーワードを使って任意の場所から値を戻すこともできますが、
      // Scalaではなるべきreturnを使わず戻すのが一般的です

      // タプルを使って複数値返すこともできる
      def user: (Int, String) = (1, "Naoki")
    }
  }
}

// 可変長引数を定義
// 引数の型のあとに「*」をつけます、内部では、Seq型として扱います
object Class3 {
  def main(args: Array[String]): Unit = {
    def args(values: String*) = for (s <- values) println(s)

    // 引数なし
    args()

    // 引数1つ
    args("A")

    // 引数2つ委譲
    args("A", "B")

    // Seq型を直接渡す場合は引数のあとに「:_*」をつける必要がある
    val list = List("A", "B")
    args(list: _*)
  }
}

// 暗黙の引数
// 関数、またはメソッドの引数を暗黙引数として定義し
// 暗黙の引数に渡す値を別途用意しておくことです。実行時にその引数を省略できます。
// 暗黙の引数や実際に渡す値は、implicitキーワードを使って定義します。
object Class4 {
  def main(args: Array[String]): Unit = {
    class Tax {
      // 引数rateが暗黙の引数
      def tax(implicit rate: Int) = rate * 0.01
    }

    class VAT {
      // 暗黙の引数へ実際に渡す値
      implicit val vat = 5
      //      implicit val vat1 = 5 // 複数同じ型には使えない

      def calc = {
        val tax = new Tax
        // 暗黙の引数を利用
        println(tax.tax) // 0.05
        // 明示的に引数を指定
        tax.tax(10)
      }
    }

    val v = new VAT
    v.calc
  }
}

// コンストラクタ
object Class5 {
  def main(args: Array[String]): Unit = {
    // 引数なしコンストラクタ
    class Hello {
      val value = "Hello World"
      print(value)
    }

    // 引数ありコンストラクタ
    class Hello2(name: String) {
      val n = name
      println(n)
    }

    // 補助コンストラクタ
    class Hello3(x: Int, y: Int) {
      println(x + y)

      def this(z: Int) = {
        this(z, 0) // 基本コンストラクタを呼ぶ 必ず書く必要がある
        println("補助コンストラクタ")
      }
    }
  }
}

// クラスのメンバーとなるコンストラクタ引数
// Scalaはコンストラクタの引数にvalまたは、varをつけるとクラスのメンバーとなり
// 以下のように自動生成します
// privateフィールド、引数名と同じpublicな取得用アクセスメソッド
// varのみ「引数名_=」のpublicな設定用アクセサメソッド
object Class6 {
  def main(args: Array[String]): Unit = {
    class ScalaSample(val count: Int, var name: String) {
    }

    val sample = new ScalaSample(1, "Takako")
    // valのcountはgetterのみ生成される
    println(sample.count) // 1
    // varのnameはgetterとsetterが生成される
    println(sample.name) // Takako
    sample.name_=("Naoki") // Naokiが設定される
    sample.name = "Naoki" // これでも同じ
  }
}

// 継承
object Class7 {
  def main(args: Array[String]): Unit = {
    // スーパークラス
    class SuperClass(val id: Int)

    // サブクラス
    class SubClass(id: Int, val name: String) extends SuperClass(id) {
      def this(name: String) = this(100, name)
    }

    final class CantExtend
  }
}

// sealed
// クラスに付与するキーワードの一つです。sealedが付与されたクラスは、シールドクラスと呼ばれ、
// このクラスを継承できるのは、同一ソースファイル内のクラスのみに制限されます。別ファイルのクラスは継承できない。
// シールドクラスを継承したサブクラスは、全ての同一のソース・ファイルに定義するという特徴から、存在しえるサブクラスがコンパイル時に確定します
// そのため、シールドクラスをパターンマッチした時、match式の網羅性をコンパイラがチェックできます。もし、ケースクラスを新たに追加し
// match式の変更を忘れるとコンパ裏が警告を出してくれるため、パターン漏れを事前に検出できます。
object Class8 {
  def main(args: Array[String]): Unit = {
    // シールドクラス
    sealed abstract class Message

    // シールドクラスを継承したケースクラス
    case class TextMessage() extends Message
    case class ObjectMessage() extends Message
    case class ByteMessage() extends Message

    // パターンマッチ
    // ByteMessageクラスに対応するパターンが不足しているので
    // 「warning: match is not exhaustive」という警告が出る
    def message(m: Message): Unit = m match {
      case TextMessage() => println("TextMessage")
      case ObjectMessage() => println("ObjectMessage")
      // case ByteMessage() => println("ByteMessage")
    }
  }
}

// 抽象クラスを定義したい
// abstract class 抽象クラス名 {
//   抽象メンバーを定義
//   通常メンバーも定義可能
// }
// Javaと違うとこ
// 抽象メンバーにabstractキーワードは不要（実装を書かなければ抽象メンバーになる）
// メソッドだけではなく、フィールドを抽象化できる
object Class9 {
  def main(args: Array[String]): Unit = {
    // 抽象メンバーを定義する
    abstract class AbstractClass {
      // 抽象フィールド
      val value: String

      // 抽象メソッド
      def hello(arg: String): String
//      def hello(arg: String) // 戻り値を指定しない場合はUnitになる
    }

    // 抽象メンバーを実装する
    class extendsClass extends AbstractClass {
      // 抽象フィールドを実装
      val value = "hello"
      // 抽象メソッドを実装
      def hello(arg:String) = "hello" + arg
    }
  }
}

// フィールドやメソッドをオーバーライドしたい
// overrideキーワードを付与することで、フィールドやメソッドをオーバーライドできます。Javaとは違い、オーバーライドする場合は
// overrideキーワードが必須です
object Class10 {
  def main(args: Array[String]): Unit = {
    class SuperClass {
      val id = 1
      var key = "key1"
      def name = "SuperClass"
      final val finalName = "Final" // overrideできない
    }

    class SubClass extends SuperClass {
      // valのオーバーライドにはoverrideキーワードが必要
      override val id: Int = 100
      // var のオーバーライドには、overrideキーワードは不要
      key = "key 100"

      // メソッドのオーバーライドにはoverrideキーワードが必要
      override def name: String = "SubClass" + super.name
    }
  }
}
