package com.cv.perseo.repository

import com.cv.perseo.model.ImgurApiResponse
import com.cv.perseo.network.ImgurApi
import retrofit2.Response
import javax.inject.Inject

class ImgurRepository @Inject constructor(private val api:ImgurApi) {
    suspend fun uploadImage(image: String, title: String, album: String): Response<ImgurApiResponse> {
        return api.uploadImage(image, title, album)
    }
}