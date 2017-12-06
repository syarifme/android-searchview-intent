package com.syarifme.actionbar.activity


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.syarifme.actionbar.R
import com.syarifme.actionbar.model.Movie
import com.syarifme.actionbar.service.ApiClient
import kotlinx.android.synthetic.main.movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetail : AppCompatActivity() {
    lateinit var apiKey : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail)

        val intent = getIntent()
        val movieId = intent.extras.get("movie_id")
        apiKey = getString(R.string.api_key)

        val movieService = ApiClient.getClient()
        val call : Call<Movie> = movieService.getMovieDetail(movieId.toString(), apiKey)
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                val movie = response?.body()
                if (movie != null) {
                    Log.d("Movie Detail", "${movie.title}")
                    var posterPath = StringBuilder()
                    posterPath.append(getString(R.string.poster_base_path)).append(movie.poster)
                    mvDtTitle.setText(movie.title)
                    mvDtStar.setText(movie.vote)
                    mvDtOverview.setText(movie.overview)
                    Glide.with(applicationContext).load(posterPath.toString()).into(mvDtImage)
                }
            }

            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                Log.d("Movie Detail Error", "${t?.message}")
            }

        })
    }
}
