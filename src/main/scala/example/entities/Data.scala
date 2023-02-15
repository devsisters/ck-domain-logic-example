package example.entities

final case class Data(
  items: Map[ItemDataId, ItemData],
  shop: ShopData
)

final case class ItemData(
  dataId: ItemDataId,
  name: String
)

final case class ShopData(
  slots: Map[ShopSlotDataId, ShopSlotData]
)

final case class ShopSlotData(
  dataId: ShopSlotDataId,
  coinPrice: Int,
  itemDataId: ItemDataId,
  maxItemAmount: Int
)
