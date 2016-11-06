/**
  * @author kraken
  */
object App {
    def main(args: Array[String]): Unit = {
        require(args.length == 5, "Required params are uid1, uid2, csv file name, delimiter, distance")

        val (uid1, uid2, csvFileName, delimiter) = Tuple4.apply(args(0), args(1), args(2), args(3))
        val distance = args(4).toInt

        val movementsProvider = CsvFileMovementsLoader(csvFileName, delimiter)
        val trackEngine = DistanceMeetChecker(movementsProvider, distance)
        val meetPoints = trackEngine.meetPoints(uid1, uid2)

        println(formatOut(uid1, uid2, meetPoints))
    }

    def formatOut(uid1: String, uid2: String, meetPoints: Iterable[MeetPoint]) =
        if (meetPoints.isEmpty)
            s"Users ($uid1, $uid2) didn't meet each other"
        else {
            val meetPointsFormatted = meetPoints.map(p => s"${p.time}, ${p.x}, ${p.y}, ${p.floor}").mkString
            s"Users ($uid1, $uid2) meet each other.\n Meet points: $meetPointsFormatted"
        }
}