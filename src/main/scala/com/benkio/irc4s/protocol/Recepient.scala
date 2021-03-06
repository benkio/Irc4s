package com.benkio.irc4s.protocol

import cats.data.ValidatedNel
import cats.data.Validated
import cats.implicits._

sealed trait Recepient
final case class Nickname(value: String) extends Recepient
object Nickname {
  // https://datatracker.ietf.org/doc/html/rfc2812#section-2.3.1

  // Idea:
  // - Create a validated for the length
  // - Create a validated for the first char
  // - Create a validated for the other chars
  // - When validate, check the length then zip the string with a list
  //   of validated per char. validate the single chars one by one and
  //   fold the list somehow.

  def apply(value: String): ValidatedNel[Throwable, Nickname] = ???
}

final case class Channel private[protocol] (value: String) extends Recepient
object Channel {

  private val maxChannelLength: Int                = 50
  private val invalidChannelCharacters: List[Char] = List(' ', ',', ':', 7.toChar)
  private val validChannelPrefix: List[String]     = List("#", "&", "!", "+")

  final case class InvalidChannelPrefix(value: String) extends Throwable(s"Invalid channel prefix: $value")
  final case class InvalidChannelLength(value: String)
      extends Throwable(s"Invalid channel length (${value.length}): $value")
  final case class InvalidChannelName(value: String)
      extends Throwable(s"Invalid channel name (contains $invalidChannelCharacters): $value")

  private def validChannelPrefix(value: String): ValidatedNel[Throwable, String] =
    Validated.condNel(
      validChannelPrefix.exists(prefix => value.startsWith(prefix)),
      value,
      InvalidChannelPrefix(value)
    )
  private def validChannelLength(value: String): ValidatedNel[Throwable, String] =
    Validated.condNel(
      value.length <= maxChannelLength,
      value,
      InvalidChannelLength(value)
    )
  private def validChannelName(value: String): ValidatedNel[Throwable, String] =
    Validated.condNel(
      invalidChannelCharacters.intersect(value.tail).isEmpty,
      value,
      InvalidChannelName(value)
    )

  def apply(value: String): ValidatedNel[Throwable, Channel] =
    (validChannelPrefix(value) *> validChannelLength(value) *> validChannelName(value)).map((v: String) =>
      new Channel(v)
    )
}
