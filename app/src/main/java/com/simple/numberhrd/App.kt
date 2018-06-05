package com.simple.numberhrd

import android.app.Application
import android.arch.persistence.room.Room
import com.google.android.gms.ads.MobileAds
import com.simple.numberhrd.db.NumberHRD_DB

class App : Application() {

    companion object {
        const val adMob_id = "ca-app-pub-2171500527855297~3639703247"
    }

    var db: NumberHRD_DB? = null

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this, adMob_id)
        db = Room.databaseBuilder(this, NumberHRD_DB::class.java,
                "number_hrd")
                .build()
    }


}