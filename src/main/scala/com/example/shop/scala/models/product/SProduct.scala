package com.example.shop.scala.models.product

import java.util.UUID

import com.example.shop.scala.json.UUIDFormatter
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object SProduct {

  import DefaultJsonProtocol._
  import UUIDFormatter._

  implicit val sProductFormat: RootJsonFormat[SProduct] = jsonFormat4(SProduct.apply)

}

case class SProduct(id: UUID, price: Double, description: String, amount: Int)
