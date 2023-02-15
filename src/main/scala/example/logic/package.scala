package example

import example.entities._
import zio.prelude.fx.ZPure

package object logic {
  type Program[+A] = ZPure[Event, State, State, Data, Error, A]

  def extractOption[A](option: Option[A], error: => Error): ZPure[Nothing, State, State, Any, Error, A] =
    option match {
      case Some(a) => ZPure.succeed(a)
      case None    => ZPure.fail(error)
    }

  def assertThat(assertion: Boolean, error: => Error): ZPure[Nothing, State, State, Any, Error, Unit] =
    if (assertion) ZPure.unit else ZPure.fail(error)

  def liftEvent[Evt <: Event](event: Evt)(implicit transition: Transition[Evt]): Program[Unit] =
    for {
      () <- transition.run(event)
      () <- ZPure.log(event)
    } yield ()
}
