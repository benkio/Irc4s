package com.benkio.irc4s.client

trait IRCClient:
  def connect(server: String, port: Int = 6667)
  def disconnect()
  def joinChannel(channel: String)
  def sendCommand(command: String, message: String)
  def sendMessageTo(recepient: String, message: String)

