package com.simple.numberhrd.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(RecordEntity::class), version = 1,exportSchema = false)
abstract class NumberHRD_DB : RoomDatabase() {

     abstract fun recordDao(): RecordDao

}