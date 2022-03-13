package com.benkio.irc4s.protocol

sealed trait IRCCommand {
  def toCommandString: String
}
case object QuitCommand extends IRCCommand {
  def toCommandString: String = "QUIT"
}
final case class SendPrivateMessageCommand(recipient: Recepient, message: String) extends IRCCommand {
  def toCommandString: String = s"PRIVMSG $recipient :$message"
}
final case class JoinChannelCommand(channel: Channel) extends IRCCommand {
  def toCommandString: String = s"JOIN $channel"
}
