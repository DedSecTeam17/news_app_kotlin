package com.example.newsapplication.repository

import com.example.newsapplication.core.const.newsApiKey
import com.example.newsapplication.core.utils.DataState
import com.example.newsapplication.models.Article
import com.example.newsapplication.offline_db.ArticleDao
import com.example.newsapplication.offline_db.OfflineArticle
import com.example.newsapplication.services.NewsApiCalls
import com.example.newsapplication.services.mapping.ArticleToOfflineArticle
import com.example.newsapplication.services.mapping.NetWorkArticleToModel
import com.example.newsapplication.services.mapping.OfflineArticlesToArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class ArticleRepository constructor(
    private val newsApiCalls: NewsApiCalls,
    private val netWorkArticleToModel: NetWorkArticleToModel,
    private val articleToOfflineArticle: ArticleToOfflineArticle,
    private val articleDao: ArticleDao,
    private val offlineArticlesToArticles: OfflineArticlesToArticles
) {
    suspend fun getToHeadLinesArticles(): Flow<DataState<List<Article>>> =
        flow {

            emit(DataState.Loading)
            try {
                val topHeadlinesResponse =
                    newsApiCalls.getTopHeadLines(country = "ae", apiKey = newsApiKey);
                val articles: MutableList<Article> = arrayListOf<Article>()
                articleDao.deleteAllArticle();
                topHeadlinesResponse.articles.forEachIndexed { index, entity ->
                    val article = netWorkArticleToModel.mapFromEntity(entity)
                    if (index > 10) {
                        articleDao.insertArticle(
                            offlineArticle = articleToOfflineArticle.mapFromEntity(entity = article)
                        )
                    }
                    articles.add(
                        article
                    )

                }
                emit(DataState.Success(articles))
            } catch (e: Exception) {
                print(e);
                emit(DataState.Error(e))
            }
        }


    suspend fun getToOfflineHeadLinesArticles(): Flow<DataState<List<Article>>> =
        flow {
            emit(DataState.Loading)
            try {
                val offlineArticles: List<OfflineArticle> = articleDao.getAllArticles()
                val articles: List<Article> =
                    offlineArticlesToArticles.mapFromEntity(offlineArticles)
                emit(DataState.Success(articles))
            } catch (e: Exception) {
                print(e);
                emit(DataState.Error(e))
            }
        }
}