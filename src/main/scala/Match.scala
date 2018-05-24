object Match {
  def main(args: Array[String]): Unit = {
    val lang: String = "Java"
    lang match {
      case "Java" => {
        println(lang)
      }
      case "Scala" | "Java" => {
        println("Scala | Java")
      }
      case _ => {
        println("Other")
      }
    }
  }
}

object Match2 {
  def main(args: Array[String]): Unit = {
    val Man = 0
    val Woman = 1
    val i = 1
    i match {
      case Man => println("Man")
      case Woman => println("Woman")
      case _ => throw new IllegalArgumentException
    }
  }
}

object Match3 {
  def main(args: Array[String]): Unit = {
    val man = 0
    val woman = 1
    val i = 1
    i match {
        // 大文字で書く必要が普通はあるが、バッククオートつけることでなんでもよくなる
      case `man` => println(i)
      case `woman` => println(i)
    }
  }
}

object Match4 {
  def main(args: Array[String]): Unit = {
    val i: Int = 0
    val lang: String = i match {
      case 0 => "Java"
      case 1 => "PHP"
      case _ => "Go"
    }
    println(lang)
  }
}

object MatchType {
  def main(args: Array[String]): Unit = {
    def getLength(value: AnyRef): Int =
      value match {
        case x:String => x.length
        case x:Array[_] => x.length
        case x:Iterable[_] => x.size
        case _ => throw new IllegalArgumentException
      }

    def patternMatch(value: AnyRef): Unit =
      value match {
          // 型情報を失いたくない時は明示的に書く
        case _:Array[String] => println("Array[String]")
        case _:Array[Int] => println("Array[Int]")
      }
  }
}

object PatternGuard {
  def main(args: Array[String]): Unit = {
    def patternGuardSample(tuple: (Any, Any)) =
      tuple match {
        case(x, y) if x == y => {
          //タプル要素が同じだった場合
        }
      }
  }
}