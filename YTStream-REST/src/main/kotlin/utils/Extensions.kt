package utils

import io.vertx.core.AsyncResult
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


fun HttpServerResponse.endJson(any: Any) {
    putHeader("Content-Type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(any))
}

inline fun Route.asyncHandler(crossinline f: suspend (RoutingContext) -> Unit) {
    handler {
        GlobalScope.launch(it.vertx().dispatcher()) {
            try {
                f(it)
            } catch (t: Throwable) {
                it.fail(t)
            }
        }
    }
}

inline fun <T> Vertx.eventBusRequest(
    address: String,
    message: Any,
    crossinline success: (AsyncResult<Message<T>>) -> Unit,
    crossinline error: () -> Unit
) {
    eventBus().request(address, message) { asyncResult: AsyncResult<Message<T>> ->
        if (asyncResult.succeeded()) {
            success(asyncResult)
        } else {
            error()
        }
    }
}