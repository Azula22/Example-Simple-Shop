package com.example.shop.scala.models.errors

import java.util.UUID

import scala.util.control.NoStackTrace

sealed class LogicError(msg: String) extends NoStackTrace {
  override def getMessage: String = msg
}

case class NoProduct(id: UUID) extends LogicError(s"Product with id ${id.toString} not found")
case class NotEnoughOfProduct(id: UUID, amount: Int) extends LogicError(s"Max number of product with $id to buy is $amount")
case class NoUser(email: String) extends LogicError(s"User with email $email not found")
case class NoItemsToBuy(userId: String) extends LogicError(s"No items in a cart to buy for $userId")
