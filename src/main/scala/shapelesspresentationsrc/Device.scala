package shapelesspresentationsrc

sealed trait Device {
  val manufacturer: String
  val model: String
}

case class Phone(manufacturer: String, model: String, imei: Long) extends Device
case class Laptop(manufacturer: String, model: String, displaySize: Double) extends Device