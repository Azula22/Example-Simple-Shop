package com.example.shop.scala.models
import java.util.UUID

import spray.json._

object NewUser {

  import DefaultJsonProtocol._

  implicit val newUserReader: RootJsonReader[NewUser] = jsonFormat2(NewUser.apply)

}

case class NewUser(email: String, bankAccount: String) {

  def complete = User(UUID.randomUUID(), email, bankAccount)

}

