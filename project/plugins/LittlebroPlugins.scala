import sbt._

class LittlebroPlugins(info: ProjectInfo) extends PluginDefinition(info) {
  lazy val android = "org.scala-tools.sbt" % "sbt-android-plugin" % "0.5.0"
  lazy val eclipse = "de.element34" % "sbt-eclipsify" % "0.6.0"
}