import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment

object SchemaInferenceExample {
  def main(args: Array[String]): Unit = {
    // Set up the Flink environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tableEnv = StreamTableEnvironment.create(env)

    // Step 1: Define a source table with RAW type
    tableEnv.executeSql(
      """
        |CREATE TEMPORARY TABLE sourceTable (
        |  data STRING
        |) WITH (
        |  'connector' = 'filesystem',
        |  'path' = '/tmp/example',
        |  'format' = 'raw'
        |)
      """.stripMargin
    )

    // Step 2: Read data as RAW and collect a sample
    val rawData = tableEnv.sqlQuery("SELECT * FROM sourceTable LIMIT 1")
    val rawStream = tableEnv.toDataStream(rawData)

    // Collect the first row to infer the schema
    val sampleRow = rawStream.executeAndCollect().next()

    println(sampleRow)
  }
}