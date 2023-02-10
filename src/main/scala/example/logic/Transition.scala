package example.logic

import zio.prelude.fx.ZPure
import example.entities._

trait Transition[Evt] {
  def run(event: Evt): ZPure[Nothing, State, State, Any, Error, Unit]
}

object Transition {
  implicit val coinPaidTransition: Transition[Event.CoinPaid] =
    ???

  implicit val itemRewardedTransition: Transition[Event.ItemRewarded] =
    ???

  implicit val shopSlotPurchasedTransition: Transition[Event.ShopSlotPurchased] =
    ???
}
