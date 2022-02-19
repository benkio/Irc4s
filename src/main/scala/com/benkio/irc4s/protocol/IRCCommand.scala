//package com.benkio.irc4s.protocol

// sealed trait IRCCommand {
//   def toCommandString: String
// }
// case object QuitCommand extends IRCCommand {
//   def toCommandString: String = "QUIT"
// }
// final case class SendPrivateMessageCommand(recipient: String, message: String) extends IRCCommand {
//   def toCommandString: String = s"PRIVMSG $recipient :$message"
// }
// final case class JoinChannelCommand(channel: String) extends IRCCommand {
//   def toCommandString: String = s"JOIN $channel"
// }
// object JoinChannelCommand {

//   final case class InvalidChannelName(channel: String) extends Throwable(s"Invalid Channel Name: $channel")

//   def apply(channel: String): Either[Throwable, JoinChannelCommand] =
//     Either.cond(
//       List("#", "&", "!", "+").exists(prefix => channel.startsWith(prefix)),
//       new JoinChannelCommand(channel),
//       InvalidChannelName(channel)
//     )
// }
