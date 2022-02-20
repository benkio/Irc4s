package com.benkio.irc4s.protocol

import com.benkio.irc4s.protocol.Channel.InvalidChannelPrefix
import cats.data.Validated.{Valid, Invalid}
import cats.data.NonEmptyList
import munit.ScalaCheckSuite
import org.scalacheck.Prop._

class RecepientSpec extends ScalaCheckSuite {

  import Generators.Channel._

  property("A channel is created successfully when the input is valid") {
    forAll(channelName) { (v: String) =>
      assert(Channel(v) == Valid(new Channel(v)))
    }
  }

  property("A channel is not created if the input has invalid prefix") {
    forAll(invalidChannelName_badPrefix) { (v:String) =>
      assert(Channel(v) == Invalid(NonEmptyList.one(InvalidChannelPrefix(v))))
    }
  }
}
