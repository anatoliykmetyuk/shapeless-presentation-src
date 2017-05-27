package shapelesspresentationsrc

import ShowCsv.ops._

object Main {
  def main(args: Array[String]): Unit = {
    val phone  = Phone ("Samsung", "Galaxy S5"  , 1234567890L)
    val laptop = Laptop("Apple"  , "MacBook Pro", 15.0       )

    val database: List[Device] = List(phone, laptop)
    println(database.showCsv)
  }
}
