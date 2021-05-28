package com.example.newsapplication.fragments

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.R
import com.example.newsapplication.adapters.NewsListAdapter
import com.example.newsapplication.core.actions.NewsAction
import com.example.newsapplication.core.event.ArticleEvent
import com.example.newsapplication.core.utils.DataState
import com.example.newsapplication.core.utils.LiveNetworkStatus
import com.example.newsapplication.databinding.FragmentNewsBinding
import com.example.newsapplication.models.Article
import com.example.newsapplication.view_models.ArticleViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAction {

    lateinit var newsListAdapter: NewsListAdapter
    private val articleViewModel: ArticleViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val newsFragmentBinding: FragmentNewsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        if (isNetworkAvailable(context = requireActivity())) {
            loadArticles()
            Toast.makeText(
                requireContext(),
                "Load online data",
                Toast.LENGTH_LONG
            ).show();
        } else {
            Toast.makeText(
                requireContext(),
                "Load offline data",
                Toast.LENGTH_LONG
            ).show();
            loadOfflineArticles()
        }
        subscribeObserversToGetNewsFromApi(newsFragmentBinding)
        observerNetworkConnection(newsFragmentBinding)


        return newsFragmentBinding.root
    }

    private fun setupNewsList(binding: FragmentNewsBinding, articles: List<Article>) {

        newsListAdapter = NewsListAdapter(

            ctx = requireActivity(),
            newsAction = this,
            articles = articles

        )
        binding.newsList.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

    private fun subscribeObserversToGetNewsFromApi(binding: FragmentNewsBinding) {
        articleViewModel.articles.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Article>> -> {


                    if (dataState.data.isEmpty()) {
                        binding.newsList.visibility = View.GONE
//                        binding.emptyProducts.visibility = View.VISIBLE
                    } else {
                        binding.newsList.visibility = View.VISIBLE
//                        binding.emptyProducts.visibility = View.GONE
                    }
                    setupNewsList(binding, dataState.data);
                }
                is DataState.Error -> {


                    binding.newsList.visibility = View.GONE

                }
                is DataState.Loading -> {
                    print("Loading..")
                }
            }
        })
    }

    private fun observerNetworkConnection(binding: FragmentNewsBinding) {
        LiveNetworkStatus(context ?: return)
            .observe(viewLifecycleOwner, Observer { isConnected ->
                isConnected?.let {
                    if (!isConnected) {
                        // Internet Not Available
                        changeToOfflineStatus(binding)
                        loadOfflineArticles()

                    } else {

                        changeToOnlineStatus(binding)
                        loadArticles();

                    }
                }

                // Internet Available
            })
    }


    private fun changeToOfflineStatus(binding: FragmentNewsBinding) {
        Toast.makeText(
            requireContext(),
            "No Internet connection",
            Toast.LENGTH_LONG
        ).show();
        binding.networkStatus.visibility = View.VISIBLE
        binding.networkStatus.setBackgroundColor(requireActivity().getColor(R.color.red))
        binding.status.text = "Offline"
    }

    private fun changeToOnlineStatus(binding: FragmentNewsBinding) {
        binding.networkStatus.visibility = View.VISIBLE
        binding.networkStatus.setBackgroundColor(requireActivity().getColor(R.color.teal_200))
        binding.status.text = "Online"
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000L)
            binding.networkStatus.visibility = View.GONE

        }
    }

    private fun loadArticles() {
        articleViewModel.setStateEvent(articleEvent = ArticleEvent.TopHeadLines)

    }

    private fun loadOfflineArticles() {
        articleViewModel.setStateEvent(articleEvent = ArticleEvent.OfflineTopHeadLines)

    }


    override fun onNewsClicked(article: Article) {
        val gson = Gson()
        findNavController().navigate(
            NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(
                article = gson.toJson(article)
            )
        )
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}