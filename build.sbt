ThisBuild / scalaVersion := "2.13.10"

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.6",
      "dev.zio" %% "zio-prelude" % "1.0.0-RC16"
    )
  )
