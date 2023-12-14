package com.example.mvvmmovieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmmovieapp.databinding.MovieItemBinding
import com.example.mvvmmovieapp.models.PopularMovieDetails
import com.example.mvvmmovieapp.models.TopRatedMovieDetails
import com.example.mvvmmovieapp.util.Constants


class TopRatedMovieAdapter : RecyclerView.Adapter<TopRatedMovieAdapter.TopRatedMovieViewHolder>() {

    private var topRatedMovieList = ArrayList<TopRatedMovieDetails>()
    var onItemClick: ((TopRatedMovieDetails) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setTopRatedMovies(topRatedMovies: ArrayList<TopRatedMovieDetails>) {
        this.topRatedMovieList = topRatedMovies
        notifyDataSetChanged()
    }

    inner class TopRatedMovieViewHolder(var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMovieViewHolder {
        return TopRatedMovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return topRatedMovieList.size
    }

    override fun onBindViewHolder(holder: TopRatedMovieViewHolder, position: Int) {
        val topRatedMoviePosition = topRatedMovieList[position]
        Glide.with(holder.itemView)
            .load(Constants.POSTER_BASE_URL + topRatedMoviePosition.posterPath)
            .into(holder.binding.imgMovie)
        holder.binding.tvMovieName.text = topRatedMoviePosition.title
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(topRatedMoviePosition)
        }
    }

}