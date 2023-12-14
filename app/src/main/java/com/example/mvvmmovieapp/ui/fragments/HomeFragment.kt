package com.example.mvvmmovieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmmovieapp.ui.activities.MainActivity
import com.example.mvvmmovieapp.ui.activities.MovieDetailsActivity
import com.example.mvvmmovieapp.adapter.PopularMovieAdapter
import com.example.mvvmmovieapp.databinding.FragmentHomeBinding
import com.example.mvvmmovieapp.adapter.TopRatedMovieAdapter
import com.example.mvvmmovieapp.adapter.UpComingMovieAdapter
import com.example.mvvmmovieapp.models.PopularMovieDetails
import com.example.mvvmmovieapp.models.TopRatedMovieDetails
import com.example.mvvmmovieapp.models.UpComingMovieDetails
import com.example.mvvmmovieapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var popularMovieAdapter: PopularMovieAdapter
    private lateinit var topRatedMovieAdapter: TopRatedMovieAdapter
    private lateinit var upComingMovieAdapter: UpComingMovieAdapter
    private var TAG = "HomeFragment"

    companion object {
        const val MOVIE_ID = "com.example.mvvmmovieapp.ui.fragments.id"
        const val MOVIE_NAME = "com.example.mvvmmovieapp.ui.fragments.title"
        const val MOVIE_POSTER = "com.example.mvvmmovieapp.ui.fragments.posterPath"
        const val MOVIE_RELEASE_DATE = "com.example.mvvmmovieapp.ui.fragments.releaseDate"
        const val MOVIE_VOTE_AVERAGE = "com.example.mvvmmovieapp.ui.fragments.voteAverage"
        const val MOVIE_SUMMARY = "com.example.mvvmmovieapp.ui.fragments.overview"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingCase()
        // viewModel.getPopularMovie()
        observePopularMovies()
        preparePopularMovieRecyclerView()

        // viewModel.getTopRatedMovie()
        observeTopRatedMovies()
        prepareTopRatedRecyclerView()

        // viewModel.getUpComingMovie()
        observeUpComingMovie()
        prepareUpComingMovieRecyclerView()
        onResponseCase()

        onPopularMovieClick()
        onTopRatedMovieClick()
        onUpComingMovieClick()

    }

    private fun preparePopularMovieRecyclerView() {
        popularMovieAdapter = PopularMovieAdapter()
        binding.rvPopularMovie.apply {
            layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL, false
            )
            adapter = popularMovieAdapter
        }
    }

    private fun observePopularMovies() {
        viewModel.observePopularMovieLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<PopularMovieDetails>> {
                override fun onChanged(t: List<PopularMovieDetails>?) {
                    val popularMovieList = t
                    popularMovieList!!.forEach {
                        Log.d(TAG, it.title)
                    }
                    popularMovieAdapter.setMovies(popularMovies = popularMovieList as ArrayList<PopularMovieDetails>)
                }
            })
    }

    private fun prepareTopRatedRecyclerView() {
        topRatedMovieAdapter = TopRatedMovieAdapter()
        binding.rvTopRated.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = topRatedMovieAdapter
        }
    }

    private fun observeTopRatedMovies() {
        viewModel.observeTopRatedMovie()
            .observe(viewLifecycleOwner, object : Observer<List<TopRatedMovieDetails>> {
                override fun onChanged(t: List<TopRatedMovieDetails>?) {
                    val topRatedMovieList = t
                    topRatedMovieList!!.forEach {
                        Log.d(TAG, it.title)
                    }
                    topRatedMovieAdapter.setTopRatedMovies(topRatedMovies = topRatedMovieList as ArrayList<TopRatedMovieDetails>)
                }
            })
    }

    private fun prepareUpComingMovieRecyclerView() {
        upComingMovieAdapter = UpComingMovieAdapter()
        binding.rvUpComing.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = upComingMovieAdapter
        }
    }

    private fun observeUpComingMovie() {
        viewModel.observeUpComingMovie()
            .observe(viewLifecycleOwner, object : Observer<List<UpComingMovieDetails>> {
                override fun onChanged(t: List<UpComingMovieDetails>?) {
                    val upComingMovieList = t
                    upComingMovieList!!.forEach {
                        Log.d(TAG, it.title)
                    }
                    upComingMovieAdapter.setUpComingMovies(upComingMovies = upComingMovieList as ArrayList<UpComingMovieDetails>)
                }
            })
    }

    private fun onPopularMovieClick() {
        popularMovieAdapter.onItemClick = { popularMovie ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, popularMovie.id)
            intent.putExtra(MOVIE_NAME, popularMovie.title)
            intent.putExtra(MOVIE_POSTER, popularMovie.posterPath)
            intent.putExtra(MOVIE_RELEASE_DATE, popularMovie.releaseDate)
            intent.putExtra(MOVIE_VOTE_AVERAGE, popularMovie.voteAverage)
            intent.putExtra(MOVIE_SUMMARY, popularMovie.overview)
            startActivity(intent)
        }
    }

    private fun onTopRatedMovieClick() {
        topRatedMovieAdapter.onItemClick = { topRatedMovie ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, topRatedMovie.id)
            intent.putExtra(MOVIE_NAME, topRatedMovie.title)
            intent.putExtra(MOVIE_POSTER, topRatedMovie.posterPath)
            intent.putExtra(MOVIE_RELEASE_DATE, topRatedMovie.releaseDate)
            intent.putExtra(MOVIE_VOTE_AVERAGE, topRatedMovie.voteAverage)
            intent.putExtra(MOVIE_SUMMARY, topRatedMovie.overview)
            startActivity(intent)
        }
    }

    private fun onUpComingMovieClick() {
        upComingMovieAdapter.onItemClick = { upComingMovie ->
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, upComingMovie.id)
            intent.putExtra(MOVIE_NAME, upComingMovie.title)
            intent.putExtra(MOVIE_POSTER, upComingMovie.posterPath)
            intent.putExtra(MOVIE_RELEASE_DATE, upComingMovie.releaseDate)
            intent.putExtra(MOVIE_VOTE_AVERAGE, upComingMovie.voteAverage)
            intent.putExtra(MOVIE_SUMMARY, upComingMovie.overview)
            startActivity(intent)
        }
    }

    private fun loadingCase() {
        binding.linearLayoutHeader.visibility = View.INVISIBLE
        binding.tvPopularMovieLabel.visibility = View.INVISIBLE
        binding.rvPopularMovie.visibility = View.INVISIBLE
        binding.tvTopRatedLabel.visibility = View.INVISIBLE
        binding.rvTopRated.visibility = View.INVISIBLE
        binding.tvUpComing.visibility = View.INVISIBLE
        binding.rvUpComing.visibility = View.INVISIBLE
        binding.progressBarHome.visibility = View.VISIBLE

    }

    private fun onResponseCase() {
        binding.linearLayoutHeader.visibility = View.VISIBLE
        binding.tvPopularMovieLabel.visibility = View.VISIBLE
        binding.rvPopularMovie.visibility = View.VISIBLE
        binding.tvTopRatedLabel.visibility = View.VISIBLE
        binding.rvTopRated.visibility = View.VISIBLE
        binding.tvUpComing.visibility = View.VISIBLE
        binding.rvUpComing.visibility = View.VISIBLE
        binding.progressBarHome.visibility = View.INVISIBLE
    }

}