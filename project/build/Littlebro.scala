import sbt._

trait Defaults {
  def androidPlatformName = "android-1.5"
}
class Littlebro(info: ProjectInfo) extends ParentProject(info) {
  override def shouldCheckOutputDirectories = false

  override def updateAction = task {None}

  lazy val main = project(".", "littlebro", new MainProject(_))

  class MainProject(info: ProjectInfo) extends AndroidProject(info) with TypedResources with Defaults

}
