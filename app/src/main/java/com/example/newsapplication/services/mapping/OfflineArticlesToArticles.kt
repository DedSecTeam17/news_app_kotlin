package com.example.newsapplication.services.mapping

import com.example.newsapplication.core.utils.EntityMapper
import com.example.newsapplication.models.Article
import com.example.newsapplication.offline_db.OfflineArticle
import javax.inject.Inject

class OfflineArticlesToArticles @Inject constructor() :
    EntityMapper<List<OfflineArticle>, List<Article>> {

    override fun mapFromEntity(entity: List<OfflineArticle>): List<Article> {
        val articles: MutableList<Article> = arrayListOf()

        entity.forEachIndexed { index, offlineArticle ->

            articles.add(
                Article(
                    title = offlineArticle.title,
                    imageUrl = offlineArticle.imageUrl,
                    description = offlineArticle.description,
                    author = offlineArticle.author
                )
            )

        }
        return articles
    }


}