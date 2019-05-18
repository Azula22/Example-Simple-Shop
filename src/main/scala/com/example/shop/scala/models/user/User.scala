package com.example.shop.scala.models.user

import java.util.UUID

import com.example.shop.scala.json.UUIDFormatter
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object User {

  import DefaultJsonProtocol._
  import UUIDFormatter.uuidFormat

  implicit val userFormat: RootJsonFormat[User] = jsonFormat3(User.apply)

}

case class User(id: UUID, email: String, bankAccount: String)
