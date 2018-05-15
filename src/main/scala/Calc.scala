class Calc (val1: Int, val2: Int){
  println("Create Calc Class val1:" + val1 +" val2:"+ val2)

  def plus(): Unit = {
    println(val1 + val2)
  }

  def min(): Unit = {
    println(val1 - val2)
  }
}

object CallCalcClass {
  def main(args: Array[String]): Unit = {
    val calc = new Calc(1, 2)
    calc.plus()
    calc.min()
  }
}

