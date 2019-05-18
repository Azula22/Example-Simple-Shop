package com.example.shop.scala.models

import java.util.UUID

import com.example.shop.scala.json.UUIDFormatter
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object SProduct {

  import DefaultJsonProtocol._
  import UUIDFormatter.uuidFormat

  implicit val productFormat: RootJsonFormat[SProduct] = jsonFormat3(SProduct.apply)

}

case class SProduct(id: UUID, price: Double, description: String)
