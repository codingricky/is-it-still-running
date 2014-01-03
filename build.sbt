name := "is-it-still-running"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "securesocial" %% "securesocial" % "master-SNAPSHOT"
)

resolvers ++= Seq(
  "typesafe" at "http://repo.typesafe.com/typesafe/repo",
  Resolver.url("typesafe-community", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
  Resolver.url("typesafe-community-snapshots", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
)

play.Project.playJavaSettings
