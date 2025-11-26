package models

import play.api.libs.json._

case class LobbyOptions(boardSize: BoardSize = BoardSize.Classic)

object LobbyOptions {
  implicit val format: OFormat[LobbyOptions] = Json.format[LobbyOptions]
}

sealed trait BoardSize
object BoardSize {
  case object Small extends BoardSize
  case object Medium extends BoardSize
  case object Classic extends BoardSize

  // Play JSON format
  implicit val format: Format[BoardSize] = new Format[BoardSize] {
    def writes(size: BoardSize): JsValue = JsString(size.toString.toLowerCase)
    def reads(json: JsValue): JsResult[BoardSize] = json match {
      case JsString("small")   => JsSuccess(Small)
      case JsString("medium")  => JsSuccess(Medium)
      case JsString("classic") => JsSuccess(Classic)
      case other               => JsError(s"Unknown board size: $other")
    }
  }
}