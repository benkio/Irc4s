import Settings._

ThisBuild / organization := "com.benkio"
ThisBuild / scalaVersion := "3.0.0"

Global / onChangedBuildSource                              := ReloadOnSourceChanges
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"
//ThisBuild / scalacOptions += "-P:semanticdb:synthetics:on"

addCommandAlias("fix", ";scalafixAll; scalafmtAll; scalafmtSbt")

lazy val root = (project in file(".")).settings(
  irc4sSettings
)
