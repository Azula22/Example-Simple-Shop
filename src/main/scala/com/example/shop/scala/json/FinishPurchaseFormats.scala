package com.example.shop.scala.json

import com.example.shop.scala.models.requests.FinishPurchase
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait FinishPurchaseFormats extends DefaultJsonProtocol {

  implicit val finishPurchaseFormats: RootJsonFormat[FinishPurchase] = jsonFormat1(FinishPurchase)

}
