package com.example.shop.scala.json

import com.example.shop.scala.models.user.NewUser
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait NewUserFormats extends DefaultJsonProtocol {

  implicit val newUserFormats: RootJsonFormat[NewUser] = jsonFormat2(NewUser.apply)

}
