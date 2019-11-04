package com.gitcoffee.jek.presentation.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.gitcoffee.jek.data.Constants
import com.gitcoffee.jek.data.db.TrendingRepoDatabase
import com.gitcoffee.jek.data.repos.GitTrendingLocalRepository
import com.gitcoffee.jek.data.repos.GitTrendingRemoteRepository
import com.gitcoffee.jek.data.repos.GitTrendingRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule(private var context: Context) {

    @Provides
    fun provideContext():Context {
        return context
    }

    @Singleton @Provides
    fun provideSharedPref(context: Context):SharedPreferences{
        return context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }


    @Singleton
    @Provides
    fun provideTrendingDatabase(context: Context): TrendingRepoDatabase {
        return Room.databaseBuilder(
            context,
            TrendingRepoDatabase::class.java,
            "git_database"
        ).fallbackToDestructiveMigration().addCallback(TrendingRepoDatabase.roomCallback).build()

    }


    @Singleton
    @Provides
    fun provideGitTrendingRepository(context: Context,db:TrendingRepoDatabase): GitTrendingRepository {
        return GitTrendingRepository(
            GitTrendingLocalRepository(db),
            GitTrendingRemoteRepository()
            ,provideSharedPref(context)
        )
    }


    @Singleton
    @Provides
    fun provideGitTrendingLocalRepository(db:TrendingRepoDatabase): GitTrendingLocalRepository {
        return GitTrendingLocalRepository(db)
    }


    @Singleton
    @Provides
    fun provideGitTrendingRemoteRepository(): GitTrendingRemoteRepository {
        return GitTrendingRemoteRepository()
    }


}