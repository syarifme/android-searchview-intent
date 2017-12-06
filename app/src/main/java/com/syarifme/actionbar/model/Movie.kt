package com.syarifme.actionbar.model

import com.google.gson.annotations.SerializedName

/**
 * Created by syarifme on 05/12/17.
 */
data class Movie(
        @SerializedName("id") val id : String,
        @SerializedName("original_title") val title : String,
        @SerializedName("vote_average") val vote : String,
        @SerializedName("poster_path") val poster : String,
        @SerializedName("overview") val overview : String
)

data class MovieResult(@SerializedName("results") val movies : List<Movie>)