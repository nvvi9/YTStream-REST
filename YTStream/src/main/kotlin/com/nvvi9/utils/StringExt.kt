package com.nvvi9.utils

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


internal fun String.decode(): String =
    URLDecoder.decode(this, StandardCharsets.UTF_8.name())

internal fun String.encode(): String =
    URLEncoder.encode(this, StandardCharsets.UTF_8.name())