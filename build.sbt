import sbt.Keys._

name := "goose"

version := "2.1.29"

scalaVersion := "2.11.8"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

scalacOptions ++= Seq("-target:jvm-1.8", "-Ybackend:GenBCode", "-Ydelambdafy:method")

libraryDependencies ++=
  "junit" % "junit" % "4.8.1" % Test ::
    "org.slf4j" % "slf4j-api" % "1.6.1" % Compile ::
    "org.slf4j" % "slf4j-log4j12" % "1.6.1" % Test ::
    "org.jsoup" % "jsoup" % "1.7.3" ::
    "commons-io" % "commons-io" % "2.0.1" ::
    "org.apache.httpcomponents" % "httpclient" % "4.1.3" ::
    "commons-lang" % "commons-lang" % "2.6" ::
    "joda-time" % "joda-time" % "2.9.6" ::
    "com.fasterxml.jackson.core" % "jackson-core" % "2.4.4" ::
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4" ::
    "org.json4s" %% "json4s-native" % "3.5.0" ::
    Nil

