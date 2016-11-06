import scala.io.Source
import java.time.ZonedDateTime

/**
  * Movements loader that retrieves data from csv file
  *
  * @param fileName csv file name that contains movements data
  */
class CsvFileMovementsLoader(fileName: String, delimiter: String) extends MovementsProvider {
    /**
      * Data is set to be lazy to eliminate multiple loading from file
      */
    private lazy val data = Source.fromFile(fileName).getLines().toList.tail.map(el =>
        el.split(delimiter) match { case Array(time, x, y, floor, uid) =>
            TrackedItem(ZonedDateTime.parse(time), x.toFloat, y.toFloat, floor.toInt, uid)
        })

    override def get(): List[TrackedItem] = data
}

object CsvFileMovementsLoader {
    def apply(fileName: String, delimiter: String): CsvFileMovementsLoader = new CsvFileMovementsLoader(fileName, delimiter)
}