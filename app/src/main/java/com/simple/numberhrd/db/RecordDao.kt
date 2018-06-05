package com.simple.numberhrd.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Observable

@Dao
interface RecordDao {

    @Query("SELECT * FROM tb_record ORDER BY finish_seconds")
    fun getAll(): List<RecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RecordEntity)
}