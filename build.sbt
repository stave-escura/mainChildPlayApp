import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

name := "hellodeploy"

lazy val commonSettings = Seq(
  organization := "org.myorg",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.11.4",
  resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    libraryDependencies ++= Seq(
	  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.2"
	),
  publishTo := {
	   Some(Resolver.file("file",  new File( "deploy/maven/snapshots" )) )
  }
)

lazy val root = (project in file(".")).settings(commonSettings: _*).settings(deployViewsSettings:_*).enablePlugins(PlayScala).
              aggregate(main).dependsOn(main).
              aggregate(child).dependsOn(child)

lazy val main = (project in file("modules/main")).settings(commonSettings: _*).enablePlugins(PlayScala).
              aggregate(child).dependsOn(child)

lazy val child = (project in file("modules/child")).settings(commonSettings: _*). enablePlugins(PlayScala)

EclipseKeys.withSource := true