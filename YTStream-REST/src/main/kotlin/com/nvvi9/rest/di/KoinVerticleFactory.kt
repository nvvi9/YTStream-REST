package com.nvvi9.rest.di

import io.vertx.core.Promise
import io.vertx.core.Verticle
import io.vertx.core.spi.VerticleFactory
import org.koin.core.KoinComponent
import org.koin.java.KoinJavaComponent.get
import java.util.concurrent.Callable


object KoinVerticleFactory : VerticleFactory, KoinComponent {

    override fun createVerticle(verticleName: String?, classLoader: ClassLoader?, promise: Promise<Callable<Verticle>>?) {
        promise?.complete(Callable { get(clazz = Class.forName(verticleName?.substringAfter("koin:"))) as Verticle })
    }

    override fun prefix(): String = "koin"
}