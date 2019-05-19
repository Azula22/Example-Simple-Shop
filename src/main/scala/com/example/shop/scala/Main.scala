package com.example.shop.scala

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.example.shop.scala.controllers.{Controllers, ProductController, UserController}
import com.example.shop.scala.repositories.{ProductRepo, UserRepo}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

object Main extends Controllers {

  //host port
  def main(args: Array[String]): Unit = {

    val host = Try(args(0)).getOrElse("localhost")
    val port = Try(args(1).toInt).getOrElse(8080)

    implicit val as: ActorSystem = ActorSystem()
    implicit val mat: ActorMaterializer = ActorMaterializer()

    Http(as).bindAndHandle(routes, host, port)

  }

}
