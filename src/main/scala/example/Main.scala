package example

import zio.ZEnvironment
import zio.prelude.fx.ZPure
import example.entities._
import example.logic.Programs._

//object Main {
//  val program: ZPure[Nothing, Unit, Unit, Any, Nothing, Int] =
//    ZPure.succeed(1)
//
//  def minusOne(n: Int): ZPure[Nothing, Unit, Unit, Any, String, Int] =
//    if (n >= 1)
//      ZPure.succeed(n - 1)
//    else
//      ZPure.fail("n cannot be less than 1")
//
//  val accessEnv: ZPure[Nothing, Unit, Unit, String, Nothing, String] =
//    for {
//      env  <- ZPure.environment
//      value = env.get
//    } yield "environment: " + value
//
//  val changeState: ZPure[Nothing, String, String, Any, Nothing, String] =
//    for {
//      state1 <- ZPure.get
//      ()     <- ZPure.set(state1 + "2")
//      state2 <- ZPure.get
//    } yield "new state: " + state2
//
//  val log: ZPure[String, Unit, Unit, Any, Nothing, String] =
//    for {
//      () <- ZPure.log("cookie")
//      () <- ZPure.log("run")
//      () <- ZPure.log("kingdom")
//    } yield "done"
//
//  def main(args: Array[String]): Unit = {
//    println(program.run)
//    println(minusOne(0).runEither)
//    println(accessEnv.provideEnvironment(ZEnvironment("Hello")).run)
//    println(changeState.run("1"))
//    println(log.runLog)
//  }
//}

object Main {
  def main(args: Array[String]): Unit = {
    val item1DataId     = DataId("item1")
    val item2DataId     = DataId("item2")
    val shopSlot1DataId = DataId("shopSlot1")
    val shopSlot2DataId = DataId("shopSlot2")

    val data = Data(
      Map(
        item1DataId -> ItemData(item1DataId, "Item 1"),
        item2DataId -> ItemData(item2DataId, "Item 2")
      ),
      ShopData(
        Map(
          shopSlot1DataId -> ShopSlotData(shopSlot1DataId, 300, item1DataId, 20),
          shopSlot2DataId -> ShopSlotData(shopSlot2DataId, 5000, item2DataId, 1)
        )
      )
    )

    val initialState = State(
      1000,
      Map.empty,
      Shop(
        Map(
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
