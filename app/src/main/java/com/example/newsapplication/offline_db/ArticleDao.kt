package com.example.newsapplication.offline_db

import androidx.room.*


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(offlineArticle: OfflineArticle): Long

    @Query("SELECT * FROM article ORDER BY id ASC")
    suspend fun getAllArticles(): List<OfflineArticle>

    @Query("DELETE  FROM article ")
    suspend fun deleteAllArticle(): Int
}