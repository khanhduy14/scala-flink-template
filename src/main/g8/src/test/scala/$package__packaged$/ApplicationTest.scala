package $package__packaged$

import org.apache.flink.streaming.api.scala.StreamExecutionEnviroment

class ApplicationTest {
  def start(): Unit = {
    val env = StreamExecutionEnviroment.getExecutionEnviroment
    env.executeAsync()
  }
}

object ApplicationTest {
  def main(args: Array[String]): Unit = {
    val application = new ApplicationTest()
    application.start()
  }
}
