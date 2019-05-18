package com.example.shop.scala.json

import com.example.shop.scala.models.product.SProduct
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait SProductFormats extends DefaultJsonProtocol with UUIDFormatter {

  implicit val sProductFormat: RootJsonFormat[SProduct] = jsonFormat4(SProduct.apply)

}
