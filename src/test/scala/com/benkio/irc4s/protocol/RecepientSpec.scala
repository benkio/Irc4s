package com.benkio.irc4s.protocol

import com.benkio.irc4s.protocol.Channel.InvalidChannelPrefix
import com.benkio.irc4s.protocol.Channel.InvalidChannelLength
import com.benkio.irc4s.protocol.Channel.InvalidChannelName
import cats.data.Validated.Valid
import cats.data.Validated.Invalid
import cats.data.NonEmptyList
import munit.ScalaCheckSuite
import org.scalacheck.Prop._

class RecepientSpec extends ScalaCheckSuite {

  // Channel //////////////////////////////////////////////////////////////////

  import Generators.Channel._

  property("A channel is created successfully when the input is valid") {
    forAll(channelName) { (v: String) =>
      assert(Channel(v) == Valid(new Channel(v)))
    }
  }

  property("A channel is not created if the input has invalid prefix") {
    forAll(invalidChannelName_badPrefix) { (v: String) =>
      assert(Channel(v) == Invalid(NonEmptyList.one(InvalidChannelPrefix(v))))
    }
  }

  property("A channel is not created if the input is too long") {
    forAll(invalidChannelName_tooLong) { (v: String) =>
      assert(Channel(v) == Invalid(NonEmptyList.one(InvalidChannelLength(v))))
    }
  }

  property("A channel is not created if the input contains an invalid char") {
    forAll(invalidChannelName_invalidChar) { (v: String) =>
      assert(Channel(v) == Invalid(NonEmptyList.one(InvalidChannelName(v))))
    }
  }

  property("A channel is not created if the input: contains an invalid char or is too long or has a bad prefix") {
    forAll(invalidChannelName) { (v: String) =>
      assert(Channel(v).isInvalid)
    }
  }
}
