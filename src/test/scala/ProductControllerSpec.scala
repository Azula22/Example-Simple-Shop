import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.example.shop.scala.controllers.Controllers
import com.example.shop.scala.json._
import com.example.shop.scala.models.product.{NewProduct, SProduct}
import com.example.shop.scala.models.requests.{FinishPurchase, PutItem}
import com.example.shop.scala.models.user.{NewUser, User}
import org.scalatest.{FunSpecLike, Matchers}

class ProductControllerSpec extends Controllers
  with FunSpecLike
  with Matchers
  with ScalatestRouteTest
  with NewProductFormats
  with SProductFormats
  with PutItemFormats
  with NewUserFormats
  with UserFormats
  with FinishPurchaseFormats
  with UUIDFormatter {

  describe("Product controller") {

    val price = 2
    val description = "descr"
    val amount = 12
    val newProduct = NewProduct(price, description, amount)

    it("should create product") {

      Post("/product/", newProduct) ~> routes ~> check {
        response.status shouldBe OK
        val sProduct = responseAs[SProduct]
        sProduct.price shouldBe newProduct.price
        sProduct.description shouldBe newProduct.description
        sProduct.amount shouldBe newProduct.amount
      }

      Get("/product?limit=10&offset=0") ~> routes ~> check {
        response.status shouldBe OK
        val products = responseAs[List[SProduct]]
        products.length shouldBe 1
        val product = products.head
        product.price shouldBe price
        product.description shouldBe description
        product.amount shouldBe amount
      }
    }

    it ("buy items") {

      var _itemId: UUID = null
      var _userId: UUID = null

      Post("/user/", NewUser("e", "b")) ~> routes ~> check {
        response.status shouldBe OK
        val body = responseAs[User]
        _userId = body.id
      }

      Post("/product/", newProduct) ~> routes ~> check {
        response.status shouldBe OK
        val sProduct = responseAs[SProduct]
        _itemId = sProduct.id
      }

      Put("/product/", PutItem(_userId, _itemId, 10)) ~> routes ~> check {
        response.status shouldBe OK
      }

      Put("/product/", PutItem(_userId, _itemId, 1)) ~> routes ~> check {
        response.status shouldBe OK
      }

      Put("/product/", PutItem(_userId, _itemId, 50)) ~> routes ~> check {
        response.status shouldBe OK //just ignore, it shouldn't happen if client works properly
      }

      Get(s"/product/cart/${_userId}") ~> routes ~> check {
        response.status shouldBe OK

        val products = responseAs[List[SProduct]]

        products.length shouldBe 1

        val product = products.head

        product.price shouldBe price
        product.description shouldBe description
        product.amount shouldBe 11
      }

      Put("/product/buy", FinishPurchase(_userId.toString)) ~> routes ~> check {
        response.status shouldBe OK
        responseAs[Map[String, String]].get("id").isDefined shouldBe true
      }

    }

  }

}
