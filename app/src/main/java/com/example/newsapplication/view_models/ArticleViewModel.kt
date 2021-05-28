package com.example.newsapplication.view_models

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.newsapplication.core.event.ArticleEvent
import com.example.newsapplication.core.utils.DataState
import com.example.newsapplication.models.Article
import com.example.newsapplication.repository.ArticleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi

//ArticleViewModel
class ArticleViewModel @ViewModelInject constructor(
    private val articleRepository: ArticleRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _articles: MutableLiveData<DataState<List<Article>>> = MutableLiveData()

    val articles: LiveData<DataState<List<Article>>>
        get() = _articles


    fun setStateEvent(articleEvent: ArticleEvent) {
        viewModelScope.launch {
            when (articleEvent) {
                is ArticleEvent.TopHeadLines -> {
                    articleRepository.getToHeadLinesArticles(
                    )
                        .onEach { dataState ->
                            _articles.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is ArticleEvent.OfflineTopHeadLines -> {
                    articleRepository.getToOfflineHeadLinesArticles(
                    )
                        .onEach { dataState ->
                            _articles.value = dataState
                        }
                        .launchIn(viewModelScope)
                }


            }
        }
    }


}