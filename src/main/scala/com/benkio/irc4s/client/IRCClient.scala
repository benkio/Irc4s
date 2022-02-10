package com.benkio.irc4s.client

import com.benkio.irc4s.protocol.IRCCommand
import com.benkio.irc4s.protocol.QuitCommand
import com.comcast.ip4s._
import fs2.Stream
import fs2.io.net.Network
import fs2.io.net.Socket
import cats.effect.Resource
import cats.effect.Async
import org.typelevel.log4cats.Logger
import cats.implicits._

trait IRCClient[F[_]: Async: Logger]:
  def joinChannel(channel: String): F[Unit]
  def sendCommand(command: IRCCommand, message: String): F[Unit]
  def sendMessageTo(recepient: String, message: String): F[Unit]
  def readMessages(): Stream[F, String]

object IRCClient:
  def connect[F[_]: Async: Logger](server: String, port: Int = 6667): Resource[F, IRCClient[F]] = for {
    socket <- Network[F].client(SocketAddress(Host.fromString(server).get, port"80"))
    _      <- Resource.eval(Logger[F].debug(s"Socket created at $server : $port"))
    ircClient <- Resource.make(Async[F].pure(new IRCClientImpl[F](socket)))(client =>
      Logger[F].debug(s"quitting from $server : $port") >> client.sendCommand(QuitCommand, "Goodbye")
    )

  } yield ircClient

  private class IRCClientImpl[F[_]: Async: Logger](socket: Socket[F]) extends IRCClient[F]:
    def joinChannel(channel: String): F[Unit]                      = ???
    def sendCommand(command: IRCCommand, message: String): F[Unit] = ???
    def sendMessageTo(recepient: String, message: String): F[Unit] = ???
    def readMessages(): Stream[F, String]                          = ???
