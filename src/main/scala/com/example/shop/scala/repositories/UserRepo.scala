package com.example.shop.scala.repositories

import com.example.shop.scala.models.errors.NoUser
import com.example.shop.scala.models.user.User

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future

class UserRepo {

  private val _users: ArrayBuffer[User] = ArrayBuffer.empty //emulation of db

  def add(user: User): Future[User] = {
    _users.+=(user)
    Future.successful(user)
  }

  def get(email: String): Future[User] = {
    _users.find(_.email == email) match {
      case Some(u) => Future.successful(u)
      case None => Future.failed(NoUser(email))
    }
  }

}
