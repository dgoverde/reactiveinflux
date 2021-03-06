package com.pygmalios.reactiveinflux.command

import java.net.URI

import com.pygmalios.reactiveinflux.impl.URIUtils
import com.pygmalios.reactiveinflux.{PingResult, ReactiveInfluxCommand, ReactiveInfluxResult}
import play.api.libs.ws._

class PingCommand(baseUri: URI) extends ReactiveInfluxCommand {
  override type TResult = PingResult

  override protected def responseFactory(httpResponse: WSResponse): ReactiveInfluxResult[PingResult] =
    SimplePingResult(httpResponse.header(PingCommand.versionHeader).getOrElse(""))

  override def httpRequest(ws: WSClient) =
    ws.url(URIUtils.appendPath(baseUri, PingCommand.path).toString)
}

object PingCommand {
  val path = "/ping"
  val versionHeader = "X-Influxdb-Version"
}

private[reactiveinflux] case class SimplePingResult(influxDbVersion: String) extends PingResult with ReactiveInfluxResult[PingResult] {
  override def result: PingResult = this
}