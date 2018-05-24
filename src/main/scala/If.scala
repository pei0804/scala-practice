object IfList {
  def main(args: Array[String]): Unit = {
    val str = "test, aiueo"
    val list: List[String] = if(str != null) {
      str.split(",").toList
    } else {
      Nil
    }
    println(list)
  }
}

object If2 {
  def main(args: Array[String]): Unit = {
    val value: String = "aa"
    // nullだったら空文字、そうでなければ値を入れる
    val result = if(value == null) "" else value
    println(result)
  }
}
