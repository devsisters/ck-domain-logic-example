package example

package object entities {
  final case class DataId[+A](value: String)
  type ItemDataId = DataId[ItemData]
  type ShopSlotDataId = DataId[ShopSlotData]
}
