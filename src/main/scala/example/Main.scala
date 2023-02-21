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
          shopSlot1DataId -> ShopSlotData(shopSlot1DataId, 300, item1DataId, 20),
          shopSlot2DataId -> ShopSlotData(shopSlot2DataId, 5000, item2DataId, 1)
        )
      )
    )

    val initialState = State(
      coins = 1000,
      items = Map.empty,
      shop = Shop(
        slots = Map(
          shopSlot1DataId -> ShopSlot(shopSlot1DataId, 0),
          shopSlot2DataId -> ShopSlot(shopSlot2DataId, 0)
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
