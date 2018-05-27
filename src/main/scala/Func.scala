object Func {
  def main(args: Array[String]): Unit = {
    // 関数の定義
    //    (引数名: 引数の型,...) => {
    //      複数の式
    //    }
    // 関数は引数と関数本体を=>でつなげて定義する
    // 式が一つの場合、一行でかける
    val f = (i: Int) => i * 2
    f(1)

    // 引数の型を省略、かつ引数が一つの場合、丸括弧も省略可能
    val f2: Int => Int = (i) => i * 2
    f2(1)

    // 引数の型を省略、かつ引数が一つの場合、丸括弧も省略可能
    val f3: Int => Int = i => i * 2
    f3(1)

    // 引数の使用が1回のみの場合、プレースホルダ公文が利用可能
    val f4: Int => Int = _ * 2
    f4(1)

    // 引数が複数の場合もプレースホルダ公文を利用可能
    // n番目のプレースホルダがn番目の引数となり、引数の定義順に1回のみ使用する
    val f5 = (_: Int) + (_: Int) // (i Int, j:Int) => i + j
    f5(1, 2)

    // 引数なしの場合
    val f6 = () => println("Hello World")
    f6()
  }
}

// 引数に関数を渡す
object Func2 {
  def main(args: Array[String]): Unit = {
    // def メソッド名(引数名: (関数の引数の型,...)) => 関数の戻り値の型 = ...
    //    def exec(f: Int => Boolean) = f(10)
    def exec(f: Int => Boolean): Boolean = {
      f(10)
    }
    // 引数はInt => Booleanの関数
    println(exec(i => i % 2 == 0))
    exec(_ % 2 == 0)

    // 下のやつと同じこと
    def functional(f: Int): Boolean = {
      f % 2 == 0
    }

    println(exec(functional))
  }
}

// 関数を戻り値として返す
object Func3 {
  def main(args: Array[String]): Unit = {
    // 戻り値はInt => Int(引数がIntでIntを返す
    def createFunc(): Int => Int = {
      i: Int => i * 2
    }

    val f = createFunc()
    print(f(1))

    // 戻り値を省略
    def createFunc2(): Int => Int = (i: Int) => i * 2

    def createFunc3(): Int => Int = _ * 2
  }
}

// ネストした関数の定義
object Func4 {
  def main(args: Array[String]): Unit = {
    def list(capital: String) = {
      // ローカル関数は外側の関数の引数にアクセス可能
      def isTokyo(address: String): Boolean = address contains capital

      println(isTokyo("Shibuya, Tokyo"))
    }
  }
}

// メソッドを関数に変換したい
object Func5 {
  def main(args: Array[String]): Unit = {
    def price(total: Int): String = "金額" + total

    // 関数型(Int => String)の引数にメソッドを指定
    def printPrice(f: Int => String) = println(f(1000))

    printPrice(price)

    // 省略することもできる
    val f = price _
  }
}

// 一部の引数のみ指定して関数を呼び出したい
// 関数の部分適用と呼ばれるもの
object Func6 {
  def main(args: Array[String]): Unit = {
    def price(total: Int, discount: Int): Int = total - discount

    // 第二引数を省略
    val f1 = price(100, _: Int)
    // 不足分のdiscountのみ指定
    f1(100)

    // すべての引数を省略
    val f2 = price _
    // すべての引数を指定
    f2(1000, 1)
  }
}

// 部分関数
object Func7 {
  def main(args: Array[String]): Unit = {
    // IllegalArgumentException か IllegalStateException  の場合のみ値をあ返す
    val base: PartialFunction[Throwable, String] = {
      case _: IllegalArgumentException => "Parameter is invalid"
      case _: IllegalStateException => "State is invalid"
    }

    // 常に値を返す
    val ex: PartialFunction[Throwable, String] = {
      case _ => "No support"
    }

    // catchブロックで例外の型に応じて処理を分けるところに部分関数を利用する
    // baseのPartialFunctionを利用
    def check(i: Int) = {
      try {
        if (i < 0) throw new IllegalArgumentException
        "success"
      } catch base
    }

    // orElseメソッドで複数のParialFunctionを連結できる
    def check2(i: Int) = {
      try {
        i / 0
        "success"
      } catch base orElse ex
    }

    // 条件チェックすることもできる
    base.isDefinedAt(new IllegalArgumentException) // true
    base.isDefinedAt(new IndexOutOfBoundsException) // false
  }
}

// カリー化
// 引数が複数ある関数（またはメソッド）を、1つの引数を持つ関数のチェーンとして呼び出せるように変換することです
// 具体的には、カリー化は複数の引数リストを使用して、記述します

object Func8 {
  def main(args: Array[String]): Unit = {
    // （引数名: 型）が引数リスト
    // 変数 = (引数名: 型) => (引数名: 型) => 処理
    val greetFunc = (title: String) => (name: String) => title + name
    // すべての引数リストを適用
    greetFunc("Hello")("Takako") // HelloTakako
    // 引数を順に適用
    val h = greetFunc("Hello")
    h("Takako")

    // メソッドのカリー化
    //    def メソッド名(引数名: 型)(引数名: 型) = {
    //      処理
    //    }
    def greetFunc2(title: String)(name: String) = title + name

    // 最初の引数を適用したgreetFunc2メソッドを関数に変換
    def greet(f: String => String) = f("Takako")

    greet(greetFunc2("hello"))

    // カリー化されたメソッドを部分適用で関数に変換
    val hello = greetFunc2("Hello") _

    // 主な用途は、引数に関数リテラルのような制御構造を渡す場合に利用することで、コードの意図を明確にし、可読性を工向上する
    // このメソッドの呼び出しは、値と制御向上が混在して一見わかりにくいです
    def execute(data: List[String], f: List[String] => String) = {
      f(data)
    }

    val l = List("A")

    def functional(f: List[String]): String = {
      f.toString()
    }

    execute(l, functional)

    // これをカリー化する
    def execute2(data: List[String])(f: List[String] => String) = {
      f(data)
    }

    // このように引数がひとつになり、メソッド呼び出しを{}を利用することで一つのブロックとして渡すように明確に見せれます
    execute2(l) { data =>
      data.toString()
    }
  }
}

// 引数が複数ある関数をカリー化したい
object Func9 {
  def main(args: Array[String]): Unit = {
    val greetFunc = (title: String, name: String) => title + name
    // 関数をカリー化
    val curryFunc = greetFunc.curried
    curryFunc("Hello")("Takako")

    def greetDef(title: String, name: String) = title + name
    // メソッドカリー化
    val curryDef = (greetDef _).curried
    curryDef("Hello")("Takako")
    // どちらも(title: String) => (name: String) => title + name になっている
  }
}
