package com.simple.numberhrd

import android.app.Application
import android.arch.persistence.room.Room
import com.simple.numberhrd.db.NumberHRD_DB

/**
 * Created by simple on 20/03/2018.
 */
class App : Application() {

    companion object {
        const val adMob_id = "ca-app-pub-2171500527855297~3639703247"
    }

    var db: NumberHRD_DB? = null

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, NumberHRD_DB::class.java,
                "number_hrd")
                .build()
    }


}