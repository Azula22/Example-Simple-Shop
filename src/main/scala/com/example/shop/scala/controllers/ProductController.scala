package com.example.shop.scala.controllers

import java.util.UUID

import akka.actor.{ActorNotFound, ActorRef, ActorSystem}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import com.example.shop.scala.actors.PurchaseActor
import com.example.shop.scala.json.{NewProductFormats, PutItemFormats, SProductFormats}
import com.example.shop.scala.models.product.{NewProduct, SProduct}
import com.example.shop.scala.models.requests.PutItem
import com.example.shop.scala.repositories.ProductRepo
import akka.pattern._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import akka.http.scaladsl.model.StatusCodes.OK
import com.example.shop.scala.models.errors.NoItemsToBuy

import scala.concurrent.Future

class ProductController(productRepo: ProductRepo) extends Directives
   with NewProductFormats
   with SProductFormats
   with PutItemFormats {

  implicit val timeout: Timeout = Timeout(5.seconds)

  def create: Route = (post & pathEndOrSingleSlash & entity(as[NewProduct])) { newProduct =>
    complete(productRepo.create(newProduct.complete))
  }

  def getPaginatedProducts = (get & pathEndOrSingleSlash & parameter('limit.as[Int], 'offset.as[Int], 'onlyAvailable.?(true))) { (limit, offset, onlyAvailable) =>
    complete(productRepo.get(limit, offset, onlyAvailable))
  }

  def putInCart: Route = (put & pathEndOrSingleSlash & entity(as[PutItem]) & extractActorSystem) { case (PutItem(userId, itemId, amount), as) =>
    val result = purchase(as, userId.toString,
      _ ! PurchaseActor.Add(itemId, amount),
      {
        val newActor = PurchaseActor.create(userId.toString, productRepo)(as)
        newActor ! PurchaseActor.Add(itemId, amount)
      }
    )
    complete(result.map(_ => OK))
  }

  def getFinalCartList: Route = (get & path("cart" / Segment) & extractActorSystem) { (userId, as) =>
    val result = purchase(as, userId,
      onSuccess = actor => (actor ? PurchaseActor.GetFinalList).mapTo[Iterable[SProduct]],
      onNotFound = Future.successful(Iterable.empty[SProduct])
    ).flatten
    complete(result)
  }

  def buy = (put & path("buy" / Segment) & extractActorSystem ) { (userId, as) =>
    val result = purchase(as, userId,
      actor => (actor ? PurchaseActor.Buy).mapTo[UUID],
      Future.failed(NoItemsToBuy(userId))
    ).flatten
    complete(result)
  }

  def routes = pathPrefix("user") {
    create ~ getPaginatedProducts
  }

  private def purchase[T](as: ActorSystem, userId: String, onSuccess: ActorRef => T, onNotFound: => T): Future[T] = {
    as.actorSelection(userId.toString)
      .resolveOne()
      .map(onSuccess)
      .recover{ case ActorNotFound(_) => onNotFound }
  }

}
