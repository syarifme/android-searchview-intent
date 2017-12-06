package com.syarifme.actionbar.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.syarifme.actionbar.R
import com.syarifme.actionbar.activity.MovieDetail
import com.syarifme.actionbar.model.Movie
import kotlinx.android.synthetic.main.movie_list.view.*
/**
 * Created by syarifme on 05/12/17.
 */
class MovieAdapter(val movies : List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {
        holder?.bindData(movies.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val view : View = itemView
        var movie : Movie? = null
        override fun onClick(p0: View) {
            val intent = Intent(view.context, MovieDetail::class.java)
            intent.putExtra("movie_id", movie?.id)
            view.context.startActivity(intent)
            Toast.makeText(view.context, "${movie?.title}", Toast.LENGTH_SHORT).show()
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bindData(movie: Movie) {
            this.movie = movie
            val posterBasePath = view.resources.getString(R.string.poster_base_path)
            var posterPath = StringBuilder()
            posterPath.append(posterBasePath).append(movie.poster)
            Glide.with(view.context).load(posterPath.toString()).into(view.mvImage)
            view.mvTitle.setText(movie.title)
            view.mvStar.setText(movie.vote)
        }
    }
}