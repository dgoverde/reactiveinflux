package com.pygmalios.reactiveinflux.sync.impl

import com.pygmalios.reactiveinflux.command.query.QueryParameters
import com.pygmalios.reactiveinflux.command.write.WriteParameters
import com.pygmalios.reactiveinflux.sync.SyncReactiveInflux._
import com.pygmalios.reactiveinflux.sync.{SyncReactiveInflux, SyncReactiveInfluxDb}
import com.pygmalios.reactiveinflux.{PointNoTime, Query, ReactiveInfluxConfig, ReactiveInfluxDb}

import scala.concurrent.duration.Duration

class WrappingSyncReactiveInfluxDb(reactiveInfluxDb: ReactiveInfluxDb) extends SyncReactiveInfluxDb {
  override def create()(implicit awaitAtMost: Duration) = await(reactiveInfluxDb.create())
  override def drop(failIfNotExists: Boolean = false)(implicit awaitAtMost: Duration) = await(reactiveInfluxDb.drop(failIfNotExists))

  override def write(point: PointNoTime)(implicit awaitAtMost: Duration) = write(point, WriteParameters())
  override def write(point: PointNoTime, params: WriteParameters)(implicit awaitAtMost: Duration) = write(Seq(point), params)
  override def write(points: Iterable[PointNoTime], params: WriteParameters)(implicit awaitAtMost: Duration) = await(reactiveInfluxDb.write(points, params))

  override def query(q: Query)(implicit awaitAtMost: Duration) = query(q, QueryParameters())
  override def query(q: Query, params: QueryParameters)(implicit awaitAtMost: Duration) = await(reactiveInfluxDb.query(q, params))
  override def query(qs: Seq[Query], params: QueryParameters)(implicit awaitAtMost: Duration) = await(reactiveInfluxDb.query(qs, params))

  override def config: ReactiveInfluxConfig = reactiveInfluxDb.config
}
