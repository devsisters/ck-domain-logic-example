package example.logic

import example.entities._
import zio.prelude.fx.ZPure

trait Transition[Evt] {
  def run(event: Evt): ZPure[Nothing, State, State, Any, Error, Unit]
}

object Transition {
  implicit val coinPaidTransition: Transition[Event.CoinPaid] =
    new Transition[Event.CoinPaid] {
      def run(event: Event.CoinPaid): ZPure[Nothing, State, State, Any, Error, Unit] =
        for {
          state <- ZPure.get
          ()    <- ZPure.set(state.copy(coins = state.coins - event.amount))
        } yield ()
    }

  implicit val itemRewardedTransition: Transition[Event.ItemRewarded] =
    event =>
      for {
        state <- ZPure.get
        ()    <- ZPure.set(
                   state.copy(
                     items = state.items.updated(
                       event.itemDataId,
                       state.items.getOrElse(event.itemDataId, 0) + 1
                     )
                   )
                 )
      } yield ()

  implicit val shopSlotPurchasedTransition: Transition[Event.ShopSlotPurchased] =
    event =>
      for {
        state    <- ZPure.get
        shopSlot <- extractOption(
                      state.shop.slots.get(event.shopSlotDataId),
                      Error.EntityNotFound(event.shopSlotDataId)
                    )
        ()       <- ZPure.set(
                      state.copy(
                        shop = state.shop.copy(
                          slots = state.shop.slots.updated(
                            shopSlot.dataId,
                            shopSlot.copy(purchasedItemAmount = shopSlot.purchasedItemAmount + 1)
                          )
                        )
                      )
                    )
      } yield ()
}
