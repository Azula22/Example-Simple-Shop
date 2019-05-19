import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.example.shop.scala.controllers.UserController
import com.example.shop.scala.json.{NewUserFormats, UserFormats}
import com.example.shop.scala.models.user.{NewUser, User}
import com.example.shop.scala.repositories.UserRepo
import org.scalatest.{FunSpecLike, Matchers}

class UserControllerSPec
  extends FunSpecLike
  with Matchers
  with ScalatestRouteTest
  with NewUserFormats
  with UserFormats {

  describe("User controller") {

    val userController = new UserController(new UserRepo())
    val email = "email"
    val bankAcc = "ba"
    val newUser = NewUser(email, bankAcc)

    it("should create user") {

      Post("/user/", newUser) ~> userController.routes ~> check {
        response.status shouldBe OK
        val body = responseAs[User]
        body.email shouldBe email
        body.bankAccount shouldBe bankAcc
      }

      Get(s"/user/$email") ~> userController.routes ~> check {
        response.status shouldBe OK
        val body = responseAs[User]
        body.email shouldBe email
        body.bankAccount shouldBe bankAcc
      }

    }

    it("should return error when requesting account that does not exists") {
      Get(s"/user/bla") ~> userController.routes ~> check {
        response.status shouldBe InternalServerError
      }
    }

  }

}
