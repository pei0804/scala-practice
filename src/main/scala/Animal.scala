class Point {
  private var _x = 0 // 外からは見えないようにする
  private var _y = 0
  private val bound = 100

  def x = _x // アクセス用のセッターメソッド
  def x_= (newValue: Int): Unit = {
    if (newValue < bound) _x = newValue else printWarning
  }
//    if (newValue < bound){
//      _x = newValue
//    } else {
//      printWarning
//    }

//  private var _id:Int = _; // _ は型のデフォルト値
//  // getter
//  def id:Int = {
//    println("call getter!");
//    _id;
//  }
//  // setter
//  def id_= (id:Int) = {
//    println("call setter!");
//    _id = id;
//  }

  def y = _y
  def y_= (newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printWarning
  }
  private def printWarning = println("WARNING: Out of bounds")
}

object CallPointClass {
  def main(args: Array[String]): Unit = {
    val point1 = new Point
    point1.x = 99
    point1.y = 99
    print(point1.x)
    // point1._x もちろんこれは無理
  }
}
