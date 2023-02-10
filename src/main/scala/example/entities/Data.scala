package example.entities

// TODO: Use Map instead of List?

final case class Data(
  items: List[ItemData],
  shop: ShopData
)

final case class ItemData(
  dataId: ItemDataId,
  name: String
)

final case class ShopData(
  slots: List[ShopSlotData]
)

final case class ShopSlotData(
  dataId: ShopSlotDataId,
  coinPrice: Int,
  itemDataId: ItemDataId,
  maxItemAmount: Int
)
