package $package__packaged$

import org.apache.flink.streaming.api.scala.StreamExecutionEnviroment

class Application {
  def start(): Unit = {
    val env = StreamExecutionEnviroment.getExecutionEnviroment
    env.executeAsync()
  }
}

object Application {
  def main(args: Array[String]): Unit = {
    val application = new Application()
    application.start()
  }
}
