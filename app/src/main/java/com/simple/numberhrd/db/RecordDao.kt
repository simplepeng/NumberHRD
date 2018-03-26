package com.simple.numberhrd.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Observable

/**
 * Created by simple on 20/03/2018.
 */
@Dao
interface RecordDao {

    @Query("SELECT * FROM tb_record")
    fun getAll(): Observable<List<RecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RecordEntity)
}