package com.pygmalios.reactiveinflux.response

import com.pygmalios.reactiveinflux.ReactiveInfluxException
import com.pygmalios.reactiveinflux.error.ReactiveInfluxError
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import play.api.http.Status
import play.api.libs.json.{JsArray, JsObject, JsString, Json}
import play.api.libs.ws.WSResponse

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class PlayWSJsonResponseSpec extends FlatSpec with MockitoSugar {
  behavior of "results"

  it should "handle success response of a query with 100 points" in {
    // Prepare
    val response = mock[WSResponse]
    when(response.status).thenReturn(Status.OK)
    when(response.json).thenReturn(Json.parse(PlayWSJsonResponseSpec.data1).as[JsObject])

    // Execute
    val result = new TestJsonResponse(response).result

    // Assert
    assert(result.size == 1)

    val series = (result.head \ "series").as[JsArray]
    assert(series.value.size == 1)

    val m1Serie = series.value.head.as[JsObject]
    assert((m1Serie \ "name") == JsString("m1"))
    assert((m1Serie \ "columns") == JsArray(Seq(JsString("time"), JsString("fk"))))
    assert((m1Serie \ "values").as[JsArray].value.size == 100)

    // Verify
    verify(response).json
    verify(response).status
    verifyNoMoreInteractions(response)
  }

  it should "handle empty success response" in {
    // Prepare
    val response = mock[WSResponse]
    when(response.status).thenReturn(Status.OK)
    when(response.json).thenReturn(Json.parse(PlayWSJsonResponseSpec.data2).as[JsObject])

    // Execute
    val result = new TestJsonResponse(response).result

    // Assert
    assert(result.isEmpty)
  }

  it should "handle error response" in {
    // Prepare
    val response = mock[WSResponse]
    when(response.status).thenReturn(Status.NOT_FOUND)
    when(response.json).thenReturn(Json.parse(PlayWSJsonResponseSpec.errorData1).as[JsObject])

    // Execute
    val testJsonResponse = new TestJsonResponse(response)
    val ex = intercept[ReactiveInfluxException] {
      testJsonResponse.result
    }

    // Assert
    assert(ex.getMessage.startsWith("Not a successful response!"))
    assert(testJsonResponse.errors.size == 1)
    assert(testJsonResponse.errors.head.message == "wtf")

    // Verify
    verify(response).json
    verify(response).status
    verifyNoMoreInteractions(response)
  }

  it should "handle success response with an error" in {
    // Prepare
    val response = mock[WSResponse]
    when(response.status).thenReturn(Status.OK)
    when(response.json).thenReturn(Json.parse(PlayWSJsonResponseSpec.errorData2).as[JsObject])

    // Execute
    val testJsonResponse = new TestJsonResponse(response)
    val result = testJsonResponse.result

    // Assert
    assert(result.size == 1)
    assert((result.head \ "error") == JsString("hmm"))
    assert(testJsonResponse.errors.size == 1)
    assert(testJsonResponse.errors.head.message == "hmm")

    // Verify
    verify(response).json
    verify(response).status
    verifyNoMoreInteractions(response)
  }
}

object PlayWSJsonResponseSpec {
  val data1 =
    """{"results":[{"series":[{"name":"m1","columns":["time","fk"],"values":[["1983-01-10T11:42:08.013Z",-1],["1983-01-10T11:42:09.013Z",-1],["1983-01-10T11:42:10.013Z",-1],["1983-01-10T11:42:11.013Z",-1],["1983-01-10T11:42:12.013Z",-1],["1983-01-10T11:42:13.013Z",-1],["1983-01-10T11:42:14.013Z",-1],["1983-01-10T11:42:15.013Z",-1],["1983-01-10T11:42:16.013Z",-1],["1983-01-10T11:42:17.013Z",-1],["1983-01-10T11:42:18.013Z",-1],["1983-01-10T11:42:19.013Z",-1],["1983-01-10T11:42:20.013Z",-1],["1983-01-10T11:42:21.013Z",-1],["1983-01-10T11:42:22.013Z",-1],["1983-01-10T11:42:23.013Z",-1],["1983-01-10T11:42:24.013Z",-1],["1983-01-10T11:42:25.013Z",-1],["1983-01-10T11:42:26.013Z",-1],["1983-01-10T11:42:27.013Z",-1],["1983-01-10T11:42:28.013Z",-1],["1983-01-10T11:42:29.013Z",-1],["1983-01-10T11:42:30.013Z",-1],["1983-01-10T11:42:31.013Z",-1],["1983-01-10T11:42:32.013Z",-1],["1983-01-10T11:42:33.013Z",-1],["1983-01-10T11:42:34.013Z",-1],["1983-01-10T11:42:35.013Z",-1],["1983-01-10T11:42:36.013Z",-1],["1983-01-10T11:42:37.013Z",-1],["1983-01-10T11:42:38.013Z",-1],["1983-01-10T11:42:39.013Z",-1],["1983-01-10T11:42:40.013Z",-1],["1983-01-10T11:42:41.013Z",-1],["1983-01-10T11:42:42.013Z",-1],["1983-01-10T11:42:43.013Z",-1],["1983-01-10T11:42:44.013Z",-1],["1983-01-10T11:42:45.013Z",-1],["1983-01-10T11:42:46.013Z",-1],["1983-01-10T11:42:47.013Z",-1],["1983-01-10T11:42:48.013Z",-1],["1983-01-10T11:42:49.013Z",-1],["1983-01-10T11:42:50.013Z",-1],["1983-01-10T11:42:51.013Z",-1],["1983-01-10T11:42:52.013Z",-1],["1983-01-10T11:42:53.013Z",-1],["1983-01-10T11:42:54.013Z",-1],["1983-01-10T11:42:55.013Z",-1],["1983-01-10T11:42:56.013Z",-1],["1983-01-10T11:42:57.013Z",-1],["1983-01-10T11:42:58.013Z",-1],["1983-01-10T11:42:59.013Z",-1],["1983-01-10T11:43:00.013Z",-1],["1983-01-10T11:43:01.013Z",-1],["1983-01-10T11:43:02.013Z",-1],["1983-01-10T11:43:03.013Z",-1],["1983-01-10T11:43:04.013Z",-1],["1983-01-10T11:43:05.013Z",-1],["1983-01-10T11:43:06.013Z",-1],["1983-01-10T11:43:07.013Z",-1],["1983-01-10T11:43:08.013Z",-1],["1983-01-10T11:43:09.013Z",-1],["1983-01-10T11:43:10.013Z",-1],["1983-01-10T11:43:11.013Z",-1],["1983-01-10T11:43:12.013Z",-1],["1983-01-10T11:43:13.013Z",-1],["1983-01-10T11:43:14.013Z",-1],["1983-01-10T11:43:15.013Z",-1],["1983-01-10T11:43:16.013Z",-1],["1983-01-10T11:43:17.013Z",-1],["1983-01-10T11:43:18.013Z",-1],["1983-01-10T11:43:19.013Z",-1],["1983-01-10T11:43:20.013Z",-1],["1983-01-10T11:43:21.013Z",-1],["1983-01-10T11:43:22.013Z",-1],["1983-01-10T11:43:23.013Z",-1],["1983-01-10T11:43:24.013Z",-1],["1983-01-10T11:43:25.013Z",-1],["1983-01-10T11:43:26.013Z",-1],["1983-01-10T11:43:27.013Z",-1],["1983-01-10T11:43:28.013Z",-1],["1983-01-10T11:43:29.013Z",-1],["1983-01-10T11:43:30.013Z",-1],["1983-01-10T11:43:31.013Z",-1],["1983-01-10T11:43:32.013Z",-1],["1983-01-10T11:43:33.013Z",-1],["1983-01-10T11:43:34.013Z",-1],["1983-01-10T11:43:35.013Z",-1],["1983-01-10T11:43:36.013Z",-1],["1983-01-10T11:43:37.013Z",-1],["1983-01-10T11:43:38.013Z",-1],["1983-01-10T11:43:39.013Z",-1],["1983-01-10T11:43:40.013Z",-1],["1983-01-10T11:43:41.013Z",-1],["1983-01-10T11:43:42.013Z",-1],["1983-01-10T11:43:43.013Z",-1],["1983-01-10T11:43:44.013Z",-1],["1983-01-10T11:43:45.013Z",-1],["1983-01-10T11:43:46.013Z",-1],["1983-01-10T11:43:47.013Z",-1]]}]}]}"""
  val data2 = """{"results":[{}]}"""

  val errorData1 = """{"error": "wtf"}"""
  val errorData2 = """{"results": [{"error": "hmm"}]}"""
}
private class TestJsonResponse(wsResponse: WSResponse) extends PlayWSJsonResponse[Seq[JsObject]](wsResponse) {
  val errors = new ListBuffer[ReactiveInfluxError]

  override val errorHandler: PartialFunction[ReactiveInfluxError, Option[ReactiveInfluxError]] = {
    case error =>
      errors += error
      None
  }
  override def result = results
}