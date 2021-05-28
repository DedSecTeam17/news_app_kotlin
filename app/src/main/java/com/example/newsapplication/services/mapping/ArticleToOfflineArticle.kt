package com.example.newsapplication.services.mapping

import com.example.newsapplication.core.utils.EntityMapper
import com.example.newsapplication.models.Article
import com.example.newsapplication.offline_db.OfflineArticle
import com.example.newsapplication.services.responses.Articles
import javax.inject.Inject

class ArticleToOfflineArticle @Inject constructor() : EntityMapper<Article, OfflineArticle> {

    override fun mapFromEntity(entity: Article): OfflineArticle {
        return OfflineArticle(
            title = entity.title,
            imageUrl = entity.imageUrl,
            description = entity.description,
            author = entity.author
        )
    }


}