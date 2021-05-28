package com.example.newsapplication.core.event

sealed  class ArticleEvent {
    object TopHeadLines : ArticleEvent()
    object OfflineTopHeadLines : ArticleEvent()


}