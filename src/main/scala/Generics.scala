import scala.sys.process.processInternal.OutputStream
// ジェネリクス
// タイプセーフで汎用的な型を定義

// 型パラメータを定義する
object Generics {
  def main(args: Array[String]): Unit = {
    // クラスに定義する場合は、クラス名の後に書く
    // ScalaではAからアルファベット順につけるのが慣習となっています
    class HelloWorld[A]

    // メソッドにて定義する場合は、メソッド名の後に書く
    def hello[A](a: A) =
      println(a)

    // ワイルドカードは _ のように記述します　Javaでいう＜？＞に相当
    def execute(h: HelloWorld[_]) =
      println(h)

    // 型パラメーターが２つの場合、中置記法を利用できます
    // Map[Int, String]
    val map: Int Map String = Map()
  }
}

// 型パラメータに変位を指定したい
// 受け取るインスタンスに制約をつける場合

// 非変 [A]  指定した型のみ代入できる　通常のJavaジェネリクスと同じ
// 共変 [+A] 指定した型またはサブクラスを代入できる Javaの<? extend MyClass>に相当
// 反変 [-A] 指定した型またはスーパークラスを代入できる Java<? super MyClass>に相当

// + - は変位指定アノテーションと呼び、コンパイル時に型のチェックを行います
object Generics2 {
  def main(args: Array[String]): Unit = {
    // 非変
    // 引数と戻り値の型として利用可能
    class NonVariant[A]
    // 型パラメータはStringのみ代入可能
    val v: NonVariant[String] = new NonVariant[String]

    // 共変
    // 戻り値の型として利用出来る
    class CoVariant[+A]
    // 型パラメータはAnRefおよびそのサブクラス(Stringのみ代入可能)
    val v1: CoVariant[AnyRef] = new CoVariant[AnyRef]
    val v2: CoVariant[AnyRef] = new CoVariant[String]

    // 反変
    // 引数の型として利用できる
    class ContraVariant[-A]
    // 型パラメータはStringおよびそのスーパークラスのみ代入可能
    val v3: ContraVariant[String] = new ContraVariant[String]
    val v4: ContraVariant[String] = new ContraVariant[AnyRef]
  }
}

// 制限付き型パラメータを定義したい
// Javaの<T extends String>のようにScalaにも型パラメータに指定出来る型（クラス）を制限する境界がいくつか用意されています

// 上限境界 [A <: MyClass] 型パラメータAは、MyClassまたはそのサブクラスに制限する Javaの<A extends MyClass>に相当
// 下限境界 [A >: MyClass] 型パラメータAは、MyClassまたはそのスーパークラスに制限する Javaにこの境界はない
// 可視境界 [A <% MyClass] 型パラメータAは、MyClassとして扱うことが可能（Aは暗黙の型変換によりMyClassへ変換可能なクラスに制限する）

// 様々な境界指定
object Generics3 {
  def main(args: Array[String]): Unit = {
    class SuperClass {}

    class SubClass extends SuperClass {}

    // サブクラスかSuperClassのみ
    class EqualOrSub[A <: SuperClass]

    // スーパークラスかSubClassのみ
    class EqualOrSuper[A >: SubClass]

    // SubClassか暗黙の型変換でSubClassへの変換可能なクラスのみ
    class EqualOrCastable[A <% SubClass]()
  }
}

// 型パラメータによって呼び出すメソッドを変更したい
object Generics4 {
  def main(args: Array[String]): Unit = {
    class HelloWorld[A](param: A) {
      // 型パラメータAがStringの場合、printメソッド呼び出し可能
      def print(implicit evidence: A =:= String) = println(param)

      // 型パラメータAがOutputStreamのサブクラスの場合
      // outputメソッドを呼び出し可能
      def output(implicit evidence: A <:< OutputStream) = println(param)

      // 型パラメータAが暗黙の型変換でLongに変換可能な場合
      // dobleメソッド呼び出し可能
      def double(implicit evidence: A => Long) = param * 2
    }
  }
}
