// コレクションは以下の3種類に大別されます
// Seq シーケンス　順序を持った要素の集合
// Set 集合　重複を含まない要素の集合
// Map マップ　キーと値のペア集合
object Collection {
  def main(args: Array[String]): Unit = {
    // コレクションは通常、以下のようにファクトリメソッドを使って生成する
    // 型パラメータとして要素の型を指定します
    // ただし、実際にはファクトリメソッドに渡した要素から
    // 型推論が可能であるため、型を省略しても問題ありません
    val list: List[String] = List[String]("A", "B", "C")
    val list2 = List("A", "B", "C")
  }
}
