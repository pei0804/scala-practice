object HelloWorld {
  def main(args: Array[String]): Unit = {
    val age: Int = 5
    val isSchoolStarted = false
    if (!isSchoolStarted) {
      println("幼児です")
      return
    }
    if (age >= 1 && age <= 6) {
      println("幼児です")
      return
    }
    println("幼児ではありません")
  }
}

object While {
  def main(args: Array[String]): Unit = {
    var i = 1
    do {
      println(i)
      i+=1
    } while (i <= 10)
  }
}

object For {
  def main(args: Array[String]): Unit = {
    for (x <- 1 to 5) {
      println(x)
    }
  }
}

object ForTwo {
  def main(args: Array[String]): Unit = {
    for (x <- 1 to 5; y <- 1 until 5) {
      println("x=", x, "y=", y)
    }
  }
}

object ForThree {
  def main(args: Array[String]): Unit = {
    for (x <- 1 to 5; y <- 1 to 5; if y == x) {
      println("x=", x, "y=", y)
    }
  }
}

object ForList {
  def main(args: Array[String]): Unit = {
    for (v <- List("v1", "v2")) {
      println(v)
    }
  }
}

object PatternUsage {
  def main(args: Array[String]): Unit = {
    val (x, y) = (1, 2)
    (x, y) match {
      case(1, 2) => println("one two")
      case _ => println("other")
    }
  }
}

object PatternGuard {
  def main(args: Array[String]): Unit = {
    val piyo = "piyo"
    piyo match {
      case p if p.length == 4 => println("piyo")
      case p if p.length >= 5 => println("short piyo")
    }
  }
}

object PatternTuple {
  def main(args: Array[String]): Unit = {
    val p = "piyo"
    val h = "hoge"
    (p, h) match {
      case ("piyo", "fuga") => println("piyo fuga")
      case (_, "hoge") => println("other hoge") // ここヒット
      case ("piyo", _) => println("piyo other")
      case (_, _) => println("other")
    }
  }
}
