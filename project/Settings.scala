import sbt._

import Keys._
import scalafix.sbt.ScalafixPlugin.autoImport._

object Settings {

  lazy val irc4sSettings = Seq(
    name              := "irc4s",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    libraryDependencies ++= Dependencies.irc4sDependencies
  )
}

object Dependencies {

  object Versions {
    val catsEffect      = "3.3.5"
    val comcast         = "3.1.2"
    val munitCatsEffect = "1.0.7"
    val fs2             = "3.2.4"
    val log4cats        = "2.2.0"
  }

  object Libs {
    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    val catsEffectKernel = "org.typelevel" %% "cats-effect-kernel" % Versions.catsEffect
    // standard "effect" library (Queues, Console, Random etc.)
    val catsEffectStd   = "org.typelevel" %% "cats-effect-std"     % Versions.catsEffect
    val comcast         = "com.comcast"   %% "ip4s-core"           % Versions.comcast
    val munitCatsEffect = "org.typelevel" %% "munit-cats-effect-3" % Versions.munitCatsEffect % Test
    val fs2Core         = "co.fs2"        %% "fs2-core"            % Versions.fs2
    val fs2IO           = "co.fs2"        %% "fs2-io"              % Versions.fs2
    val log4cats        = "org.typelevel" %% "log4cats-slf4j"      % Versions.log4cats
  }

  lazy val irc4sDependencies = Seq(
    Libs.catsEffect,
    Libs.catsEffectKernel,
    Libs.catsEffectStd,
    Libs.comcast,
    Libs.fs2Core,
    Libs.fs2IO,
    Libs.log4cats,
    Libs.munitCatsEffect
  )
}
