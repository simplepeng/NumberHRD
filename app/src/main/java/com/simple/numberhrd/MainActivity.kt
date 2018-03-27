package com.simple.numberhrd

import android.content.Context
import android.content.Intent
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
import android.widget.Toast
import com.simple.numberhrd.db.RecordEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = "NumberHRD"
    private var mSpanCount = 3
    private val mDatas: MutableList<ItemData> = ArrayList()
    private var mAdapter: Adapter? = null
    private var mCanStart = false
    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null
    private var mTimeSecond = 0
    private val fmt: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()

        mAdapter = Adapter()
        recyclerView.layoutManager = GridLayoutManager(this, mSpanCount)
        recyclerView.adapter = mAdapter
    }

    private fun initView() {
        toolbar.inflateMenu(R.menu.menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.reset -> {
                    reset()
                }
                R.id.three -> {
                    mSpanCount = 3
                    (recyclerView.layoutManager as GridLayoutManager)
                            .spanCount = 3
                    reset()
                }
                R.id.four -> {
                    mSpanCount = 4
                    (recyclerView.layoutManager as GridLayoutManager)
                            .spanCount = 4
                    reset()
                }
                R.id.record -> {
                    startActivity(Intent(this@MainActivity, RecordActivity::class.java))
                }
            }

            true
        }

        btn_start.setOnClickListener {
            mCanStart = true
            it.visibility = View.GONE
            tv_time.visibility = View.VISIBLE
            tv_time.text = "0 秒"
            mTimeSecond = 0
            mTimer = Timer()
            mTimerTask = object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        mTimeSecond++
                        tv_time.text = "$mTimeSecond 秒"
                    }
                }
            }
            mTimer?.schedule(mTimerTask, 1000, 1000)
        }
    }

    private fun reset() {
        btn_start.visibility = View.VISIBLE
        tv_time.visibility = View.GONE
        mCanStart = false
        mTimer?.cancel()
        mTimer = null
        mTimerTask?.cancel()
        mTimerTask = null
        initData()
        mAdapter?.notifyDataSetChanged()
    }

    private fun initData() {
        mDatas.clear()
        val totalCount = mSpanCount * mSpanCount
        val bool = BooleanArray(totalCount)
        val random = Random()
        var randomValue: Int
        for (i in 0 until totalCount - 1) {
            do {
                randomValue = random.nextInt(totalCount - 1)
            } while (bool[randomValue])
            bool[randomValue] = true
//            mDatas.add(ItemData(i))
            mDatas.add(ItemData(randomValue))
        }
        mDatas.add(ItemData(totalCount - 1))
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
            holder.itemView.setOnClickListener {
                Log.d(TAG, "position == $position")
                if (mCanStart) {
                    val topPosition = position - mSpanCount
                    val leftPosition = position - 1
                    val rightPosition = position + 1
                    val bottomRight = position + mSpanCount

                    for (moveToPosition in arrayOf(topPosition, leftPosition, rightPosition, bottomRight)) {
                        if (moveToPosition < 0 || moveToPosition > mDatas.size - 1) {
                            continue
                        }
                        if (moveToPosition == rightPosition && moveToPosition % mSpanCount == 0) {
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

                    if (isFinish(mDatas)) {
                        Log.i(TAG, "Finished")

                        val recordEntity = RecordEntity()
                        recordEntity.finishSeconds = mTimeSecond
                        recordEntity.finishDate = fmt.format(Date())
                        insertRecord(recordEntity)

                        mCanStart = false
                        mTimer?.cancel()
                        mTimer = null
                        mTimerTask?.cancel()
                        mTimerTask = null
                        toast("这么快完成啦，真棒！")

                    }
                }
            }
        }

        override fun getItemCount() = mDatas.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = View.inflate(parent.context, R.layout.item_number, null)
            return ViewHolder(itemView)
        }

        private fun isFinish(mDatas: MutableList<ItemData>): Boolean {
            return mDatas.none {
                mDatas.indexOf(it) < mDatas.size - 1 && it.number >
                        mDatas[mDatas.indexOf(it) + 1].number
            }
        }

    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val mTvNumber: TextView? = itemView?.findViewById(R.id.tv_number)

    }

    fun insertRecord(recordEntity: RecordEntity) {
        async(UI) {
            bg {
                (application as App).db?.recordDao()?.insert(recordEntity)
            }
        }

    }
}
