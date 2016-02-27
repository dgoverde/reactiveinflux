package com.pygmalios.reactiveinflux.command.query

import org.joda.time.{DateTime, DateTimeZone}
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Rfc3339Spec extends FlatSpec {
  behavior of "apply"

  it should "parse ISO time 1970-01-01T00:00:00Z" in {
    assert(Rfc3339(StringValue("1970-01-01T00:00:00Z")) ==
      new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC).toInstant)
  }

  it should "parse ISO time 2015-01-29T21:55:43.702900257Z" in {
    assert(Rfc3339(StringValue("2015-01-29T21:55:43.702900257Z")) ==
      new DateTime(2015, 1, 29, 21, 55, 43, 702, DateTimeZone.UTC).toInstant)
  }
}
