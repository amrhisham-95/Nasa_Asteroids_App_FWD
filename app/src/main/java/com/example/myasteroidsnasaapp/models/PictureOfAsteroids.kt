package com.example.myasteroidsnasaapp.models

import com.squareup.moshi.Json

data class PictureOfAsteroids(
    @Json(name = "media_type") var mediaType: String, var title: String,
    var url: String
)