package com.example.mvvmmovieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmmovieapp.databinding.MovieItemBinding
import com.example.mvvmmovieapp.models.TopRatedMovieDetails
import com.example.mvvmmovieapp.models.UpComingMovieDetails
import com.example.mvvmmovieapp.util.Constants

class UpComingMovieAdapter : RecyclerView.Adapter<UpComingMovieAdapter.UpComingMovieViewHolder>() {

    private var upComingMovieList = ArrayList<UpComingMovieDetails>()
    var onItemClick: ((UpComingMovieDetails) -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setUpComingMovies(upComingMovies: ArrayList<UpComingMovieDetails>) {
        this.upComingMovieList = upComingMovies
        notifyDataSetChanged()
    }

    inner class UpComingMovieViewHolder(var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingMovieViewHolder {
        return UpComingMovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return upComingMovieList.size
    }

    override fun onBindViewHolder(holder: UpComingMovieViewHolder, position: Int) {
        val upComingMoviePosition = upComingMovieList[position]
        Glide.with(holder.itemView)
            .load(Constants.POSTER_BASE_URL + upComingMoviePosition.posterPath)
            .into(holder.binding.imgMovie)
        holder.binding.tvMovieName.text = upComingMoviePosition.title
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(upComingMoviePosition)
        }
    }

}