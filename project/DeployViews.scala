import sbt._

import Keys._
import java.io.File

object DeployViews extends sbt.Plugin {
  import DeployViewsKeys._

  object DeployViewsKeys {
    val deployView = TaskKey[Unit]("deploy-views", "Copy views into deploy.")
  }

  private def DeployViewsTask = (streams, sourceDirectory in deployView, resourceManaged in deployView) map {
    (out, source, _) =>
      val destinationRoot = source.getParentFile.getName match {
        case "modules" => source.getParentFile.getParentFile
        case _ => source
      }
      val destination = new File(destinationRoot, "deploy")
      out.log.info("Deploying from " + source.getAbsolutePath  + "--->  " + destination.getAbsolutePath)
      IO.copyDirectory(new File(source, "conf"), new File(destination, "conf"), overwrite=true)
      IO.copyDirectory(new File(source, "public"), new File(destination, "public"), overwrite=true)
  }

  def deployViewsSettingsIn(c: Configuration): Seq[Setting[_]] =
    inConfig(c)(Seq(
      sourceDirectory in deployView <<= (baseDirectory in c) ,
      resourceManaged in deployView <<= (baseDirectory in c) { _ / "deploy" },
      deployView <<= DeployViewsTask
    )) ++ Seq(
      compile in c <<= (compile in c).dependsOn(deployView in c)
    )

  def deployViewsSettings: Seq[Setting[_]] = 
    deployViewsSettingsIn(Compile)
}

