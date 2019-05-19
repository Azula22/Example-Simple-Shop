package com.example.shop.scala.models.requests

import java.util.UUID

case class PutItem(userId: UUID, itemId: UUID, amount: Int)
