package com.nvvi9

import com.nvvi9.model.VideoData
import com.nvvi9.model.VideoDetails
import com.nvvi9.model.extraction.EncodedStreams
import com.nvvi9.model.extraction.Raw
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.rx3.asObservable


@ExperimentalCoroutinesApi
@FlowPreview
class YTStream {

    fun extractVideoData(vararg id: String) =
            id.asFlow()
                    .flatMapMerge { Raw.fromIdFlow(it) }
                    .flatMapMerge { EncodedStreams.fromRawFlow(it) }
                    .flatMapMerge { VideoData.fromEncodedStreamsFlow(it) }

    fun extractVideoDetails(vararg id: String) =
            id.asFlow()
                    .flatMapMerge { VideoDetails.fromIdFlow(it) }

    fun extractVideoDataObservable(vararg id: String) =
            extractVideoData(*id).asObservable()

    fun extractVideoDetailsObservable(vararg id: String) =
            extractVideoDetails(*id).asObservable()
}