val ScalaVer = "2.12.2"

val Cats       = "0.9.0"
val Shapeless  = "2.3.2"
val Simulacrum = "0.10.0"
val Paradise   = "2.1.0"

lazy val commonSettings = Seq(
  name    := "shapeless-presentation-src"
, version := "0.1.0"
, scalaVersion := ScalaVer
, libraryDependencies ++= Seq(
    "org.typelevel"        %% "cats"       % Cats
  , "com.chuusai"          %% "shapeless"  % Shapeless
  , "com.github.mpilquist" %% "simulacrum" % Simulacrum
  )
, addCompilerPlugin("org.scalamacros" % "paradise" % Paradise cross CrossVersion.full)
, scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:experimental.macros",
      "-unchecked",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Xfuture",
      "-Ypartial-unification")
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    initialCommands := "import shapelesspresentationsrc._"
  )
