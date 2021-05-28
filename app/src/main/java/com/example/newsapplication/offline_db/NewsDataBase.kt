package com.example.newsapplication.offline_db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [OfflineArticle::class], version = 1)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao


    companion object {
        val DATABASE_NAME: String = "news_db"
    }


}