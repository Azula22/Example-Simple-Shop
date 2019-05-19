package com.example.shop.scala.models.product

import java.util.UUID

case class SProduct(id: UUID, price: Double, description: String, amount: Int) {
  def isAvailable: Boolean = amount > 0
  def increment(amount: Int = 1) = copy(amount = this.amount + amount)
}
