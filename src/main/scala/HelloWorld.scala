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

object ForQuestion {
  def main(args: Array[String]): Unit = {
    for (x <- 1 to 100; )
  }
}
