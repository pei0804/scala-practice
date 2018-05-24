object ForType {
  def main(args: Array[String]): Unit = {
    // 1 ~ 10
    for (i <- 1 to 10) {
      println(i)
    }
    println("---")

    // 2ずつインクリメントしながら10まで
    // 変数 <- コレクションの部分はジェネレーターと呼ばれている
    // また、iは再代入することが出来ない（valと同じ）
    for (i <- 1 to(10, 2)) {
      println(i)
    }
    println("---")

    val range = Range(1, 10)
    for (i <- range) {
      println(i)
    }
    println("---")

    val list = List("A", "B", "C")
    for (e <- list) {
      println(e)
    }
    println("---")

    val list2 = List("Java", "Scala", "Clojure")
    for (lang <- list2 if !lang.startsWith("S")) {
      println(lang)
    }
    println("---")

    for (lang <- list2
         if !lang.startsWith("J")
         if !lang.startsWith("C")) {
      println(lang)
    }
  }
}

// Forで新しいコレクションを作成したい
object ForYield {
  def main(args: Array[String]): Unit = {
    val list = List("Java", "Scala", "Go")
    val result = for(lang <- list if !lang.startsWith("J")) yield lang
    println(result) // Scala, Go

    val result2 = for(lang <- list) yield lang.toUpperCase()
    println(result2) // JAVA, SCALA, GO
  }
}

object ForNest {
  def main(args: Array[String]): Unit = {
    for(x <- 1 to 5) {
      for (y <- 1 to 2) {
        println("x=%d, y=%d".format(x, y))
      }
    }

    for(x <- 1 to 5; y<- 1 to 2) {
      println("x=%d, y=%d".format(x, y))
    }

    val nestedList: List[List[String]] = List(
      List("Java", "C#"),
      List("PHP", "Perl")
    )

    for (row <- nestedList; item <- row if item.startsWith("J")) {
      println(item)
    }
  }
}

