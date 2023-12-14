package com.example.mvvmmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmmovieapp.databinding.MovieItemBinding
import com.example.mvvmmovieapp.models.MovieDetails
import com.example.mvvmmovieapp.util.Constants

class FavoriteMoviesAdapter :
    RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder>() {

    var onItemClick: ((MovieDetails) -> Unit)? = null
    private var TAG = "FavoritesFragmentAdapter"

    inner class FavoriteMoviesViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<MovieDetails>() {
        override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        return FavoriteMoviesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(Constants.POSTER_BASE_URL + movie.posterPath)
            .into(holder.binding.imgMovie)
        holder.binding.tvMovieName.text = movie.title.toString()
        holder.binding.constraintMovie.setOnClickListener {
            onItemClick!!.invoke(differ.currentList[position])
        }
    }

}