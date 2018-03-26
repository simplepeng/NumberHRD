package com.simple.numberhrd.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "tb_record")
class RecordEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "finish_seconds")
    var finishSeconds = 0

    @ColumnInfo(name = "finish_date")
    var finishDate: String = ""
}