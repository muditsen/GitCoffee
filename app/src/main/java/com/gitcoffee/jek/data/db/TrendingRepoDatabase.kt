package com.gitcoffee.jek.data.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gitcoffee.jek.domain.entities.TrendingRepoEntity
import javax.inject.Inject

@Database(entities = [TrendingRepoEntity::class], version = 1)
abstract class TrendingRepoDatabase : RoomDatabase() {

    abstract fun trendingRepoDao(): TrendingRepoDao

    companion object {

        val roomCallback = object:RoomDatabase.Callback(){

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("Room Log","Database created")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d("Room Log","Database opened")
            }
        }


    }


}