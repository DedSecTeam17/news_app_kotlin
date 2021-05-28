package com.example.newsapplication.di

import android.content.Context
import androidx.room.Room
import com.example.newsapplication.offline_db.ArticleDao
import com.example.newsapplication.offline_db.NewsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object OfflineDataBaseModule {


    @Singleton
    @Provides
    fun provideCartDataBase(@ApplicationContext context: Context): NewsDataBase {
        return Room
            .databaseBuilder(
                context,
                NewsDataBase::class.java,
                NewsDataBase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideArticleDao(newsDataBase: NewsDataBase): ArticleDao {
        return newsDataBase.articleDao()
    }


}