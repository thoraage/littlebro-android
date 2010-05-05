import sbt._

trait Defaults {
  def androidPlatformName = "android-1.5"
}
class Littlebro(info: ProjectInfo) extends ParentProject(info) {
  override def shouldCheckOutputDirectories = false
  override def updateAction = task { None } 

  lazy val main  = project(".", "littlebro", new MainProject(_))
  lazy val tests = project("tests",  "tests", new TestProject(_), main)

  class MainProject(info: ProjectInfo) extends AndroidProject(info) with Defaults {    
    val scalatest = "org.scalatest" % "scalatest" % "1.0" % "test"
    val specs = "org.scala-tools.testing" % "specs" % "1.6.2" % "test"
  }

  class TestProject(info: ProjectInfo) extends AndroidTestProject(info) with Defaults
}
