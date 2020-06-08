package com.app.data.local.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.data.local.db.user.UserEntity

@Database(
    entities = [
        UserEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val TAG = "AppDatabase"
        const val DATABASE_NAME = "base_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, name: String): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    name
                ).addCallback(object : Callback() {
                    override
                    fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "onCreate")
                    }

                    override
                    fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d(TAG, "onOpen")
                    }
                }).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}