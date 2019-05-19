package com.example.shop.scala.repositories

import java.util.UUID

import com.example.shop.scala.models.errors.{NoProduct, NotEnoughOfProduct}
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

  def get(id: UUID, onlyAvailable: Boolean = true): Future[SProduct] = {
    val condition: SProduct => Boolean = { product =>
      product.id == id && (if (onlyAvailable) product.isAvailable else true)
    }
    _products.values
      .find(condition)
      .fold[Future[SProduct]](Future.failed(NoProduct(id)))(Future.successful)
  }

  def get(limit: Int, offset: Int, onlyAvailable: Boolean): Future[List[SProduct]] = {
    val products = if (onlyAvailable) _products.values.filter(_.isAvailable) else _products.values
    Future.successful(
      Try(products.sliding(limit, limit).toList(offset).toList)
        .getOrElse(Nil)
    )
  }

  def take(id: UUID, amount: Int = 1): Future[SProduct] = {
    _products.get(id) match {
      case Some(el) if el.amount >= amount =>
        val updated = el.copy(amount = el.amount - amount)
        _products.update(id, updated)
        Future.successful(updated)
      case Some(el) => Future.failed(NotEnoughOfProduct(el.id, el.amount))
      case None => Future.failed(NoProduct(id))
    }
  }

}
