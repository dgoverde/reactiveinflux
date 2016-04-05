package com.pygmalios.reactiveinflux.command.query

import com.pygmalios.reactiveinflux.ReactiveInfluxException
import com.pygmalios.reactiveinflux.command.query.Series.{ColumnName, SeriesName}
import com.pygmalios.reactiveinflux.command.write.PointTime
import org.joda.time.Instant

trait QueryResult extends Serializable {
  def q: Query
  def result: Result
  def rows: Seq[Row] = result.singleSeries.rows
  def row: Row = result.singleSeries.singleRow
}

object QueryResult {
  def apply(q: Query, result: Result): QueryResult = SimpleQueryResult(q, result)
}

trait Result extends Serializable {
  def series: Seq[Series]
  def singleSeries: Series = {
    if (series.isEmpty)
      throw new ReactiveInfluxException("No series!")

    if (series.size > 1)
      throw new ReactiveInfluxException(s"More series! [${series.map(_.name).mkString(",")}]")

    series.head
  }
  def isEmpty: Boolean = series.forall(_.isEmpty)
}

object Result {
  def apply(series: Seq[Series]): Result = SimpleResult(series)
}

trait Series extends Serializable {
  def name: SeriesName
  def columns: Seq[ColumnName]
  def rows: Seq[Row]
  def singleRow: Row = {
    if (rows.isEmpty)
      throw new ReactiveInfluxException("No values!")

    if (rows.size > 1)
      throw new ReactiveInfluxException(s"More values! [${rows.size}]")

    rows.head
  }
  def isEmpty: Boolean = rows.isEmpty
  def apply(row: Row, columnName: ColumnName): Value = row.values(columns.indexOf(columnName))
}

object Series {
  val timeColumnName: ColumnName = "time"

  type SeriesName = String
  type ColumnName = String

  def apply(name: SeriesName, columns: Seq[ColumnName], values: Seq[Seq[Value]], timeFormat: TimeFormat): Series = {
    val timeColumnIndex = columns.indexOf(timeColumnName)
    SimpleSeries(name, columns, values.map(Row.apply(_, timeColumnIndex, timeFormat)))
  }
}

trait Row {
  def time: PointTime
  def values: Seq[Value]
}

object Row {
  def apply(items: Seq[Value], timeColumnIndex: Int, timeFormat: TimeFormat): Row =
    SimpleRow(timeFormat(items(timeColumnIndex)), items)
}

sealed trait Value extends Serializable
case class StringValue(value: String) extends Value
case class BigDecimalValue(value: BigDecimal) extends Value
case class BooleanValue(value: Boolean) extends Value

trait TimeFormat {
  def apply(value: Value): PointTime
}

private[reactiveinflux] object Rfc3339 extends TimeFormat {
  override def apply(value: Value): PointTime = value match {
    case StringValue(s) => Instant.parse(s)
    case other => throw new ReactiveInfluxException(s"RFC3339 time must be a string! [$other]")
  }
}

private[reactiveinflux] case class SimpleQueryResult(q: Query, result: Result) extends QueryResult
private[reactiveinflux] case class SimpleResult(series: Seq[Series]) extends Result
private[reactiveinflux] case class SimpleSeries(name: SeriesName, columns: Seq[ColumnName], rows: Seq[Row]) extends Series
private[reactiveinflux] case class SimpleRow(time: PointTime, values: Seq[Value]) extends Row