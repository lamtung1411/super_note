package com.swipereveal.demo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chauthai.swipereveallayout.R

/**
 * Created by raquezha on 5/17/23.
 */
@Suppress("UNUSED_PARAMETER")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_recycler_view) {
            startActivity(Intent(this, RecyclerDemoActivity::class.java))
            return true
        }
        if (item.itemId == R.id.action_list_view) {
            startActivity(Intent(this, ListDemoActivity::class.java))
            return true
        }
        if (item.itemId == R.id.action_grid_view) {
            startActivity(Intent(this, GridActivity::class.java))
            return true
        }
        return false
    }

    fun layoutOneOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Layout 1 clicked", Toast.LENGTH_SHORT).show()
    }

    fun layoutTwoOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Layout 2 clicked", Toast.LENGTH_SHORT).show()
    }

    fun layoutThreeOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Layout 3 clicked", Toast.LENGTH_SHORT).show()
    }

    fun layoutFourOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Layout 4 clicked", Toast.LENGTH_SHORT).show()
    }

    fun moreOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "More clicked", Toast.LENGTH_SHORT).show()
    }

    fun deleteOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Delete clicked", Toast.LENGTH_SHORT).show()
    }

    fun archiveOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Archive clicked", Toast.LENGTH_SHORT).show()
    }

    fun helpOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Help clicked", Toast.LENGTH_SHORT).show()
    }

    fun searchOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Search clicked", Toast.LENGTH_SHORT).show()
    }

    fun starOnClick(v: View?) {
        Toast.makeText(this@MainActivity, "Star clicked", Toast.LENGTH_SHORT).show()
    }
}
