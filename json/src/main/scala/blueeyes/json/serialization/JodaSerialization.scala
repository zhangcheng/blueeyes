package blueeyes.json
package serialization

import DefaultSerialization._
import org.joda.time.{Instant, DateTime, DateTimeZone, Duration}

trait JodaSerializationImplicits {
  implicit val InstantExtractor = new Extractor[Instant] {
    override def extract(jvalue: JValue): Instant = new Instant(jvalue.deserialize[Long])
  }

  implicit val InstantDecomposer = new Decomposer[Instant] {
    def decompose(dateTime: Instant): JValue = JNum(dateTime.getMillis)
  }

  implicit val DurationExtractor = new Extractor[Duration] {
    override def extract(jvalue: JValue): Duration = new Duration(jvalue.deserialize[Long])
  }

  implicit val DurationDecomposer = new Decomposer[Duration] {
    def decompose(duration: Duration): JValue = JNum(duration.getMillis)
  }

  def ZonedTimeExtractor(zone: DateTimeZone): Extractor[DateTime] = new Extractor[DateTime] {
    override def extract(jvalue: JValue): DateTime = jvalue match {
      case JNum(instant)  => new DateTime(instant.longValue, zone)
      case JString(text)  => new DateTime(text, zone)
      case x => sys.error("Unexpected time format: " + x + "; was anticipating integral millisecond value or text string.")
    }
  }

  implicit val DateTimeDecomposer = new Decomposer[DateTime] {
    def decompose(dateTime: DateTime): JValue = JNum(dateTime.getMillis)
  }
}

object JodaSerializationImplicits extends JodaSerializationImplicits

// vim: set ts=4 sw=4 et:
