package com.example.newsapplication.services.mapping

import com.example.newsapplication.core.utils.EntityMapper
import com.example.newsapplication.models.Article
import com.example.newsapplication.services.responses.Articles
import javax.inject.Inject

class NetWorkArticleToModel @Inject constructor() : EntityMapper<Articles, Article> {

    override fun mapFromEntity(entity: Articles): Article {
        return Article(
            title = entity.title,
            imageUrl = entity.urlToImage,
            description = entity.description,
            author = entity.author
        )
    }


}