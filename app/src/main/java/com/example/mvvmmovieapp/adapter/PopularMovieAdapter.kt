package com.example.mvvmmovieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmmovieapp.databinding.MovieItemBinding
import com.example.mvvmmovieapp.models.PopularMovieDetails
import com.example.mvvmmovieapp.util.Constants

class PopularMovieAdapter : RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {

    private var popularMovieList = ArrayList<PopularMovieDetails>()
    var onItemClick: ((PopularMovieDetails) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(popularMovies: ArrayList<PopularMovieDetails>) {
        this.popularMovieList = popularMovies
        notifyDataSetChanged()
    }

    inner class PopularMovieViewHolder(var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        return PopularMovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val tvShows = popularMovieList[position]
        Glide.with(holder.itemView)
            .load(Constants.POSTER_BASE_URL + tvShows.posterPath)
            .into(holder.binding.imgMovie)
        holder.binding.tvMovieName.text = tvShows.title
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(popularMovieList[position])
        }
    }

    override fun getItemCount(): Int {
        return popularMovieList.size
    }


}