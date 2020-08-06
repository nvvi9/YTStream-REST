import di.KoinVerticleFactory
import di.serviceModule
import di.verticleModule
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.context.startKoin
import verticles.VideoDataVerticle
import verticles.VideoDetailsVerticle
import verticles.WebVerticle


@ExperimentalCoroutinesApi
@FlowPreview
class MainVerticle : CoroutineVerticle() {

    override suspend fun start() {
        startKoin { modules(verticleModule, serviceModule) }
        vertx.apply {
            registerVerticleFactory(KoinVerticleFactory)
            deployVerticle("${KoinVerticleFactory.prefix()}:${WebVerticle::class.java.canonicalName}")
            deployVerticle("${KoinVerticleFactory.prefix()}:${VideoDataVerticle::class.java.canonicalName}")
            deployVerticle("${KoinVerticleFactory.prefix()}:${VideoDetailsVerticle::class.java.canonicalName}")
        }
    }
}