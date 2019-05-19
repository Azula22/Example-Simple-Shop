package com.example.shop.scala.models.product

import java.util.UUID

case class NewProduct(price: Double, description: String, amount: Int) {
  def complete = SProduct(UUID.randomUUID(), price, description, amount)
}
