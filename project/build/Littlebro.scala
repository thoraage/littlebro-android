import de.element34.sbteclipsify.Eclipsify
import sbt._

class Littlebro(info: ProjectInfo) extends AndroidProject(info) with TypedResources with Eclipsify {
  override def androidPlatformName = "android-1.5"
}
