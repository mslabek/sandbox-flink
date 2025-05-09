import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment

import scala.jdk.CollectionConverters._
import scala.util.Try

object PostgresCatalog {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.createLocalEnvironment()
    val tConfig = EnvironmentSettings.newInstance()
      .withConfiguration(Configuration.fromMap(env.getConfiguration.toMap))
      .withClassLoader(getClass.getClassLoader)
      .build()
    val tableEnv = StreamTableEnvironment.create(env, tConfig)

    val result = Try {
      tableEnv.executeSql(
        s"""|CREATE CATALOG my_catalog WITH (
            | 'type' = 'jdbc',
            | 'default-database' = 'example',
            | 'username' = 'admin',
            | 'password' = 'admin',
            | 'base-url' = 'jdbc:postgresql://localhost:15432'
            |)""".stripMargin
      )
    }

    val rawData = tableEnv.sqlQuery("SELECT * FROM my_catalog.example.`public.products` LIMIT 1")
    val rawStream = tableEnv.toDataStream(rawData).executeAndCollect().asScala.toList

    println(rawStream)

  }
}
