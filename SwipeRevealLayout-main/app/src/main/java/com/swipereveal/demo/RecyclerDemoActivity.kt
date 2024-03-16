package com.swipereveal.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chauthai.swipereveallayout.databinding.ActivityRecyclerBinding

/**
 * Created by raquezha on 5/17/23.
 */
@Suppress("SameParameterValue")
class RecyclerDemoActivity : AppCompatActivity() {
    private var adapter: RecyclerAdapter? = null
    private val binding: ActivityRecyclerBinding by lazy {
        ActivityRecyclerBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
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
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(this, createList(20))
        binding.recyclerView.adapter = adapter
    }

    private fun createList(n: Int): MutableList<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0 until n) {
            list.add("View $i")
        }
        return list
    }
}
