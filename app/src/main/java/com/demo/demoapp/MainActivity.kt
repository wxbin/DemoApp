package com.demo.demoapp

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var activitys : List<String>? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }


    private fun initView(){
        loadPages()
        pageLV.adapter = MyAdapter()
        pageLV.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent()
            intent.component = ComponentName(packageName, activitys!![position])
            startActivity(intent)
        }
    }

    private fun loadPages() {
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        activitys = packageInfo.activities.map { it -> it.name }.filter { !it.contains("Main") }
    }

    inner class MyAdapter : BaseAdapter(){
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: TextView = LayoutInflater.from(parent?.context).inflate(
                    android.R.layout.simple_list_item_1,null) as TextView
            view.text = activitys?.get(position)
            return view

        }

        override fun getItem(position: Int): Any {
            return 0
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return activitys ?.size ?: 0
        }

    }
}
