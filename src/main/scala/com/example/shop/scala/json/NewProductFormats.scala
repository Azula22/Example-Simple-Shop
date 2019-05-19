package com.example.shop.scala.json

import com.example.shop.scala.models.product.NewProduct
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait NewProductFormats extends DefaultJsonProtocol with UUIDFormatter {

  implicit val newProductFormat: RootJsonFormat[NewProduct] = jsonFormat3(NewProduct.apply)

}