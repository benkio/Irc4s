package com.benkio.irc4s.client

import com.benkio.irc4s.protocol.IRCCommand
import com.benkio.irc4s.protocol.QuitCommand
import com.benkio.irc4s.protocol.JoinChannelCommand
import com.benkio.irc4s.protocol.SendPrivateMessageCommand
import com.comcast.ip4s.Host
import com.comcast.ip4s.Port
import com.comcast.ip4s.SocketAddress
import fs2.Stream
import fs2.io.net.Network
import fs2.io.net.Socket
import cats.effect.Resource
import cats.effect.Async
import org.typelevel.log4cats.Logger
import cats.implicits._

trait IRCClient[F[_]: Async: Logger]:
  def joinChannel(channel: String): F[Unit]
  def sendCommand(command: IRCCommand, message: Option[String]): F[Unit]
  def sendMessageTo(recepient: String, message: String): F[Unit]
  def readMessages(): Stream[F, String]

object IRCClient:

  final case class InvalidPortException(port: Int) extends Throwable(s"[IRCClient] Invalid Port: $port")

  def connect[F[_]: Async: Logger](server: String, port: Int = 6667): Resource[F, IRCClient[F]] = for {
    portValidated <- Resource.eval(
      Port.fromInt(port).fold(Async[F].raiseError(InvalidPortException(port)))(Async[F].pure(_))
    )
    socket <- Network[F].client(SocketAddress(Host.fromString(server).get, portValidated))
    _      <- Resource.eval(Logger[F].debug(s"Socket created at $server : $port"))
    ircClient <- Resource.make(Async[F].pure(new IRCClientImpl[F](socket)))(client =>
      Logger[F].debug(s"quitting from $server : $port") >> client.sendCommand(QuitCommand, Some("Goodbye"))
    )
  } yield ircClient

  private class IRCClientImpl[F[_]: Async: Logger](socket: Socket[F]) extends IRCClient[F]:
    def joinChannel(channel: String): F[Unit] = for {
      joinChannelCommand <- Async[F].fromEither(JoinChannelCommand(channel))
      _                  <- sendCommand(joinChannelCommand, None)
    } yield ()
    def sendCommand(command: IRCCommand, message: Option[String]): F[Unit] = ???
    def sendMessageTo(recepient: String, message: String): F[Unit] =
      sendCommand(SendPrivateMessageCommand(recepient), Some(message))
    def readMessages(): Stream[F, String] = ???
