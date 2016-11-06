import java.time.ZonedDateTime

/**
  * Represents meet point
  *
  * @param x     x coordinate
  * @param y     y coordinate
  * @param floor floor number
  * @param time  tracked time
  */
case class MeetPoint(x: Float, y: Float, floor: Int, time: ZonedDateTime)
