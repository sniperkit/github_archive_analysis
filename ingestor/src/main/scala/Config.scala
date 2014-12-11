package ingestor

import com.typesafe.config.ConfigFactory

sealed trait Environment

case object Development extends Environment {
  override def toString = "development"
}
case object Production extends Environment {
  override def toString = "production"
}
case object Stage extends Environment {
  override def toString = "stage"
}
case object Test extends Environment {
  override def toString = "test"
}

object Config {
  def env: Environment = {
    val environment = scala.io.Source.fromFile("/etc/env").mkString.trim
    environment match {
      case "production" => Production
      case "development" => Development
      case "stage" => Stage
      case "test" => Test
    }
  }
  private lazy val config = ConfigFactory.load().getConfig(env.toString)
  private lazy val githubArchiveConfig = config.getConfig("github-archive")
  lazy val githubArchiveUrl = githubArchiveConfig.getString("url")
  lazy val githubArchiveStartDate = githubArchiveConfig.getString("start_date")

  lazy val hadoopHome = config.getString("hadoop-home")
  lazy val hadoopConfigPaths = List(
    s"$hadoopHome/core-site.xml",
    s"$hadoopHome/hdfs-site.xml",
    s"$hadoopHome/mapred-site.xml"
  )
}