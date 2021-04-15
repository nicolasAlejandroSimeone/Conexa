package com.example.conexa.repositories.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.conexa.models.CartProduct
import com.example.conexa.models.Product

@Database(entities = [CartProduct::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ProductDAO() : ProductsDao

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase? = null
        private const val ROOM_DB_NAME = "products.db"

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    ROOM_DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}