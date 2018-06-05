package com.simple.numberhrd

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.simple.numberhrd.db.RecordEntity
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        initView()
        initData()
    }

    private fun initView() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initData() {
        async(UI) {
            val result = bg {
                (application as App).db?.recordDao()?.getAll()
            }
            val recordEntities = result.await()
            if (recordEntities == null || recordEntities.isEmpty()) {
                toast("暂无记录")
            } else {
                val adapter = Adapter(recordEntities)
                rv_record.layoutManager = LinearLayoutManager(this@RecordActivity)
                rv_record.adapter = adapter
            }
        }

    }

    inner class Adapter(private val recordEntities: List<RecordEntity>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_record, parent, false)
//            val itemView = View.inflate(parent?.context, R.layout.item_record, null)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int = recordEntities.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val entity = recordEntities[position]
            holder?.apply {
                mTvFinishSeconds.text = "${entity.finishSeconds} 秒"
                mTvFinishDate.text = entity.finishDate
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mTvFinishSeconds: TextView = itemView.findViewById(R.id.tv_finishSeconds)
        var mTvFinishDate: TextView = itemView.findViewById(R.id.tv_finishDate)

    }
}