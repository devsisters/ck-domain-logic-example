package example.entities

sealed trait Event
object Event {
  final case class CoinPaid(amount: Int)                                          extends Event
  final case class ItemRewarded(itemDataId: ItemDataId, amount: Int)              extends Event
  final case class ShopSlotPurchased(shopSlotDataId: ShopSlotDataId, amount: Int) extends Event
}
