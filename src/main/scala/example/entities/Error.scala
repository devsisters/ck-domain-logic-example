package example.entities

trait Error
object Error {
  case class DataNotFound[A](dataId: DataId[A]) extends Error
}
