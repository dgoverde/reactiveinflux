package com.pygmalios.reactiveinflux.command.write

import com.pygmalios.reactiveinflux.PointTime
import org.scalatest.FlatSpec

class PrecisionSpec extends FlatSpec {
  behavior of "Nano"

  it should "convert 1 nanosecond to 1" in {
    assert(Nano.format(PointTime.ofEpochSecond(0, 1)) == "1")
  }

  it should "convert 1 millisecond to 1000000" in {
    assert(Nano.format(PointTime.ofEpochMilli(1)) == "1000000")
  }

  it should "convert 1 second to 1000000000" in {
    assert(Nano.format(PointTime.ofEpochSecond(1)) == "1000000000")
  }

  it should "convert 1 minute to 60000000000" in {
    assert(Nano.format(PointTime.ofEpochSecond(60)) == "60000000000")
  }

  it should "convert 1234567890 to 1234567890000000000" in {
    assert(Nano.format(PointTime.ofEpochSecond(1234567890)) == "1234567890000000000")
  }

  behavior of "Micro"

  it should "convert 1 nanosecond to 0" in {
    assert(Micro.format(PointTime.ofEpochSecond(0, 1)) == "0")
  }

  it should "convert 999 nanoseconds to 0" in {
    assert(Micro.format(PointTime.ofEpochSecond(0, 999)) == "0")
  }

  it should "convert 1 microsecond to 1" in {
    assert(Micro.format(PointTime.ofEpochSecond(0, 1000)) == "1")
  }

  it should "convert 1 millisecond to 1000" in {
    assert(Micro.format(PointTime.ofEpochMilli(1)) == "1000")
  }

  it should "convert 1 second to 100000" in {
    assert(Micro.format(PointTime.ofEpochSecond(1)) == "1000000")
  }

  it should "convert 1 minute to 60000000" in {
    assert(Micro.format(PointTime.ofEpochSecond(60)) == "60000000")
  }

  it should "convert 1234567890 to 1234567890000000" in {
    assert(Micro.format(PointTime.ofEpochSecond(1234567890)) == "1234567890000000")
  }

  behavior of "Milli"

  it should "convert 1 nanosecond to 0" in {
    assert(Milli.format(PointTime.ofEpochSecond(0, 1)) == "0")
  }

  it should "convert 999 nanoseconds to 0" in {
    assert(Milli.format(PointTime.ofEpochSecond(0, 999)) == "0")
  }

  it should "convert 1 microsecond to 0" in {
    assert(Milli.format(PointTime.ofEpochSecond(0, 1000)) == "0")
  }

  it should "convert 999 microsecond to 0" in {
    assert(Milli.format(PointTime.ofEpochSecond(0, 999000)) == "0")
  }

  it should "convert 1 millisecond to 1" in {
    assert(Milli.format(PointTime.ofEpochMilli(1)) == "1")
  }

  it should "convert 1 second to 1000" in {
    assert(Milli.format(PointTime.ofEpochSecond(1)) == "1000")
  }

  it should "convert 1 minute to 60000" in {
    assert(Milli.format(PointTime.ofEpochSecond(60)) == "60000")
  }

  it should "convert 1234567890 to 1234567890000" in {
    assert(Milli.format(PointTime.ofEpochSecond(1234567890)) == "1234567890000")
  }

  behavior of "Second"

  it should "convert 1 nanosecond to 0" in {
    assert(Second.format(PointTime.ofEpochSecond(0, 1)) == "0")
  }

  it should "convert 1 microsecond to 0" in {
    assert(Second.format(PointTime.ofEpochSecond(0, 1000)) == "0")
  }

  it should "convert 1 millisecond to 0" in {
    assert(Second.format(PointTime.ofEpochMilli(1)) == "0")
  }

  it should "convert 999 milliseconds to 0" in {
    assert(Second.format(PointTime.ofEpochMilli(999)) == "0")
  }

  it should "convert 1 second to 1" in {
    assert(Second.format(PointTime.ofEpochSecond(1)) == "1")
  }

  it should "convert 1 minute to 60" in {
    assert(Second.format(PointTime.ofEpochSecond(60)) == "60")
  }

  it should "convert 1234567890 to 1234567890" in {
    assert(Second.format(PointTime.ofEpochSecond(1234567890)) == "1234567890")
  }

  behavior of "Minute"

  it should "convert 1 nanosecond to 0" in {
    assert(Minute.format(PointTime.ofEpochSecond(0, 1)) == "0")
  }

  it should "convert 1 microsecond to 0" in {
    assert(Minute.format(PointTime.ofEpochSecond(0, 1000)) == "0")
  }

  it should "convert 1 millisecond to 0" in {
    assert(Minute.format(PointTime.ofEpochMilli(1)) == "0")
  }

  it should "convert 1 second to 0" in {
    assert(Minute.format(PointTime.ofEpochSecond(1)) == "0")
  }

  it should "convert 59 seconds to 0" in {
    assert(Minute.format(PointTime.ofEpochSecond(59)) == "0")
  }

  it should "convert 1 minute to 1" in {
    assert(Minute.format(PointTime.ofEpochSecond(60)) == "1")
  }

  it should "convert 1234567890 to 20576131" in {
    assert(Minute.format(PointTime.ofEpochSecond(1234567890)) == "20576131")
  }

  behavior of "Hour"

  it should "convert 1 nanosecond to 0" in {
    assert(Hour.format(PointTime.ofEpochSecond(0, 1)) == "0")
  }

  it should "convert 1 microsecond to 0" in {
    assert(Hour.format(PointTime.ofEpochSecond(0, 1000)) == "0")
  }

  it should "convert 1 millisecond to 0" in {
    assert(Hour.format(PointTime.ofEpochMilli(1)) == "0")
  }

  it should "convert 1 second to 0" in {
    assert(Hour.format(PointTime.ofEpochSecond(1)) == "0")
  }

  it should "convert 1 minute to 0" in {
    assert(Hour.format(PointTime.ofEpochSecond(60)) == "0")
  }

  it should "convert 59 minutes to 0" in {
    assert(Hour.format(PointTime.ofEpochSecond(3540)) == "0")
  }

  it should "convert 1234567890 to 342935" in {
    assert(Hour.format(PointTime.ofEpochSecond(1234567890)) == "342935")
  }
}