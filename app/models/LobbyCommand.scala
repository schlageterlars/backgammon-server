package models

import org.apache.pekko.actor.ActorRef

sealed trait LobbyCommand
case class Join(user: String, ref: ActorRef) extends LobbyCommand
case class Leave(user: String) extends LobbyCommand
case class BroadcastMessage(user: String, text: String) extends LobbyCommand
