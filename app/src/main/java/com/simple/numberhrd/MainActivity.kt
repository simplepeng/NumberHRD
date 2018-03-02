package com.simple.numberhrd

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    val TAG = "NumberHRD"
    val mSpanCount = 3
    val mDatas: MutableList<ItemData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val totalCount = mSpanCount * mSpanCount
        val bool = BooleanArray(totalCount)
        val random = Random()
        var randomValue = 0
        for (i in 0 until totalCount) {
            do {
                randomValue = random.nextInt(totalCount)
            } while (bool[randomValue])
            bool[randomValue] = true
            mDatas.add(ItemData(randomValue))
        }
//        mDatas.add(ItemData(totalCount-1))
        val adapter = Adapter()
        recyclerView.layoutManager = GridLayoutManager(this, mSpanCount)
        recyclerView.adapter = adapter
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val itemData = mDatas[position]
            val whiteSpaceNumber = itemCount - 1
            val itemView = holder.itemView as CardView
            Log.d(TAG, "whiteSpaceNumber = $whiteSpaceNumber")
            if (itemData.number == whiteSpaceNumber) {
                holder.mTvNumber?.text = ""
                itemView.cardElevation = 0f
                itemView.setCardBackgroundColor(Color.TRANSPARENT)
            } else {
                holder.mTvNumber?.text = (itemData.number + 1).toString()
                itemView.cardElevation = 5f
                itemView.setCardBackgroundColor(Color.WHITE)
            }
            holder.itemView?.setOnClickListener {
                Log.d(TAG, "position == $position")
                val topPosition = position - mSpanCount
                val leftPosition = position - 1
                val rightPosition = position + 1
                val bottomRight = position + mSpanCount

                for (moveToPosition in arrayOf(topPosition, leftPosition, rightPosition, bottomRight)) {
                    if (moveToPosition < 0 || moveToPosition > mDatas.size - 1) {
                        continue
                    }
                    if (mDatas[moveToPosition].number == whiteSpaceNumber) {
                        Log.d(TAG, "moveToPosition = $moveToPosition")
                        val tempData = mDatas[position]
                        mDatas[position] = mDatas[moveToPosition]
                        mDatas[moveToPosition] = tempData
                        notifyDataSetChanged()
                    }
                }


            }
        }

        override fun getItemCount() = mDatas.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = View.inflate(parent.context, R.layout.item_number, null)
            return ViewHolder(itemView)
        }

    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val mTvNumber: TextView? = itemView?.findViewById(R.id.tv_number)

    }
}
