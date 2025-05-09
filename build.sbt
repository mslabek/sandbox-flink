ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

val flinkV = "1.19.2"
val jdbcFlinkConnectorV = "3.2.0-1.19"
val postgresqlV = "42.7.4"
val calciteV = "1.32.0"

lazy val root = (project in file("."))
  .settings(
    name := "sandbox-flink",
    libraryDependencies ++= Seq(
      "org.apache.flink" % "flink-table-api-java" % flinkV,
      "org.apache.flink" % "flink-table-api-java-bridge" % flinkV,
      "org.apache.flink" % "flink-table-planner-loader" % flinkV,
      "org.apache.flink" % "flink-table-runtime" % flinkV,
      "org.apache.flink" % "flink-clients" % flinkV,
      "org.apache.flink" % "flink-sql-parser" % flinkV,
      "org.apache.flink" % "flink-connector-files" % flinkV,
      "org.apache.flink" % "flink-json" % flinkV,
      "org.apache.flink" % "flink-csv" % flinkV,
      "org.apache.flink" % "flink-connector-jdbc" % jdbcFlinkConnectorV,
      "org.postgresql" % "postgresql" % postgresqlV,
      "org.apache.calcite" % "calcite-linq4j" % calciteV, // required by flink-sql-parser,
      "ch.qos.logback" % "logback-classic" % "1.5.18"
    )
  )
