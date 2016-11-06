import java.time.ZonedDateTime

/**
  * Class representing a tracked coordinate
  *
  * @param time  tracked time
  * @param x     x coordinate
  * @param y     y coordinate
  * @param floor floor
  * @param uid   user identity
  */
case class TrackedItem(time: ZonedDateTime, x: Float, y: Float, floor: Int, uid: String)
