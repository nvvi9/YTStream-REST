package network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


internal interface YTApiService {

    @GET("watch")
    suspend fun getVideoPage(@Query("v") id: String): ResponseBody

    @GET
    suspend fun getVideoInfo(@Url url: String): ResponseBody

    @GET("s/{jsPath}")
    suspend fun getJsFile(@Path("jsPath") jsFilePath: String): ResponseBody
}