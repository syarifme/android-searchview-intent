package com.syarifme.actionbar.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.syarifme.actionbar.R
import com.syarifme.actionbar.adapter.MovieAdapter
import com.syarifme.actionbar.model.Movie
import com.syarifme.actionbar.model.MovieResult
import com.syarifme.actionbar.service.ApiClient
import com.syarifme.actionbar.service.MovieService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var apiKey : String
    var movies : MutableList<Movie> = mutableListOf()
    var adapter = MovieAdapter(movies)
    val movieService : MovieService = ApiClient.getClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rvMovie.layoutManager = LinearLayoutManager(applicationContext)
        rvMovie.adapter = adapter

        apiKey = getString(R.string.api_key)
        getPopularMovies(apiKey)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        val searchItem : MenuItem = menu.findItem(R.id.searchMenu)
        val searchView : SearchView= searchItem.actionView as SearchView

        searchQuery(searchView)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.setting -> ""
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchQuery(searchView: SearchView) {
        var options : MutableMap<String, String> = mutableMapOf()
        options.put("api_key", apiKey)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    return false
                }

                options.put("query", query!!.toString())
                val call : Call<MovieResult> = movieService.searchMovies(options)
                getMovieData(call)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun getPopularMovies(apiKey: String) {
        val call : Call<MovieResult> = movieService.getPopularMovies(apiKey)
        getMovieData(call)
    }

    fun getMovieData(call : Call<MovieResult>) {
        call.enqueue(object : Callback<MovieResult> {
            override fun onFailure(call: Call<MovieResult>?, t: Throwable?) {
                Toast.makeText(applicationContext, "${t.toString()}", Toast.LENGTH_SHORT).show()
                Log.d("Movie Erorr", "${t?.message}")
            }

            override fun onResponse(call: Call<MovieResult>?, response: Response<MovieResult>?) {
                if (response?.body() != null) {
                    movies = response.body()!!.movies.toMutableList()
                    adapter = MovieAdapter(movies)
                    rvMovie.adapter = adapter
                }
                Log.d("Movie", "${response?.body()}")
            }

        })
    }
}
