package example

import zio.prelude.fx.ZPure
import example.entities._

package object logic {
  type Reader[+A]  = ZPure[Nothing, State, State, Data, Error, A]
  type Program[+A] = ZPure[Event, State, State, Data, Error, A]

  def extractOption[A](option: Option[A], error: => Error): ZPure[Nothing, State, State, Any, Error, A] =
    ZPure.fromOption(option).orElseFail(error)

  def inquire[A](f: Data => A): Reader[A] =
    ZPure.environmentWith(env => f(env.get))

  def liftEvent[Evt <: Event](event: Evt)(implicit transition: Transition[Evt]): Program[Unit] =
    for {
      () <- transition.run(event)
      () <- ZPure.log(event)
    } yield ()
}
