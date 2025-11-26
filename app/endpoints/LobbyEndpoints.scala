package endpoints
/*
import sttp.tapir._
import sttp.tapir.json.play._
import models._
import sttp.capabilities.akka.AkkaStreams
import sttp.tapir.CodecFormat

case class CreateLobbyResponse(lobbyId: String)

object LobbyEndpoints {

  val createLobby: Endpoint[Unit, Unit, CreateLobbyResponse, Any] =
    endpoint.post
      .in("lobby")
      .out(jsonBody[CreateLobbyResponse])

  val wsLobby: Endpoint[String, Unit, akka.stream.scaladsl.Flow[ClientMessage, ServerMessage, _], AkkaStreams] =
    endpoint.get
      .in("lobby" / path[String]("lobbyId") / "ws")
      .out(webSocketBody[ClientMessage, CodecFormat.Json, ServerMessage, CodecFormat.Json](AkkaStreams))
}
*/