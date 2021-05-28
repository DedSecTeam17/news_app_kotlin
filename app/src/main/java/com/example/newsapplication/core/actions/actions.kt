package com.example.newsapplication.core.actions

import com.example.newsapplication.models.Article


interface  WaitAction {
    fun  onFinish();
}

interface NewsAction {
    fun  onNewsClicked(article: Article)
}