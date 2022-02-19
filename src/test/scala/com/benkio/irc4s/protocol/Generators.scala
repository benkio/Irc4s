package com.benkio.irc4s.protocol

import org.scalacheck.Gen

object Generators {

  val channelName: Gen[String] = for {
    prefix <- Gen.oneOf[Char](List('#', '&', '!', '+'))
    channelName <- Gen.alphaStr.filter(v => v.length <= 50 && List(' ', ',', ':', 7.toChar).intersect(v).isEmpty)
  } yield prefix +: channelName

}
