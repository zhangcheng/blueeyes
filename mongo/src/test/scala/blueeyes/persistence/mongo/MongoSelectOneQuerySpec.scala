package blueeyes.persistence.mongo

import org.specs2.mutable.Specification
import MongoQueryBuilder._
import MongoFilterBuilder._
import MongoFilterOperators._
import blueeyes.json.JPathImplicits._
import blueeyes.json._
import blueeyes.json.JPath

class MongoSelectOneQuerySpec extends Specification{
  private val query = selectOne("foo", "bar").from(MongoCollectionReference("collection"))

  "'where' method sets new filter" in {
    query.where("name".jpath === "Joe") mustEqual (MongoSelectOneQuery(MongoSelection(Set(JPath("foo"), JPath("bar"))), "collection", Some(MongoFieldFilter("name", $eq, "Joe"))))
  }

  "'sortBy' method sets new sort" in {
    query.sortBy("name".jpath << ) mustEqual (MongoSelectOneQuery(MongoSelection(Set(JPath("foo"), JPath("bar"))), "collection", None, Some(MongoSort(JPath("name"), MongoSortOrderDescending))))
  }
}
