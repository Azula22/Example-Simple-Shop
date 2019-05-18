package com.example.shop.scala.repositories

import java.util.UUID

import com.example.shop.scala.models.errors.NoProduct
import com.example.shop.scala.models.product.SProduct

import scala.collection.mutable
import scala.concurrent.Future
import scala.util.Try

class ProductRepo {

  private var _products: mutable.Map[UUID, SProduct] = mutable.Map.empty //emulation of db

  def create(p: SProduct): Future[SProduct] = {
    _products.+=(p.id -> p)
    Future.successful(p)
  }

  def get(limit: Int, offset: Int, onlyAvailable: Boolean = true): List[SProduct] = {
    val products = if (onlyAvailable) _products.values.filter(_.isAvailable) else _products.values
    Try(products.sliding(limit, limit).toList(offset).toList).getOrElse(Nil)
  }

  def increment(id: UUID, amount: Int = 1): Future[SProduct] = {
    _products.get(id) match {
      case Some(el) =>
        val updated = el.copy(amount = el.amount + amount)
        _products.update(id, updated)
        Future.successful(updated)
      case None => Future.failed(NoProduct(id))
    }
  }

}
