name := "spendit-shop"

version := "0.1"

scalaVersion := "2.12.8"

val akkaActors = "2.5.22"
val akkaHttp = "10.1.8"

lazy val akka = Seq(
  "com.typesafe.akka" %% "akka-http"            % akkaHttp,
  "com.typesafe.akka" %% "akka-stream"          % akkaActors,
  "com.typesafe.akka" %% "akka-actor"           % akkaActors,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttp
)

lazy val tests = Seq(
  "org.scalatest"     %% "scalatest"            % "3.0.5",
  "com.typesafe.akka" %% "akka-testkit"         % akkaActors,
  "com.typesafe.akka" %% "akka-stream-testkit"  % akkaActors,
  "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttp
).map(_ % Test)

libraryDependencies ++= akka ++ tests

assemblyJarName in assembly := "app.jar"
test in assembly := {}