package example

import example.entities._
import example.logic.Programs._
import zio.ZEnvironment

object Main {
  def main(args: Array[String]): Unit = {
    val item1DataId     = DataId("item1")
    val item2DataId     = DataId("item2")
    val shopSlot1DataId = DataId("shopSlot1")
    val shopSlot2DataId = DataId("shopSlot2")

    val data = Data(
      items = Map(
        item1DataId -> ItemData(item1DataId, "Item 1"),
        item2DataId -> ItemData(item2DataId, "Item 2")
      ),
      shop = ShopData(
        slots = Map(
          shopSlot1DataId -> ShopSlotData(
            dataId = shopSlot1DataId,
            coinPrice = 300,
            itemDataId = item1DataId,
            maxItemAmount = 20
          ),
          shopSlot2DataId -> ShopSlotData(
            dataId = shopSlot2DataId,
            coinPrice = 5000,
            itemDataId = item2DataId,
            maxItemAmount = 1
          )
        )
      )
    )

    val initialState = State(
      coins = 1000,
      items = Map.empty,
      shop = Shop(
        slots = Map(
          shopSlot1DataId -> ShopSlot(
            dataId = shopSlot1DataId,
            purchasedItemAmount = 0
          ),
          shopSlot2DataId -> ShopSlot(
            dataId = shopSlot2DataId,
            purchasedItemAmount = 0
          )
        )
      )
    )

    val scenario =
      for {
        () <- purchaseShopSlot(shopSlot1DataId)
        () <- purchaseShopSlot(shopSlot2DataId)
      } yield ()

    val (events, result) =
      scenario.provideEnvironment(ZEnvironment(data)).runAll(initialState)

    println(events)
    println(result)
  }
}
