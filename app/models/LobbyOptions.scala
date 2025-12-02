package models

import play.api.libs.json._
import de.htwg.se.backgammon.controller.IController
import de.htwg.se.backgammon.model.base.Game
import de.htwg.se.backgammon.controller.base.Controller
import de.htwg.se.backgammon.model.base.Model
import de.htwg.se.backgammon.model.base.Dice

case class LobbyOptions(
  boardSize: BoardSize = BoardSize.Classic, 
  scope: LobbyScope = LobbyScope.Private,
  colorDesicion: ColorDecision = ColorDecision.White
  ) {
  def apply(): IController = {
    val game: Game = boardSize match {
      case BoardSize.Small    => new Game(12, 10)
      case BoardSize.Medium   => new Game(16, 12)
      case BoardSize.Classic  => new Game(24, 15)
    }
    return Controller(new Model(game, Dice()))
  }
}

object LobbyOptions {
  implicit val format: OFormat[LobbyOptions] = Json.format[LobbyOptions]
}

sealed trait LobbyScope
object LobbyScope {
  case object Private extends LobbyScope
  case object Public extends LobbyScope

  // Play JSON format
  implicit val format: Format[LobbyScope] = new Format[LobbyScope] {
    def writes(scope: LobbyScope): JsValue = JsString(scope.toString.toLowerCase)

    def reads(json: JsValue): JsResult[LobbyScope] = json match {
      case JsString(value) =>
        value.toLowerCase match {
          case "private"  => JsSuccess(Private)
          case "public"  => JsSuccess(Public)
          case _        => JsError(s"Unknown lobby scope: $value")
        }
      case other =>
        JsError(s"Expected a string for ColorDecision, got: $other")
    }

  }
}

sealed trait ColorDecision
object ColorDecision {
  case object White extends ColorDecision
  case object Black extends ColorDecision
  case object Random extends ColorDecision


  // Play JSON format
  implicit val format: Format[ColorDecision] = new Format[ColorDecision] {
    def writes(color: ColorDecision): JsValue = JsString(color.toString.toLowerCase)
    def reads(json: JsValue): JsResult[ColorDecision] = json match {
      case JsString(value) =>
        value.toLowerCase match {
          case "white"  => JsSuccess(White)
          case "black"  => JsSuccess(Black)
          case "random" => JsSuccess(Random)
          case _        => JsError(s"Unknown player decision: $value")
        }
      case other =>
        JsError(s"Expected a string for ColorDecision, got: $other")
    }
  }
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
      case JsString(value) =>
        value.toLowerCase match {
          case "small"  => JsSuccess(Small)
          case "medium"  => JsSuccess(Medium)
          case "classic"  => JsSuccess(Classic)
          case _        => JsError(s"Unknown board size: $value")
        }
      case other =>
        JsError(s"Expected a string for BoardSize, got: $other")
    }
    
  }
}