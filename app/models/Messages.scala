
package models

import play.api.libs.json._

sealed trait ClientMessage
case class JoinLobby(user: String) extends ClientMessage
case class ChatMessage(user: String, text: String) extends ClientMessage

object ClientMessage {
  implicit val joinFormat: OFormat[JoinLobby] = Json.format[JoinLobby]
  implicit val chatFormat: OFormat[ChatMessage] = Json.format[ChatMessage]

  implicit val clientMessageFormat: Format[ClientMessage] = new Format[ClientMessage] {
    def writes(msg: ClientMessage): JsValue = msg match {
      case j: JoinLobby    => Json.obj("type" -> "JoinLobby") ++ Json.toJsObject(j)
      case c: ChatMessage  => Json.obj("type" -> "ChatMessage") ++ Json.toJsObject(c)
    }

    def reads(json: JsValue): JsResult[ClientMessage] = (json \ "type").validate[String].flatMap {
      case "JoinLobby" =>
        val withoutType = json.as[JsObject] - "type"
        withoutType.validate[JoinLobby]
      case "ChatMessage" =>
        val withoutType = json.as[JsObject] - "type"
        withoutType.validate[ChatMessage]
      case other =>
        JsError(s"Unknown type: $other")
    }
  }

  def fromJson(js: JsValue): JsResult[ClientMessage] = js.validate[ClientMessage]

}


case class ServerMessage(message: String)

object ServerMessage {
  implicit val format: Format[ServerMessage] = Json.format[ServerMessage]
}