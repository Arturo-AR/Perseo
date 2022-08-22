package com.perseo.telecable.repository

import com.perseo.telecable.model.imgurresponse.ImgurApiResponse
import com.perseo.telecable.network.ImgurApi
import retrofit2.Response
import javax.inject.Inject

class ImgurRepository @Inject constructor(private val api: ImgurApi) {

    suspend fun uploadImage(
        image: String,
        title: String,
        album: String
    ): Response<ImgurApiResponse> {
        return api.uploadImage(image, title, album)
    }

}