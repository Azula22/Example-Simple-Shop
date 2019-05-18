package com.example.shop.scala.models.product

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object NewProduct {

  import DefaultJsonProtocol._

  implicit val productFormat: RootJsonFormat[NewProduct] = jsonFormat2(NewProduct.apply)

}

case class NewProduct(price: Double, description: String)
