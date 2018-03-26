package com.simple.numberhrd

import android.app.Application
import android.arch.persistence.room.Room
import com.simple.numberhrd.db.NumberHRD_DB

class App : Application() {

    var db: NumberHRD_DB? = null

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, NumberHRD_DB::class.java,
                "number_hrd")
                .build()
    }


}