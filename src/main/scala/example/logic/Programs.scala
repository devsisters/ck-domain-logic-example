package example.logic

import zio.prelude.fx.ZPure
import example.entities._

object Programs {
  def inquireShopSlotData(shopSlotDataId: ShopSlotDataId): Reader[ShopSlotData] =
    for {
      shopData     <- inquire(_.shop)
      shopSlotData <- extractOption(shopData.slots.find(_.dataId == shopSlotDataId), Error.DataNotFound(shopSlotDataId))
    } yield shopSlotData

  def purchaseShopSlot(shopSlotDataId: ShopSlotDataId, amount: Int): Program[(CoinPayment, ItemReward)] =
    ???
}
