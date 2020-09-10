package di

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.nvvi9.YTStream
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module
import verticles.VideoDataVerticle
import verticles.VideoDetailsVerticle
import verticles.WebVerticle


@ExperimentalCoroutinesApi
@FlowPreview
val verticleModule = module {
    single { WebVerticle(get()) }
    single { VideoDetailsVerticle(get(), get()) }
    single { VideoDataVerticle(get(), get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val serviceModule = module {

    fun provideMapper() = ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(KotlinModule())

    factory { provideMapper() }
    single { YTStream() }
}