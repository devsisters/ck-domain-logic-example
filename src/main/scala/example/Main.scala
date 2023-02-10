package example

import zio._
import zio.Console._

object Main extends ZIOAppDefault {
  def run =
    for {
      _ <- printLine("!")
    } yield ()
}
