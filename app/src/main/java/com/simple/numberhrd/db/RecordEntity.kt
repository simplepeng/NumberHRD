package com.simple.numberhrd.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by simple on 20/03/2018.
 */
@Entity(tableName = "tb_record")
class RecordEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "finish_time")
    var finish_time = 0

    @ColumnInfo(name = "finish_date")
    var finish_date: String = ""
}