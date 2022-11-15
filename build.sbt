import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "server-client-test",
    libraryDependencies += scalaTest % Test
  )

lazy val `remoteClientScala3` = project
  .in(file("module-scala3"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    run / fork := true,
    name := "module-scala3",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= {
      val vScallop = "4.1.0"
      Seq(
        "org.rogach" %% "scallop" % vScallop,
        "com.typesafe" % "config" % "1.4.2",
        "org.scalameta" %% "munit" % "0.7.29" % Test
      )
    }
  )
lazy val stageAll = taskKey[Unit]("Stage all projects")

val projects_stage = ScopeFilter(inProjects(root, `remoteClientScala3`), inConfigurations(Universal))

stageAll := {
  stage.all(projects_stage).value
}

// Uncomment the following for publishing to Sonatype.
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for more detail.

// ThisBuild / description := "Some descripiton about your project."
// ThisBuild / licenses    := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
// ThisBuild / homepage    := Some(url("https://github.com/example/project"))
// ThisBuild / scmInfo := Some(
//   ScmInfo(
//     url("https://github.com/your-account/your-project"),
//     "scm:git@github.com:your-account/your-project.git"
//   )
// )
// ThisBuild / developers := List(
//   Developer(
//     id    = "Your identifier",
//     name  = "Your Name",
//     email = "your@email",
//     url   = url("http://your.url")
//   )
// )
// ThisBuild / pomIncludeRepository := { _ => false }
// ThisBuild / publishTo := {
//   val nexus = "https://oss.sonatype.org/"
//   if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
//   else Some("releases" at nexus + "service/local/staging/deploy/maven2")
// }
// ThisBuild / publishMavenStyle := true
