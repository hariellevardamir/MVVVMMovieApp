package com.example.mvvmmovieapp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.databinding.ActivityMovieDetailsBinding
import com.example.mvvmmovieapp.db.MovieDatabase
import com.example.mvvmmovieapp.ui.fragments.HomeFragment
import com.example.mvvmmovieapp.models.Genre
import com.example.mvvmmovieapp.models.MovieDetails
import com.example.mvvmmovieapp.models.ProductionCompany
import com.example.mvvmmovieapp.models.ProductionCountry
import com.example.mvvmmovieapp.util.Constants
import com.example.mvvmmovieapp.viewModel.MovieViewModel
import com.example.mvvmmovieapp.viewModel.MovieViewModelFactory

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieDatabase: MovieDatabase
    private lateinit var viewModelFactory: MovieViewModelFactory
    private lateinit var movieId: String
    private lateinit var movieName: String
    private var moviePoster: String? = null
    private var movieReleaseDate: String? = null
    private var movieVoteAverage: Double? = 0.0
    private var movieSummary: String? = null
    private var movieRuntime: Int? = 0
    private var movieToSave: MovieDetails? = null
    private var productionCountry: ProductionCountry? = null
    private var production: ProductionCompany? = null
    private var genres: Genre? = null
    private var movieHomePage: String? = null
    private var TAG: String = "MovieDetailsActivity"

    companion object {
        const val MOVIE_HOMEPAGE_URL: String = "com.example.mvvmmovieapp.ui.activities.homepage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MVVMMovieApp)
        setContentView(binding.root)

        movieDatabase = MovieDatabase.getInstance(this)
        viewModelFactory = MovieViewModelFactory(movieDatabase)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
        loadingCase()
        getInformationFromIntent()
        setInformationViews()
        viewModel.getMovieDetails(movieId)
        onResponseCase()
        Log.d(TAG, movieId)
        pressBackButton()
        observerMovieDetails()
        onFavoriteClick()
        onHomePageUrlClick()

    }

    private fun observerMovieDetails() {
        viewModel.observeMovieDetails().observe(this, object : Observer<MovieDetails> {
            override fun onChanged(t: MovieDetails?) {
                val movieDetail = t
                movieDetail.let {
                    movieToSave = movieDetail
                    movieRuntime = movieDetail!!.runtime!!

                    if (!movieDetail.genres.isNullOrEmpty()) {
                        genres = movieDetail.genres[0]
                    }
                    if (!movieDetail.productionCompanies.isNullOrEmpty()) {
                        production = movieDetail.productionCompanies[0]
                    }
                    if (!movieDetail.productionCountries.isNullOrEmpty()) {
                        productionCountry = movieDetail.productionCountries[0]
                    }
                    if (!movieDetail.homepage.isNullOrEmpty()) {
                        movieHomePage = movieDetail.homepage
                    }
                    Log.d(TAG, "runtime : $movieRuntime min")
                    Log.d(TAG, "genres : ${genres?.name}")
                    Log.d(TAG, "production : $production ")
                    Log.d(TAG, "productionCountry : $productionCountry ")
                    Log.d(TAG, "movieHomePage : $movieHomePage ")
                    setMovieInfo()
                }
            }
        })
    }

    private fun pressBackButton() {
        binding.imgBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getInformationFromIntent() {
        val intent = intent
        movieId = intent.getStringExtra(HomeFragment.MOVIE_ID)!!
        movieName = intent.getStringExtra(HomeFragment.MOVIE_NAME)!!
        moviePoster = intent.getStringExtra(HomeFragment.MOVIE_POSTER)
        movieReleaseDate = intent.getStringExtra(HomeFragment.MOVIE_RELEASE_DATE)
        movieVoteAverage = intent.getDoubleExtra(HomeFragment.MOVIE_VOTE_AVERAGE, 0.0)
        movieSummary = intent.getStringExtra(HomeFragment.MOVIE_SUMMARY)
    }

    @SuppressLint("SetTextI18n")
    private fun setInformationViews() {
        binding.tvDetailMovieName.text = movieName
        binding.tvDate.text = movieReleaseDate
        val formattedVoteAverage = String.format("%.1f", movieVoteAverage)
        binding.tvVote.text = "$formattedVoteAverage/10"
        binding.tvSummaryInfo.text = movieSummary
        Glide.with(this)
            .load(Constants.POSTER_BASE_URL + moviePoster)
            .into(binding.imgBigMoviePoster)
        Glide.with(this)
            .load(Constants.POSTER_BASE_URL + moviePoster)
            .into(binding.imgSmallMoviePoster)
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieInfo() {
        binding.tvTime.text = "$movieRuntime min"
        binding.tvGenre.text = genres?.name ?: "-"
        binding.tvProduction.text = production?.name ?: "-"
        binding.tvProductionCountry.text = productionCountry?.name ?: "-"
        binding.tvMovieUrl.text = movieHomePage ?: "-"
    }

    private fun onFavoriteClick() {
        binding.imgFavorite.setOnClickListener {
            movieToSave?.let {
                viewModel.upsertMovie(it)
                Toast.makeText(this, "${it.title} added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadingCase() {
        binding.linearLayoutDetailsHeader.visibility = View.INVISIBLE
        binding.constraintMoviePoster.visibility = View.INVISIBLE
        binding.linearLayoutMovieInfo.visibility = View.INVISIBLE
        binding.tvGenre.visibility = View.INVISIBLE
        binding.tvProduction.visibility = View.INVISIBLE
        binding.tvProductionCountry.visibility = View.INVISIBLE
        binding.tvSummary.visibility = View.INVISIBLE
        binding.tvSummaryInfo.visibility = View.INVISIBLE
        binding.progressBarMovieDetails.visibility = View.VISIBLE
    }

    private fun onResponseCase() {
        binding.linearLayoutDetailsHeader.visibility = View.VISIBLE
        binding.constraintMoviePoster.visibility = View.VISIBLE
        binding.linearLayoutMovieInfo.visibility = View.VISIBLE
        binding.tvGenre.visibility = View.VISIBLE
        binding.tvProduction.visibility = View.VISIBLE
        binding.tvProductionCountry.visibility = View.VISIBLE
        binding.tvSummary.visibility = View.VISIBLE
        binding.tvSummaryInfo.visibility = View.VISIBLE
        binding.progressBarMovieDetails.visibility = View.INVISIBLE
    }

    private fun onHomePageUrlClick() {
        binding.tvMovieUrl.setOnClickListener {
            val movieUrl = binding.tvMovieUrl.text.toString()
            if (movieUrl.isNotEmpty() && movieUrl != "-") {
                Log.d(TAG, "Clicked: $movieUrl")
                val intent = Intent(this, MovieHomePageActivity::class.java)
                intent.putExtra(MOVIE_HOMEPAGE_URL, movieUrl)
                startActivity(intent)
            } else {
                binding.tvMovieUrl.isClickable = false
                binding.tvMovieUrl.setOnClickListener(null)
            }
        }
    }

}
