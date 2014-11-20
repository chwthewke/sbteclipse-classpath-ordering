import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtScalariform._
import com.typesafe.sbteclipse.core.EclipsePlugin._
import scalariform.formatter.preferences._
import scoverage.ScoverageSbtPlugin
import sbtbuildinfo.Plugin._

object SbteclipsecporderingBuild extends Build {

  object Dependencies {
    val scalazVersion = "7.1.0"

    val scalaz = "org.scalaz" %% "scalaz-core" % scalazVersion withSources () withJavadoc ()

    val scalatest = "org.scalatest" %% "scalatest" % "2.2.1" % "test" withSources () withJavadoc ()

    val scalacheck = Seq(
      "org.scalacheck" %% "scalacheck" % "1.11.4" % "test" withSources () withJavadoc (),
      "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test" withSources () withJavadoc ()
    )

  }

  lazy val sbteclipsecporderingScalariformSettings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences := defaultPreferences
      .setPreference( AlignSingleLineCaseStatements, true )
      .setPreference( SpaceBeforeColon, true )
      .setPreference( SpaceInsideParentheses, true )
  )

  lazy val sbteclipsecporderingSettings =
    Defaults.coreDefaultSettings ++
      ScoverageSbtPlugin.instrumentSettings ++
      SbtBuildInfo.buildSettings( "net.chwthewke.sbteclipsecpordering" ) ++
      SbtEclipse.buildSettings ++
      sbteclipsecporderingScalariformSettings ++
      Seq(
        organization := "net.chwthewke",
        scalaVersion := "2.11.4",
        libraryDependencies ++= Seq(
          Dependencies.scalatest,
          Dependencies.scalaz ) ++
          Dependencies.scalacheck,
        scalacOptions ++= Seq( "-feature", "-deprecation" ),
        unmanagedSourceDirectories in Compile := ( scalaSource in Compile ).value :: Nil,
        unmanagedSourceDirectories in Test := ( scalaSource in Test ).value :: Nil
      )

  lazy val sbteclipsecpordering = Project(
    id = "sbteclipse-cp-ordering",
    base = file( "." ),
    settings = Defaults.coreDefaultSettings ++
      Seq( name := "sbteclipse-cp-ordering" )
  ).aggregate(
      sbteclipsecporderingcore,
      sbteclipsecporderingmain
    )

  lazy val sbteclipsecporderingcore = Project(
    id = "sbteclipse-cp-ordering-core",
    base = file( "sbteclipse-cp-ordering-core" ),
    settings = sbteclipsecporderingSettings ++
      Seq(
        name := "sbteclipse-cp-ordering-core",
        initialCommands := "import net.chwthewke.sbteclipsecpordering",
        buildInfoObject := "sbteclipsecporderingcoreBuildInfo"
      )
  )

  lazy val sbteclipsecporderingmain = Project(
    id = "sbteclipse-cp-ordering-main",
    base = file( "sbteclipse-cp-ordering-main" ),
    settings = sbteclipsecporderingSettings ++
      Seq(
        name := "sbteclipse-cp-ordering-main",
        mainClass := Some( "net.chwthewke.sbteclipsecpordering.Main" ),
        initialCommands := "import net.chwthewke.sbteclipsecpordering",
        buildInfoObject := "sbteclipsecporderingmainBuildInfo"
      )
  ).dependsOn( sbteclipsecporderingcore )
}
