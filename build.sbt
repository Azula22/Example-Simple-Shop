name := "spendit-shop"

version := "0.1"

scalaVersion := "2.12.8"

val akkaActors = "2.5.22"
val akkaHttp = "10.1.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"            % akkaHttp,
  "com.typesafe.akka" %% "akka-stream"          % akkaActors,
  "com.typesafe.akka" %% "akka-actor"           % akkaActors,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttp
)