@file:Suppress("SameParameterValue")

package com.swipereveal.demo

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.chauthai.swipereveallayout.R

/**
 * Created by raquezha on 5/17/23.
 */
class ListDemoActivity : AppCompatActivity() {
    private var adapter: ListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter!!.saveStates(outState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter!!.restoreStates(savedInstanceState)
        }
    }

    private fun setupList() {
        val listView = findViewById<View>(R.id.list_view) as ListView
        adapter = ListAdapter(this, createList(20))
        listView.adapter = adapter
    }

    private fun createList(n: Int): List<String?> {
        val list: MutableList<String?> = ArrayList()
        for (i in 0 until n) {
            list.add("View $i")
        }
        return list
    }
}
