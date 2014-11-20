name := "sbteclipse-cp-ordering-build"

resolvers += Classpaths.sbtPluginReleases

resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "0.99.7.1")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.5.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.3.1")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.5")
