package com.example.shop.scala

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.example.shop.scala.controllers.{ProductController, UserController}
import com.example.shop.scala.repositories.{ProductRepo, UserRepo}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

object Main {

  //host port
  def main(args: Array[String]): Unit = {

    val host = Try(args(0)).getOrElse("localhost")
    val port = Try(args(1).toInt).getOrElse(8080)

    implicit val as: ActorSystem = ActorSystem()
    implicit val mat: ActorMaterializer = ActorMaterializer()

    val productRepo = new ProductRepo()
    val userRepo = new UserRepo()
    val productController = new ProductController(productRepo)
    val userController = new UserController(userRepo)

    val routeSummary = productController.routes ~ userController.routes

    Http(as).bindAndHandle(routeSummary, host, port)

  }

}
