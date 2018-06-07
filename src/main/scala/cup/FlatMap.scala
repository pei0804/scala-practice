package cup

object MapExample {
  def main(args: Array[String]): Unit = {
    val l: List[Int] = List(1, 2, 3)
    println(l.map(_ + 1))

    val l2: List[String] = List("1", "2", "3")
    println(l2.map(_ + "A"))

    println(l2.map(_.length))
  }
}

object ListExample {
  def main(args: Array[String]): Unit = {
    val l: List[List[String]] = List(List("A", "B", "C"), List("D", "E"))
    // mapは複数のリストからなるリストを返すのに対して
    // flatMapは要素のリストを全て連結した単一のリストを返している
    println(l.map(_.toList)) // List(List(A, B, C), List(D, E))
    println(l.flatMap(_.toList)) // List(A, B, C, D, E)
    println(l.flatMap(_.toList).flatMap(_ + "!")) // List(A, !, B, !, C, !, D, !, E, !)

    // List.rangeは一定範囲に含まれる全ての整数のリストを作るメソッド
    // 1度目は、1~5未満
    // 2度目は、最初のリストの各要素 iを使って1以上i未満の整数を生成している
    // 最終的にはタプルからリストを作る
    val l2 = List.range(1, 5).flatMap(
      i => List.range(1, i).map(j => (i, j))
    )
    println(l2) // List((2,1), (3,1), (3,2), (4,1), (4,2), (4,3))

    // foreachはUnitが返り値なので、結果値を受け取りたい時は以下のようにする
    var sum = 0
    List(1, 2, 3, 4, 5).foreach(sum += _)
    println(sum) // 15

    // フィルタリング
    // T => Booleanの式を取り、trueのものを集めてリストにする
    println(List(1, 2, 3, 4, 5).filter(_ % 2 == 0)) // List(2, 4)

    // 分解すると下のやつと同じ
    val f = List(1, 2, 3, 4, 5).filter(i => {
      i % 2 == 0
    })
    println(f)

    // パーティション
    // trueとfalseのリストを作れる
    println(List(1, 2, 3, 4, 5).partition(_ % 2 == 0)) // (List(2, 4),List(1, 3, 5))

    // find
    // 与えられた述語関数を満足させる全ての要素のリストではなく、そのような最初の要素を返す
    // xs find pは演算子としてリストxsと述語関数pを取る
    // 返されるのオプション値である
    println(List(1, 2, 3, 4, 5).find(_ % 2 == 0)) // Some(2)
    println(List(1, 2, 3, 4, 5).find(_ == 0)) // None

    // takeWhile
    // 先頭から取れるだけ連続で要素を取得する
    println(List(1, 2, 3, -4, 5).takeWhile(_ > 0)) // List(1, 2, 3)

    // dropWhile
    // 先頭から削除できるだけ連続で要素を取得する
    println(List(1, 2, 3, -4, 5).dropWhile(_ > 0)) // List(-4, 5)

    // span
    // takeWhileとdropWhileの組み合わせ
    println(List(1, 2, 3, -4, 5).span(_ > 0)) // (List(1, 2, 3),List(-4, 5))

    // forall
    // 全て述語関数通りの場合はtrue
    println(List(0, 0, 0).forall(_ == 0)) // true

    // exists
    // 一つでも述語関数の通りの場合true
    println(List(0, 1, 3).exists(x => x != 0)) // true

    // http://dwango.github.io/scala_text_previews/trait-tut/collection.html
//    val arr = Array[Int](1, 2, 3, 4, 5)
//    swapArray(arr)(1, 2)
//
//    def swapArray[T](arr: Array[T])(i: Int, j: Int): Unit = {
//      val tmp = arr(i)
//      arr(i) = arr(j)
//      arr(j) = tmp
//    }
//    println(arr.map(_.toString()).flatMap(_.toList))
  }
}

