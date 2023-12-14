package com.example.mvvmmovieapp.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmmovieapp.ui.activities.MainActivity
import com.example.mvvmmovieapp.ui.activities.MovieDetailsActivity
import com.example.mvvmmovieapp.adapter.FavoriteMoviesAdapter
import com.example.mvvmmovieapp.databinding.FragmentFavoritesBinding
import com.example.mvvmmovieapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter
    private var TAG = "FavoritesFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()
        observeFavorites()
        prepareFavoriteMoviesAdapter()
        onFavoriteMovieClick()
        deleteFavoriteMovies()

    }

    private fun onBackPressed() {
        binding.imgFavoriteBackArrow.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoritesMoviesLiveData().observe(viewLifecycleOwner, Observer { movies ->
            movies.forEach {
                Log.d(TAG, it.title.toString())
            }
            favoriteMoviesAdapter.differ.submitList(movies)
            binding.tvFavoritesSize.text = movies.size.toString()
        })
    }

    private fun prepareFavoriteMoviesAdapter() {
        favoriteMoviesAdapter = FavoriteMoviesAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = favoriteMoviesAdapter
        }
    }

    private fun onFavoriteMovieClick() {
        favoriteMoviesAdapter.onItemClick = { movie ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MOVIE_ID, movie.id)
            intent.putExtra(HomeFragment.MOVIE_NAME, movie.title)
            intent.putExtra(HomeFragment.MOVIE_POSTER, movie.posterPath)
            intent.putExtra(HomeFragment.MOVIE_VOTE_AVERAGE, movie.voteAverage)
            intent.putExtra(HomeFragment.MOVIE_RELEASE_DATE, movie.releaseDate)
            intent.putExtra(HomeFragment.MOVIE_SUMMARY, movie.overview)
            startActivity(intent)
        }
    }

    private fun deleteFavoriteMovies() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoriteMoviesAdapter.differ.currentList[position]
                viewModel.deleteMovie(favoriteMoviesAdapter.differ.currentList[position])
                Snackbar.make(requireView(), "${deletedMeal.title} deleted", Snackbar.LENGTH_LONG)
                    .setAction(
                        SpannableString("Undo").apply {
                            setSpan(
                                ForegroundColorSpan(Color.YELLOW),
                                0,
                                length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }, View.OnClickListener {
                            viewModel.upsertMovie(deletedMeal)
                        }
                    ).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }

}