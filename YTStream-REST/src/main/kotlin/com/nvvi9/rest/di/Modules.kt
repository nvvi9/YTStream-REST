package com.nvvi9.rest.di

import com.nvvi9.rest.verticles.VideoDataVerticle
import com.nvvi9.rest.verticles.VideoDetailsVerticle
import com.nvvi9.rest.verticles.WebVerticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module


@ExperimentalCoroutinesApi
@FlowPreview
val verticleModule = module {
    single { WebVerticle() }
    single { VideoDetailsVerticle() }
    single { VideoDataVerticle() }
}