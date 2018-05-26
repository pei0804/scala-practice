// 関数の生成時に外部の変数を取り込んで動作する関数のこと
object Closer {
  def main(args: Array[String]): Unit = {
    // 引数のIntの無名関数がクロージャ、自由変数はtimes
    def multi(times: Int): Int => Int = {
      i: Int => i * times
    }

    // 自由変数10を取り込んでクロージャ作成
    val tentimes = multi(10)
    println(tentimes(3))

    // 生成されたクロージャを直接呼ぶ
    multi(100)(3)
  }
}
