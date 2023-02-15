package example.entities

trait Error
object Error {
  final case class DataNotFound[A](dataId: DataId[A])              extends Error
  final case class EntityNotFound[A](dataId: DataId[A])            extends Error
  final case object NotEnoughCoins                                 extends Error
  final case class ShopSlotSoldOut(shopSlotDataId: ShopSlotDataId) extends Error
}
