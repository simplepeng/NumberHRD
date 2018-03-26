package com.simple.numberhrd.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by simple on 20/03/2018.
 */
@Database(entities = arrayOf(RecordEntity::class), version = 1,exportSchema = false)
abstract class NumberHRD_DB : RoomDatabase() {

     abstract fun recordDao(): RecordDao

}