import java.io.{FileInputStream, IOException}

object Throw {
  def main(args: Array[String]): Unit = {
    // 例外をスローする
    throw new RuntimeException("例外テスト")
  }
}

object Exception {
  def main(args: Array[String]): Unit = {
    val in = new FileInputStream("build.sbt")
    try {
      val buf = new Array[Byte](in.available())
      in.read(buf)
      println(new String(buf, "UTF-8"))
    } catch {
      case e: Throwable => {
        e.printStackTrace()
      }
      case e: IOException => {
        // IOExceptionの場合
      }
      case e => {
        // それ以外の例外
      }
    } finally {
      in.close()
    }
  }
}

object TryVal {
  def main(args: Array[String]): Unit = {
    val in = new FileInputStream("build.sbt")
    val content = try {
      // ファイルの内容を文字列で返す
      val buf = new Array[Byte](in.available())
      in.read(buf)
      new String(buf, "UTF-8") // 最後に評価されたこれが戻り値
    } catch {
      case e: Throwable => e.toString // 例外発生した場合は、toString()した文字列を返す
    } finally {
      in.close() // finallyブロックの処理は戻り値には関係ない
    }
  }
}
