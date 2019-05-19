package com.example.shop.scala.models.errors

import java.util.UUID

sealed trait LogicError extends Exception {
  def msg: String
  override def setStackTrace(stackTraceElements: Array[StackTraceElement]): Unit = {} //for faster work
  override def getMessage: String = msg
}

case class NoProduct(id: UUID) extends LogicError { def msg = s"Product with id ${id.toString} not found"}
case class NotEnoughOfProduct(id: UUID, amount: Int) extends LogicError {
  def msg = s"Max number of product with $id to buy is $amount"
}
case class NoUser(email: String) extends LogicError { def msg = s"User with email $email not found"}
