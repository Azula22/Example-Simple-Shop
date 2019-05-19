package com.example.shop.scala.controllers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives
import com.example.shop.scala.json.{NewUserFormats, UserFormats}
import com.example.shop.scala.models.user.NewUser
import com.example.shop.scala.repositories.UserRepo

class UserController(userRepo: UserRepo) extends Directives
  with NewUserFormats
  with UserFormats {

  def createUser = (post & path("user") & entity(as[NewUser])) { newUser =>
    complete(userRepo.add(newUser.complete))
  }

  def getUser = (get & path(Segment)) { email =>
    complete(userRepo.get(email))
  }

}
