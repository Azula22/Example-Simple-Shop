package com.example.shop.scala.json

import com.example.shop.scala.models.requests.PutItem
import spray.json.{DefaultJsonProtocol, RootJsonReader}

trait PutItemFormats extends DefaultJsonProtocol with UUIDFormatter {

  val putItemFormats: RootJsonReader[PutItem] = jsonFormat3(PutItem)

}
