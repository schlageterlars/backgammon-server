package actors

import org.apache.pekko.actor._
import models._
import play.api.libs.json._
import play.libs.Json

class ClientActor(lobby: ActorRef, user: String, out: ActorRef) extends Actor {

  override def preStart(): Unit = lobby ! Join(user, self)
  override def postStop(): Unit = lobby ! Leave(user)

  def receive: Receive = {
    case msg: ChatMessage =>
      lobby ! BroadcastMessage(user, msg.text)
    case msg: ServerMessage =>
      out ! msg
  }
}

object ClientActor {
  def props(lobby: ActorRef, user: String, out: ActorRef): Props =
    Props(new ClientActor(lobby, user, out))
}

