package com.cv.perseo.network

import com.cv.perseo.model.ImgurApiResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ImgurApi {
    @FormUrlEncoded
    @POST("3/upload")
    suspend fun uploadImage(
        @Field("image") image: String,
        @Field("title") title: String,
        @Field("album") album: String,
    ): Response<ImgurApiResponse>
}