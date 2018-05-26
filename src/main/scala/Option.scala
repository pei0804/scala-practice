object Option {
  def main(args: Array[String]): Unit = {
    // Optionサブクラスには、SomeとNoneがあります
    // 値がある場合は、その値をラップしたSomeを
    // 値がない場合はNoneを用いることで、型で値の有無を表現できます
    // Javaでは、このようなケースではnullを使うことが一般的です。

    // 値が存在している
    val option:Option[String] = Some("test") // Some("test")

    // 値を取得
    val result = option.get // nullの場合はNoSuchElementExceptionが発生する

    // 値が空か
    val empty = option.isEmpty // false

    // 値が存在するか
    val defined = option.isDefined
  }
}

object Option2 {
  def main(args: Array[String]): Unit = {
    // 値が存在しない場合
    val option: Option[String] = null // None

    // 値、もしくはnullを取得
    val result = option.orNull // null

    // 値が空か
    val empty = option.isEmpty // true

    // 値が存在するか
    val defined = option.isDefined // false

    // 値が存在しない場合は指定した値を返す
    val result2 = option.getOrElse("default") // default
  }
}

object OptionPattern {
  def main(args: Array[String]): Unit = {
    val option: Option[String] = null // None
    val result: String = option match {
      case Some(x) => x // 値がある時
      case None => "" // 値がない時
    }
  }
}

object OptionTips {
  def main(args: Array[String]): Unit = {
    val option: Option[String] = Some("A")
    option.foreach{value: String => println(value)}
  }
}