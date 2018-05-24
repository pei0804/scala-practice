import java.io.FileInputStream

object Either {
  def main(args: Array[String]): Unit = {
    // 型パラメーターで指定した2つの型のうち、どちらか一方の値を持つことができるコンテナです
    // Eitherにメソッドを正常に終了した場合は、メソッドの戻り値が格納され、例外が発生した場合は、例外が格納されます
    // 呼び出し側は、Eitherの状態をチェックすれば、正常かエラーを知ることが出来る
    def readFile(fileName: String): Either[Throwable, String] = {
      val in = new FileInputStream("build.sbt")
      try {
        val buf = new Array[Byte](in.available())
        in.read(buf)
        Right(new String(buf, "UTF-8"))
      } catch {
        case e: Throwable => Left(e)
      } finally {
        in.close()
      }
    }
    val  result: Either[Throwable, String] = readFile("build.sbt")
    result match {
      case Left(e) => e.printStackTrace() // えらー
      case Right(s) => println(s) // 正常
    }
  }
}
