package verticles

import io.vertx.core.AsyncResult
import io.vertx.core.eventbus.Message
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle
import utils.*


class WebVerticle : CoroutineVerticle() {

    private fun router() = Router.router(vertx).apply {

        route("/*").handler(BodyHandler.create())
        mountSubRouter("/api/v1", this)

        get("/").asyncHandler {
            it.response().end("YTStream")
        }

        get("/videodetails").asyncHandler {
            vertx.eventBusRequest(VIDEO_DETAILS_ADDRESS, it.request().getParam("id"),
                    { ar: AsyncResult<Message<String>> ->
                        it.response().endJson(ar.result().body().readValue())
                    }, { it.fail(500) })
        }

        get("/videodata").asyncHandler {
            vertx.eventBusRequest(VIDEO_DATA_ADDRESS, it.request().getParam("id"),
                    { ar: AsyncResult<Message<String>> ->
                        it.response().endJson(ar.result().body().readValue())
                    }, { it.fail(500) })
        }
    }


    override suspend fun start() {
        vertx.createHttpServer()
                .requestHandler(router())
                .listenAwait(System.getenv("PORT").toInt(), System.getProperty("http.address", "0.0.0.0"))
    }
}
