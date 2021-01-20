package com.nvvi9.rest

import com.nvvi9.rest.di.KoinVerticleFactory
import com.nvvi9.rest.di.verticleModule
import com.nvvi9.rest.verticles.VideoDataVerticle
import com.nvvi9.rest.verticles.VideoDetailsVerticle
import com.nvvi9.rest.verticles.WebVerticle
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.context.startKoin


@ExperimentalCoroutinesApi
@FlowPreview
class MainVerticle : CoroutineVerticle() {

    override suspend fun start() {
        startKoin { modules(verticleModule) }
        vertx.apply {
            registerVerticleFactory(KoinVerticleFactory)
            deployVerticle("${KoinVerticleFactory.prefix()}:${WebVerticle::class.java.canonicalName}")
            deployVerticle("${KoinVerticleFactory.prefix()}:${VideoDataVerticle::class.java.canonicalName}")
            deployVerticle("${KoinVerticleFactory.prefix()}:${VideoDetailsVerticle::class.java.canonicalName}")
        }
    }
}