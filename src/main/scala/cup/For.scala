package cup

import java.io.File

object For {
  def main(args: Array[String]): Unit = {
    val filesHere: Array[File] = new File(".").listFiles()
    for (file <- filesHere)
      println(file)
    //      ./.git
    //      ./.gitignore
    //      ./.idea
    //      ./build.sbt
    //      ./LICENSE
    //      ./project
    //      ./README.md
    //      ./src
    //      ./target

    for (i <- 1 to 4)
      println(i) // 1 2 3 4

    for (i <- 1 until 4)
      println(i) // 1 2 3
  }
}

object For2 {
  def main(args: Array[String]): Unit = {
    // フィルター付きfor
    val filesHere: Array[File] = new File(".").listFiles()
    for (file <- filesHere if file.getName.endsWith(".scala"))
      println(file)

    // 以下と同じこと
    for (file <- filesHere)
      if (file.getName.endsWith(".scala"))
        println(file)

    // 複数のフィルターを使うことも出来る
    val filesHere2: Array[File] = new File(".").listFiles()
    for (file <- filesHere2
         if file.isFile
         if file.getName.endsWith(".scala")
    ) println(file)
  }
}


object ForYield {
  def main(args: Array[String]): Unit = {
    val filesHere: Array[File] = new File(".").listFiles()

    // 新しいコレクションの作成
    def scalaFiles: Array[File] =
      for {
        file <- filesHere
        if file.getName.endsWith(".scala")
      } yield file // for <節> yield <本体>

    val scalaFiles2 = for {
      file <- filesHere
      if file.getName.endsWith(".scala")
    } yield file
  }
}
