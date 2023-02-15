package example.entities

final case class State(
  coins: Int,
  items: Map[ItemDataId, Int],
  shop: Shop
)

final case class Shop(
  slots: Map[ShopSlotDataId, ShopSlot]
)

final case class ShopSlot(
  dataId: ShopSlotDataId,
  purchasedItemAmount: Int
)
