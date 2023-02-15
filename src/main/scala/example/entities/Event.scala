package example.entities

sealed trait Event
object Event {
  final case class CoinPaid(amount: Int)                             extends Event
  final case class ItemRewarded(itemDataId: ItemDataId)              extends Event
  final case class ShopSlotPurchased(shopSlotDataId: ShopSlotDataId) extends Event
}
