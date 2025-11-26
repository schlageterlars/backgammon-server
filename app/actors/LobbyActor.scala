package actors

import org.apache.pekko.actor._
import models._
import scala.collection.mutable
import de.htwg.se.backgammon.controller.IController

class LobbyActor(lobbyId: String, options: LobbyOptions) extends Actor {

  private val users = mutable.Map.empty[String, ActorRef]

  def receive: Receive = {
    case Join(user, ref) =>
      if (users.size >= 2) {
        ref ! ServerMessage("Lobby is full. Cannot join.")
        context.stop(ref)
      } else {
        users += user -> ref
        println(s"$user joined lobby $lobbyId / ${users.size} players")
        broadcast(s"$user joined the lobby")
      }
    case BroadcastMessage(user, text) =>
      broadcast(s"$user: $text")

    case Leave(user) =>
      users -= user
      broadcast(s"$user left the lobby")
  }
  private def broadcast(msg: String): Unit = {
    println(s"send broadcast to ${users}")
    users.values.foreach(_ ! ServerMessage(msg))
  }
}
