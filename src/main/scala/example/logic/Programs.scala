package example.logic

import example.entities._
import example.entities.Event._
import zio.prelude.fx.ZPure

object Programs {
  def inquire[A](f: Data => A): Program[A] =
    ZPure.environmentWith(env => f(env.get))

  def inquireShopSlotData(shopSlotDataId: ShopSlotDataId): Program[ShopSlotData] =
    for {
      shopData     <- inquire(_.shop)
      shopSlotData <- extractOption(shopData.slots.get(shopSlotDataId), Error.DataNotFound(shopSlotDataId))
    } yield shopSlotData

  def get[A](f: State => A): Program[A] =
    ZPure.get.map(f)

  lazy val getCoins: Program[Int] =
    get(_.coins)

  def getShopSlot(shopSlotDataId: ShopSlotDataId): Program[ShopSlot] =
    for {
      shop     <- get(_.shop)
      shopSlot <- extractOption(shop.slots.get(shopSlotDataId), Error.EntityNotFound(shopSlotDataId))
    } yield shopSlot

  def purchaseShopSlot(shopSlotDataId: ShopSlotDataId): Program[Unit] =
    for {
      shopSlotData <- inquireShopSlotData(shopSlotDataId)
      shopSlot     <- getShopSlot(shopSlotDataId)
      coins        <- getCoins
      ()           <- assertThat(shopSlot.purchasedItemAmount < shopSlotData.maxItemAmount, Error.ShopSlotSoldOut(shopSlotDataId))
      ()           <- assertThat(coins >= shopSlotData.coinPrice, Error.NotEnoughCoins)
      ()           <- liftEvent(CoinPaid(shopSlotData.coinPrice))
      ()           <- liftEvent(ItemRewarded(shopSlotData.itemDataId))
      ()           <- liftEvent(ShopSlotPurchased(shopSlotDataId))
    } yield ()
}
