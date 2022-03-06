package com.benkio.irc4s.protocol

import org.scalacheck.Gen

object Generators {

  object Channel {

    val maxChannelLength: Int                  = 50
    val invalidChannelCharacters: List[String] = List(" ", ",", ":", 7.toChar.toString)
    val validChannelPrefix: List[String]       = List("#", "&", "!", "+")

    val channelName: Gen[String] = for {
      prefix <- Gen.oneOf[String](validChannelPrefix)
      channelName <- Gen.alphaStr.filter(v =>
        v.length <= (maxChannelLength - 1) && invalidChannelCharacters.forall(x => !v.contains(x))
      )
    } yield prefix ++ channelName

    val invalidChannelName_tooLong: Gen[String] = for {
      prefix <- Gen.oneOf[String](validChannelPrefix)
      channelName <- Gen.alphaStr.filter(v =>
        v.length > maxChannelLength && invalidChannelCharacters.forall(x => !v.contains(x))
      )
    } yield prefix ++ channelName

    val invalidChannelName_badPrefix: Gen[String] = for {
      channelName <- Gen.alphaStr.filter(v =>
        v.length <= maxChannelLength && invalidChannelCharacters.forall(x => !v.contains(x)) && validChannelPrefix
          .forall(x => !v.startsWith(x))
      )
    } yield channelName

    val invalidChannelName_invalidChar: Gen[String] = for {
      prefix            <- Gen.oneOf[String](validChannelPrefix)
      channelName       <- Gen.alphaStr.filter(v => v.length <= maxChannelLength - 1)
      index             <- Gen.chooseNum(1, (channelName.length - 1).max(1))
      invalidStringChar <- Gen.oneOf[String](invalidChannelCharacters)
    } yield {
      val cn = prefix ++ channelName
      if (index < cn.length)
        cn.updated(index, invalidStringChar(0))
      else cn :+ invalidStringChar(0)
    }

  }

}
