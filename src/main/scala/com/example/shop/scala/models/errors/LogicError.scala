package com.example.shop.scala.models.errors

import java.util.UUID

sealed trait LogicError extends Exception {
  def msg: String
  override def setStackTrace(stackTraceElements: Array[StackTraceElement]): Unit = {} //for faster work
  override def getMessage: String = msg
}

case class NoProduct(id: UUID) extends LogicError { def msg = s"Product with id ${id.toString} not found"}
