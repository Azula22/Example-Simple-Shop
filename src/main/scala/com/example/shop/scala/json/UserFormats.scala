package com.example.shop.scala.json

import com.example.shop.scala.models.user.User
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait UserFormats extends DefaultJsonProtocol with UUIDFormatter {

  implicit val userFormats: RootJsonFormat[User] = jsonFormat3(User.apply)

}
