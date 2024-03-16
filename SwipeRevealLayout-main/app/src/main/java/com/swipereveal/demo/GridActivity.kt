package com.swipereveal.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chauthai.swipereveallayout.databinding.ActivityGridBinding

/**
 * Created by raquezha on 5/17/23.
 */
@Suppress("SameParameterValue")
class GridActivity : AppCompatActivity() {
    private var adapter: GridAdapter? = null
    private val binding: ActivityGridBinding by lazy {
        ActivityGridBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupGrid()
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

    private fun setupGrid() {
        adapter = GridAdapter(this, createList(20))
        binding.gridview.adapter = adapter
    }

    private fun createList(n: Int): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0 until n) {
            list.add("View $i")
        }
        return list
    }
}
