package com.example.shop.scala.json

import java.util.UUID

import spray.json._

trait UUIDFormatter {

  implicit val uuidFormat: RootJsonFormat[UUID] = new RootJsonFormat[UUID] {
    override def read(json: JsValue): UUID = UUID.fromString(
      json.convertTo[String](DefaultJsonProtocol.StringJsonFormat)
    )
    override def write(obj: UUID): JsValue = JsString(obj.toString)
  }

}
