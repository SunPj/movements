import java.time.ZonedDateTime

import scala.math._

/**
  * Meet checker that uses distance to determine if users meet each other
  *
  * @param loader       movements provider
  * @param meetDistance two users is considered to meet each other if distance between them less or equal to meetDistance
  */
class DistanceMeetChecker(loader: MovementsProvider, meetDistance: Float) {

    private case class Position(x: Float, y: Float, time: ZonedDateTime)

    private val trackedItems = loader.get()
        .groupBy(_.uid)
        .mapValues(_.groupBy(_.floor))
        .mapValues(_.mapValues(_.groupBy(_.time.toEpochSecond)))
        .mapValues(_.mapValues(_.mapValues(i => i.map(e => Position(e.x, e.y, e.time)))))

    /**
      * Returns a list of floors visited by user with given uid
      *
      * @param uid user identity
      * @return a list of floors visited by user with given uid
      */
    private def visitedFloors(uid: String): Iterable[Int] = trackedItems.get(uid).map(floors => floors.keys).getOrElse(Iterable.empty)

    private def isMeet(x1: Float, y1: Float, x2: Float, y2: Float): Boolean =
        sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2)) < meetDistance

    private def isMeet(c1: Position, c2: Position): Boolean = c1.time == c2.time && isMeet(c1.x, c1.y, c2.x, c2.y)

    private def meetCoordinates(c1: List[Position], c2: List[Position], f: (Position, Position) => Boolean): List[Position] = for {
        cr1 <- c1
        cr2 <- c2
        if f(cr1, cr2)
    } yield cr1

    /**
      * Returns all positions of user by given uid1 where he could meet user by uid2
      *
      * @param uid1 user identity
      * @param uid2 user identity
      * @return all possible meet points for users by given uids
      */
    def meetPoints(uid1: String, uid2: String): Iterable[MeetPoint] = for {
        vf1 <- visitedFloors(uid1)
        vf2 <- visitedFloors(uid2)
        if vf1 == vf2
        (t, c1) <- trackedItems(uid1)(vf1)
        c2 = trackedItems(uid2)(vf1).getOrElse(t, List.empty[Position])
        c <- meetCoordinates(c1, c2, isMeet)
    } yield MeetPoint(c.x, c.y, vf1, c.time)
}

object DistanceMeetChecker {
    def apply(loader: MovementsProvider, meetDistance: Float): DistanceMeetChecker = new DistanceMeetChecker(loader, meetDistance)
}