package com.example.shop.scala.controllers
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.shop.scala.models.errors.{NoItemsToBuy, NoProduct, NoUser, NotEnoughOfProduct}
import com.example.shop.scala.repositories.{ProductRepo, UserRepo}
import akka.http.scaladsl.model.StatusCodes._

trait Controllers {

  def userRepo: UserRepo = new UserRepo()
  def productRepo: ProductRepo = new ProductRepo()

  val userController = new UserController(userRepo)
  val productController = new ProductController(productRepo)

  val exceptionHandler = ExceptionHandler {
    case err @ (NoProduct(_) | NotEnoughOfProduct (_, _) | NoUser(_) | NoItemsToBuy(_)) => complete(BadRequest, err.getMessage)
    case _ => complete(InternalServerError)
  }

  val routes: Route = handleExceptions(exceptionHandler)(userController.routes ~ productController.routes)

}
