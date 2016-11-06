/**
  * Represents a movements provider
  */
trait MovementsProvider {
    def get(): List[TrackedItem]
}