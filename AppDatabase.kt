package com.pertemuan_4.prak_pmobile_101.datasource.local.entity


import androidx.room.Database
import androidx.room.RoomDatabase
import com.pertemuan_4.prak_pmobile_101.utils.ModelDatabase


@Database(entities = [ModelDatabase::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao?
}