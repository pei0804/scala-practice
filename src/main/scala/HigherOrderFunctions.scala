// HigherOrderFunctionは、他の関数をパラメーターとして受取、結果として関数を返す
// これはScalaがファーストクラスの値であるため可能です

object HigherOrderFunctions {
  def main(args: Array[String]): Unit = {
    val salaries = Seq(200, 600, 400)
    val dobuleSalary = (x: Int) => x * 2
    val newSalaries = salaries.map(dobuleSalary)
    println(newSalaries)
  }
}

object HigherOrderFunctions2 {
  def main(args: Array[String]): Unit = {
    val salaries = Seq(200, 600, 400)
    val newSalaries = salaries.map(x => x * 2)
    println(newSalaries)
  }
}

object HigherOrderFunctions3 {
  def main(args: Array[String]): Unit = {
    val salaries = Seq(200, 600, 400)
    val newSalaries = salaries.map(_ * 2)
    println(newSalaries)
  }
}

// メソッドを関数に強制する
case class WeeklyWeatherForecast(temperatures: Seq[Double]) {
  private def convertCtoF(temp: Double):Double = temp * 1.8 + 32
  def forecastInFahrenheit: Seq[Double] = temperatures.map(convertCtoF)
}

// もし、higher-order functionを使わないと以下のようになります
object SalaryRaiser {

  def smallPromotion(salaries: List[Double]): List[Double] =
    salaries.map(salary => salary * 1.1)

  def greatPromotion(salaries: List[Double]): List[Double] =
    salaries.map(salary => salary * math.log(salary))

  def hugePromotion(salaries: List[Double]): List[Double] =
    salaries.map(salary => salary * salary)
}

object SalaryRaiserHigherFunc {

  private def promotion(salaries: List[Double], promotionFunction: Double => Double): List[Double] =
    salaries.map(promotionFunction)

  def smallPromotion(salaries: List[Double]): List[Double] =
    promotion(salaries, salary => salary * 1.1)

  def bigPromotion(salaries: List[Double]): List[Double] =
    promotion(salaries, salary => salary * math.log(salary))

  def hugePromotion(salaries: List[Double]): List[Double] =
    promotion(salaries, salary => salary * salary)
}

object CallUrlBuilder{
  def main(args: Array[String]): Unit = {
    def urlBuilder(ssl: Boolean, domainName: String): (String, String) => String = {
      val schema = if (ssl) "https://" else "http://"
      (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
    }

    val domainName = "www.example.com"
    def getURL = urlBuilder(ssl=true, domainName)
    val endpoint = "users"
    val query = "id=1"
    val url = getURL(endpoint, query) // "https://www.example.com/users?id=1": String
    println(url)
  }
}

