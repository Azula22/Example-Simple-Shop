package com.example.shop.scala.json

import com.example.shop.scala.models.requests.PutItem
import spray.json.{DefaultJsonProtocol, RootJsonFormat, RootJsonReader}

trait PutItemFormats extends DefaultJsonProtocol with UUIDFormatter {

  implicit val putItemFormats: RootJsonFormat[PutItem] = jsonFormat3(PutItem)

}
