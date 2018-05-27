
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

    class ClassSample{}

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
    class DBAccess private(url: String, user:String, password: String) {
    }

    object DBAccess {
      def apply(url:String, user: String, password: String) =
        new DBAccess(url, user,password)
    }

    // オブジェクト名（引数）
    val db = DBAccess("", "test", "test")
    // このようにオブジェクトに定義されたapplyメソッドは、ファクトリとして利用するのが一般的なため
    // 混乱を招かないように、他の用途には利用しない方が懸命でしょう
    // なおapplyメソッドはクラスにも定義可能であり、その場合は
    // インスタンス（引数）で呼び出せます
    db("test2", "test2", "test2")
  }
}
