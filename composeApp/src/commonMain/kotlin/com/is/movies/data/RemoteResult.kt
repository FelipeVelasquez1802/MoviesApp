package com.`is`.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteResult(
    val page: Int,
    val results: List<RemoteMovie>,
    @SerialName("total_pages") val total_pages: Int,
    @SerialName("total_results") val total_results: Int
)

@Serializable
internal data class RemoteMovie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("original_language") val originalLanguage: String,
    val popularity: Double,
    @SerialName("vote_average") val voteAverage: Double,
)