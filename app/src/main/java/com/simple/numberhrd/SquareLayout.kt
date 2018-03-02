package com.simple.numberhrd

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

/**
 * Created by DELL on 2018/3/2.
 */
class SquareLayout(context: Context?, attrs: AttributeSet?) : CardView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}