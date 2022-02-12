package com.benkio.irc4s.protocol

sealed trait IRCCommand
case object QuitCommand                                extends IRCCommand
final case class SendPrivateMessage(recipient: String) extends IRCCommand
final case class JoinChannelCommand(channel: String)   extends IRCCommand
object JoinChannelCommand {

  final case class InvalidChannelName(channel: String) extends Throwable(s"Invalid Channel Name: $channel")

  def apply(channel: String): Either[Throwable, JoinChannelCommand] =
    Either.cond(
      List("#", "&", "!", "+").exists(prefix => channel.startsWith(prefix)),
      new JoinChannelCommand(channel),
      InvalidChannelName(channel)
    )
}
