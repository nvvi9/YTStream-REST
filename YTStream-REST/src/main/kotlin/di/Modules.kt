package di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module
import verticles.VideoDataVerticle
import verticles.VideoDetailsVerticle
import verticles.WebVerticle


@ExperimentalCoroutinesApi
@FlowPreview
val verticleModule = module {
    single { WebVerticle() }
    single { VideoDetailsVerticle() }
    single { VideoDataVerticle() }
}