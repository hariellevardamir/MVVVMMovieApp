package com.example.mvvmmovieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvvmmovieapp.ui.activities.MainActivity
import com.example.mvvmmovieapp.ui.activities.MovieDetailsActivity
import com.example.mvvmmovieapp.adapter.FavoriteMoviesAdapter
import com.example.mvvmmovieapp.databinding.FragmentSearchBinding
import com.example.mvvmmovieapp.models.MovieDetails
import com.example.mvvmmovieapp.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchMovieAdapter: FavoriteMoviesAdapter
    private var searchJob: Job? = null
    private var TAG = "SearchFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()
        prepareRecyclerView()
        observeSearchMovie()
        searchMovies()
        onSearchedMovieClick()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchJob?.cancel()
        viewModel.clearSearchMovies()
    }

    private fun onBackPressed() {
        binding.imgSearchBackArrow.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun prepareRecyclerView() {
        searchMovieAdapter = FavoriteMoviesAdapter()
        binding.rvSearchMovies.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = searchMovieAdapter
        }
    }

    private fun observeSearchMovie() {
        viewModel.observerSearchedMovieLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<MovieDetails>> {
                override fun onChanged(t: List<MovieDetails>?) {
                    if (t != null) {
                        val searchedMovie = t
                        searchMovieAdapter.differ.submitList(searchedMovie)
                        binding.tvSearchMoviesSize.text = searchedMovie.size.toString()
                    } else {
                        binding.tvSearchMoviesSize.text = "0"
                    }
                }
            })
    }

    private fun searchMovies() {
        binding.etSearchMovie.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.getSearchMovie(searchQuery = searchQuery.toString())
            }
        }
    }

    private fun onSearchedMovieClick() {
        searchMovieAdapter.onItemClick = { searchedMovies ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MOVIE_ID, searchedMovies.id)
            intent.putExtra(HomeFragment.MOVIE_NAME, searchedMovies.title)
            intent.putExtra(HomeFragment.MOVIE_POSTER, searchedMovies.posterPath)
            intent.putExtra(HomeFragment.MOVIE_VOTE_AVERAGE, searchedMovies.voteAverage)
            intent.putExtra(HomeFragment.MOVIE_RELEASE_DATE, searchedMovies.releaseDate)
            intent.putExtra(HomeFragment.MOVIE_SUMMARY, searchedMovies.overview)
            startActivity(intent)
        }
    }

}