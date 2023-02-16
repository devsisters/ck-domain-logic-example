package example.logic

import example.entities._
import example.entities.Event._
import zio.prelude.fx.ZPure

object Programs {
  def inquireShopSlotData(
    shopSlotDataId: ShopSlotDataId
  ): ZPure[Nothing, State, State, Data, Error, ShopSlotData] =
    for {
      data         <- ZPure.environmentWith[Data](_.get)
      shopSlotData <- extractOption(
                        data.shop.slots.get(shopSlotDataId),
                        Error.DataNotFound(shopSlotDataId)
                      )
    } yield shopSlotData

  lazy val getCoins: ZPure[Nothing, State, State, Data, Error, Int] =
    for {
      state <- ZPure.get
    } yield state.coins

  def getShopSlot(
    shopSlotDataId: ShopSlotDataId
  ): ZPure[Nothing, State, State, Data, Error, ShopSlot] =
    for {
      state    <- ZPure.get
      shopSlot <- extractOption(
                    state.shop.slots.get(shopSlotDataId),
                    Error.EntityNotFound(shopSlotDataId)
                  )
    } yield shopSlot

  def purchaseShopSlot(shopSlotDataId: ShopSlotDataId): Program[Unit] =
    for {
      shopSlotData <- inquireShopSlotData(shopSlotDataId)
      shopSlot     <- getShopSlot(shopSlotDataId)
      ()           <- assertThat(
                        shopSlot.purchasedItemAmount < shopSlotData.maxItemAmount,
                        Error.ShopSlotSoldOut(shopSlotDataId)
                      )
      coins        <- getCoins
      ()           <- assertThat(coins >= shopSlotData.coinPrice, Error.NotEnoughCoins)
      ()           <- liftEvent(CoinPaid(shopSlotData.coinPrice))
      ()           <- liftEvent(ItemRewarded(shopSlotData.itemDataId))
      ()           <- liftEvent(ShopSlotPurchased(shopSlotDataId))
    } yield ()
}
