package com.example.shop.scala.json

import com.example.shop.scala.models.product.NewProduct
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait NewProductFormats extends DefaultJsonProtocol with UUIDFormatter {

  implicit val productFormat: RootJsonFormat[NewProduct] = jsonFormat3(NewProduct.apply)

}