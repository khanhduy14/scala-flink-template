ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "$scalaversion$"

organization := "$organization$"
organizationName := "$organization$"

scalaVersion := "$scalaversion$"

val flinkVersion = "$flinkversion$"
val log4jCoreVersion = "2.17.2"
val log4sVersion = "1.10.0"

lazy val flinkDependencies: Seq[ModuleID] = Seq(
  "org.apache.flink" % "flink-streaming-java" % flinkVersion,
  "org.apache.flink" % "flink-connector-kinesis" % flinkVersion,
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,
  "org.apache.flink" % "flink-connector-kafka" % flinkVersion,
  "org.apache.flink" % "flink-avro" % flinkVersion,
  "org.apache.flink" % "flink-parquet" % flinkVersion,
  "org.apache.flink" % "flink-test-utils" % flinkVersion excludeAll ((ExclusionRule(organization = "log4j", name = "log4j")))
)

lazy val loggingDependencies: Seq[ModuleID] = Seq(
  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
  "org.apache.logging.log4j" % "log4j-core" % log4jCoreVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jCoreVersion,
  "org.apache.logging.log4j" % "log4j-1.2-api" % log4jCoreVersion,
  "org.apache.logging.log4j" % "log4j-jcl" % log4jCoreVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jCoreVersion,
  "org.apache.logging.log4j" % "log4j-jul" % log4jCoreVersion,
  "org.log4s" %% "log4s" % log4sVersion
)

libraryDependencies += flinkDependencies ++ loggingDependencies

assembly / assemblyMergeStrategy := {
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case PathList(ps @ _*)
    if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList("META-INF", xs @ _*) =>
    (xs map { _.toLowerCase }) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) |
           ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs)
        if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: xs =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.discard
    }
  case _ => MergeStrategy.first
}

assembly / artifact := {
  val art = (assembly / artifact).value
  art.withClassifier(Some("assembly"))
}
addArtifact(assembly / artifact, assembly)