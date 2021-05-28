package com.example.newsapplication.di


import com.example.newsapplication.offline_db.ArticleDao
import com.example.newsapplication.repository.ArticleRepository
import com.example.newsapplication.services.NewsApiCalls
import com.example.newsapplication.services.mapping.ArticleToOfflineArticle
import com.example.newsapplication.services.mapping.NetWorkArticleToModel
import com.example.newsapplication.services.mapping.OfflineArticlesToArticles
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun articleRepo(
        newsApiCalls: NewsApiCalls,
        netWorkArticleToModel: NetWorkArticleToModel,
        articleDao: ArticleDao,
        articleToOfflineArticle: ArticleToOfflineArticle,
        offlineArticlesToArticles: OfflineArticlesToArticles
    ): ArticleRepository {
        return ArticleRepository(
            newsApiCalls,
            netWorkArticleToModel,
            articleToOfflineArticle,
            articleDao,
            offlineArticlesToArticles
        )
    }


}
