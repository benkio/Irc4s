package com.benkio.irc4s.protocol

sealed trait IRCCommand
case object QuitCommand extends IRCCommand
