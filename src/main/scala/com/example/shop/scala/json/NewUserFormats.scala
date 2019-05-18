package com.example.shop.scala.json

import com.example.shop.scala.models.user.NewUser
import spray.json.{DefaultJsonProtocol, RootJsonReader}

trait NewUserFormats extends DefaultJsonProtocol {

  implicit val newUserReader: RootJsonReader[NewUser] = jsonFormat2(NewUser.apply)

}
