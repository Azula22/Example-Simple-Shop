package com.example.shop.scala.models.user

import java.util.UUID

case class NewUser(email: String, bankAccount: String) {

  def complete = User(UUID.randomUUID(), email, bankAccount)

}

