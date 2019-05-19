package com.example.shop.scala.actors

import java.util.UUID

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props, Timers}
import com.example.shop.scala.actors.PurchaseActor._
import com.example.shop.scala.models.product.SProduct
import com.example.shop.scala.repositories.ProductRepo
import scala.concurrent.duration._

import scala.collection.mutable

class PurchaseActor(productRepository: ProductRepo) extends Actor with Timers {

  import context.dispatcher

  timers.startSingleTimer(PoisonPill, PoisonPill, 1.hour)

  private val _products: mutable.Map[UUID, SProduct] = mutable.Map()

  def receive: Receive = {

    case Add(id, amount) =>
      val mayBeError = for {
        p <- productRepository.get(id)
        _ <- productRepository.take(id, amount)
      } yield self ! Put(p.copy(amount = amount))
      mayBeError.recover{ case err => println(err.getMessage)}

    case Put(p) if _products.get(p.id).isDefined =>
      _products.update(p.id, _products(p.id).increment(p.amount))

    case Put(p) =>
      _products.update(p.id, p)

    case Increment(id, amount) if _products.get(id).isDefined =>
      productRepository.take(id, amount).map { _ =>
        val pToAdd = _products(id).copy(amount = amount)
        self ! Put(pToAdd)
      }.recover { case err => println(err.getMessage) }

    case Increment(id, amount) => self ! Add(id, amount)

    case Delete(id) =>
      _products - id

    case GetFinalList => sender() ! _products.values

    case Buy =>
      val purchaseId = UUID.randomUUID()
      // should save in purchase repo here
      sender() ! purchaseId
      self ! PoisonPill

  }

  override def postStop(): Unit = {
    super.postStop()
    println("Shopping stopped. Starting cleaning the chart")
    _products.foreach { case (key, product) =>
      productRepository.take(key, product.amount * -1)
    }
  }

}

object PurchaseActor {
  def create(name: String, productRepo: ProductRepo)(implicit as: ActorSystem): ActorRef =
    as.actorOf(Props(classOf[PurchaseActor], productRepo), name)

  case class Add(id: UUID, amount: Int = 1)
  private case class Put(p: SProduct)
  case class Increment(id: UUID, amount: Int)
  case class Delete(id: UUID)
  case class GetFinalList()
  case object Buy

}
