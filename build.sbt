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
    //"org.jsoup" % "jsoup" % "1.11.2" ::
    "org.jsoup" % "jsoup" % "1.7.3" ::
    "commons-io" % "commons-io" % "2.0.1" ::
    "org.apache.httpcomponents" % "httpclient" % "4.1.3" ::
    "org.apache.commons" % "commons-lang3" % "3.5" ::
    "com.fasterxml.jackson.core" % "jackson-core" % "2.6.6" ::
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.6" ::
    "org.json4s" %% "json4s-native" % "3.5.0" ::
    "io.lemonlabs" %% "scala-uri" % "0.5.1" ::
    Nil

