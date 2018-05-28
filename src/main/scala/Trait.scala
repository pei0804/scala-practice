
// Javaで言うと、メソッドを実装できるインターフェイスのようなもの
// Javaのインターフェイスは定義されたすべてのメソッドを実装クラスで実装する必要があり
// 例えば、各実装クラスで同じ処理になるメソッドも各クラスで実装する必要があります

// インターフェイスを実装し共通処理を行ったAbstractクラスを用意することもできますが
// このようなAbstractクラスを継承できるのは、一つのみで多重継承はできません。
// それに対して、Scalaではといれいとに共通処理のメソッドを実装しておくことで
// ミックスイン（Javaのimplements）した各クラスで実装する必要がなくなります
// さらにトレイとは複数をミックスインすることが可能です

// 同じ処理を共通化しつつ、同じインターフェイスを共通化も出来る

// アクセス修飾子 trait トレイト名 {
//   ここにメソッドやフィールド、コンストラクタを定義する
//   抽象メンバーも定義可能
// }

// 特徴
// 通常のメンバーおよび抽象メンバーの両立を定義出来る
// 複数のトレイとをミックスイン出来る
// クラスを継承出来る
// インスタンス生成時にトレイとをミックスイン出来る
// トレイとの中にトレイトやクラスを定義出来る
// 基本コンストラクタは必ず引数なしで、補助コンストラクタはサポートしていない
object Trait {
  def main(args: Array[String]): Unit = {
    trait TraitSample {
      val value: String

      def name = "TraitSample"
    }
  }
}

// トレイとをミックスインしたい
// トレイとのミックスインには、withキーワードを使います。ただし、継承するクラスがない場合
// 最初のトレイとのみextendsキーワードを使います

// クラス継承なし
// class クラス名 extends トレイト

// クラス継承ありミックスイン
// class クラス名 extends スーパークラス with トレイト

// 複数
// class クラス名 extends トレイト1 with トレイト2
// class クラス名 extends スーパークラス with トレイト1 with トレイト2

// インスタンス生成時にトレイトをミックスインする
object Trait2 {
  def main(args: Array[String]): Unit = {
    trait TraitString {
      def string: String
    }
    trait TraitPrint {
      def print() = println("TraitPrint")
    }

    class ClassSample {}

    val sample = new TraitString {
      override def string: String = "TraitString"
    }

    val sample2 = new TraitPrint with TraitString {
      override def string: String = "TraitString2"
    }

    // クラスのインスタンス生成時にミックスイン
    val sample3 = new ClassSample with TraitPrint
  }
}

// トレイトの抽象メソッドを拡張したい
// トレイト内でsuperキーワードを使って抽象メソッドをよびだしている場合
// 実際に実行されるメソッドはミックスインされた時に決定します
object Trait3 {
  def main(args: Array[String]): Unit = {
    trait Executor {
      // 抽象メソッド
      def handle()
    }

    // DefaultExecutorトレイトをミックスイン出来るのは
    // Executorトレイトをミックスインするクラスやインスタンスのみ
    trait DefaultExecutor extends Executor {
      // Executor#handleメソッドの実行前後にログを出力
      abstract override def handle(): Unit = {
        println("start")
        super.handle()
        println("end")
      }
    }
    // superでExecutor#handleメソッドを呼び出していますが
    // この時点ではまだどのような処理が実行されるか決定していません
    // このようにトレイトのメソッドからsuperで親のトレイトの中庸メソッドを呼び出す場合
    // 以下のようにabstract overrideキーワードをつけます
    // abstract override def handle(): Unit = {
    class OutputConsole extends Executor {
      def handle() = println("handle execute")
    }
    val exe = new OutputConsole with DefaultExecutor
  }
}

// 継承元を指定してメソッドを呼び出したい
// 以下のようにスーパークラスやトレイトを指定してメソッドを呼び出すことができます
// super[クラス名またはトレイト名]
// 継承元を指定したメソッド実行
object Super {
  def main(args: Array[String]): Unit = {
    class SuperClass {
      def print() = println("SuperClass")
    }

    trait TraitSample {
      def print() = println("TraitSample")
    }

    class MainClass extends SuperClass with TraitSample {
      override def print(): Unit = {
        super.print()
        super[TraitSample].print()
        super[SuperClass].print()
      }
    }
  }
}

// 抽象フィールドが初期化されるタイミングを知りたい
// トレイトに定義した抽象メンバーは実装クラスで値を定義しますが、コンストラクタはトレイト、
// 実装クラスの順に実行されます。そのため、トレイトの中で抽象メンバーを利用する場合
// トレイトのコンストラクタが実行されるタイミングではまだ初期化されません
// なぜなら、実装クラスで具体が書かれているから
object Lazy {

  // 間違ったコード
  trait TraitSample {
    // 抽象フィールド
    val value: String

    // 抽象フィールドを利用
    val hello = "Hello" + value
  }

  class ClassSample extends TraitSample {
    // 抽象フィールドを実装
    val value = "Scala"
  }

  def main(args: Array[String]): Unit = {
    // コンストラクタはTraitSample -> ClassSampleの順に実行される
    val c = new ClassSample
    println(c.hello) // Hello null まだ、ClassSampleの評価が終わっていない時の評価されたためnullとなった
  }
}

object Lazy2 {

  // 正しいコード
  trait TraitSample {
    // 抽象フィールド
    val value: String

    // 抽象フィールドを利用
    // lazyによる遅延評価
    lazy val hello = "Hello" + value
  }

  class ClassSample extends TraitSample {
    // 抽象フィールドを実装
    val value = "Scala"
  }

  def main(args: Array[String]): Unit = {
    // コンストラクタはTraitSample -> ClassSampleの順に実行される
    // しかし、lazyによってhelloフィールドは実際にフィールドが参照されるまで評価がされないので
    // 値が正しく取得できた
    val c = new ClassSample
    println(c.hello) // Hello Scala!
  }
}

// シングルトンを定義したい
// シングルトンはobjectキーワードを使って定義します
// アクセス修飾子 object オブジェクト名 {
//     ここにメソッドやフィールドを定義する
// }
// シングルトンのインスタンスは自動生成されるので
// コンストラクタに引数を指定できず
// 補助コンストラクタも使用できません
// シングルトンのインスタンスには、オブジェクト名でアクセスします

// objectはstaticではない
// 唯一のインスタンスであって、staticではありません Scalaはすべてがオブジェクトであり、staticをサポートしていません
object Singleton {
  def main(args: Array[String]): Unit = {
    object Format {
      def format(input: String) = {
        println("いえい")
      }
    }
  }
}

// コンパニオンオブジェクトってなに？
// 同じ名前のクラスとシングルトンを一つのソース・ファイルに定義した場合
// そのシングルトンはコンパニオンオブジェクトとなります
// クラスとコンパニオンオブジェクトは、お互いprivateメンバーにアクセス可能という特徴があり
// 主にファクトリとして利用されます
// ファクトリメソッドはapplyメソッドに定義します

// applyメソッドを定義する
object FactoryApply {
  def main(args: Array[String]): Unit = {
    class DBAccess private(url: String, user: String, password: String) {
    }

    object DBAccess {
      def apply(url: String, user: String, password: String) =
        new DBAccess(url, user, password)
    }

    // オブジェクト名（引数）
    val db = DBAccess("", "test", "test")
    // このようにオブジェクトに定義されたapplyメソッドは、ファクトリとして利用するのが一般的なため
    // 混乱を招かないように、他の用途には利用しない方が懸命でしょう
    // なおapplyメソッドはクラスにも定義可能であり、その場合は
    // インスタンス（引数）で呼び出せます
//    db("", "test2", "test2")
  }
}

// 抽出子
// unapplyメソッドを定義したオブジェクトです
// unapplyメソッドには、applyメソッドとは逆のことを行う処理を記述します
object Unapply {
  def main(args: Array[String]): Unit = {
    class Book private
    (val title: String, val author: String, val publisher: String, val price: Int)

    object Book {
      def apply(title: String, author: String, publisher: String, price: Int): Book =
        new Book(title, author, publisher, price)

      def unapply(book: Book): Option[(String, String, String, Int)] =
        Some(book.title, book.author, book.publisher, book.price)
    }

    val book = Book("Seasar2徹底入門", "ぺい", "翔泳社", 3990)

    // 抽出子でBookクラスのインスタンスから情報を抽出
    val Book(title, author, publisher, price) = book

    println("タイトル" + title)
    println("著者" + author)
    println("出版社" + publisher)
    println("価格" + price)

    // 可変長の場合はunapplySeqを定義する
    class Authors2 private(val names: String*)

    // Authorsのコンパニオンオブジェクト
    object Authors2{
      // ファクトリメソッド
      def apply(names: String*): Authors2 = new Authors2(names:_*)

      def unapplySeq(authors: Authors2): Option[List[String]] = Some(authors.names.toList)
    }
  }
}

// CaseClass
// いくつかのメソッドやコンパニオンオブジェクトを自動生成するクラスです
// 以下のようなメソッドを自動で生成されます

// 基本コンストラクタの引数にvalを付加
// 上記のフィールドを使用してequals, hashCode, toStringメソッドを実装
// インスタンスの型が一致する場合はtrueを返すcanEqualメソッドを実装
// フィールドをコピーして新規インスタンスを生成するcopyメソッドを実装、変更したいフィールドのみ指定する
// コンパニオンオブジェクトを生成し、apply,unapply,unapplySeqを実装

// case class クラス名（引数）

case class A()

// パッケージオブジェクト
// クラス、メソッド、型の別名、暗黙の型変換などを任意のパッケージのメンバーとする構文
// 例えば、jp.sf.amateras.scala.oopパッケージにLogクラスがあり
// このLogクラスをjp.sf.amateras.scala.oop.renewalパッケージへ移動したとします
// すると、Logクラスの参照元すべてに変更が発生し、下位互換性が失われます
// このような場合、パッケージオブジェクトを別名で定義し
// パッケージ内ではその別名を使ってクラスを参照することで
// 参照元の変更なく下位互換性を担保できます
// パッケージオブジェクトは、ファイル名「package.scala」に定義するのが慣習です
// 各パッケージに一つのみパッケージオブジェクトを定義できます

// このようにしておくことで、jp.sf.amateras.scala.oopパッケージでは
//「jp.sf.amateras.scala.oop.Log」というクラス名で
// 「jp.sf.amateras.scala.oop.renewal.Log」を参照できるようになります

//package jp.sf.amateras.scala
//
//package object oop {
//  type Log = jp.sf.amateras.scala.oop.renewal.Log
//}

//import jp.sf.amateras.scala.oop._
//val log = new Log

// 列挙型

object Enum {
  def main(args: Array[String]): Unit = {
    object Sex extends Enumeration {
      // 各列挙子の型は、Enumeration#Valueめそっどの戻り値であるValueクラス（実体はValクラス）
      type Sex = Value
      val Man, Woman = Value

      val sex = Sex
      // 列挙子の名前
      val name = sex.Man.toString()
      // 列挙子のID
      val id = sex.Man.id
      // 列挙子の名前から列挙子を取得
      val s1 = sex.withName("Man")
      // 列挙子のIDから列挙子を取得
      val s2 = Sex(1)
      // すべての列挙子を取得
      def printSex = Sex.values foreach println
    }
  }
}
