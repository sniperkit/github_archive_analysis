package ingestor.stores

import scala.concurrent.Future

trait Store[T] {
  def insert(path: String, data: T, overwrite: Boolean = true): Future[Unit]
  def exists(path: String): Future[Boolean]
}