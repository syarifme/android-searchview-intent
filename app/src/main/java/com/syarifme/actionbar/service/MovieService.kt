package com.syarifme.actionbar.service

import com.syarifme.actionbar.model.Movie
import com.syarifme.actionbar.model.MovieResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by syarifme on 05/12/17.
 */
interface MovieService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey : String) : Call<MovieResult>
    @GET("search/movie")
    fun searchMovies(@QueryMap options : Map<String, String>) : Call<MovieResult>
    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") id : String, @Query("api_key") apiKey: String) : Call<Movie>
}