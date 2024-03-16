package com.newsoft.super_note.data.model

import com.newsoft.super_note.data.model.base.ResponeBase
import java.util.Calendar

class TaskModel() {

    var id = 0
    var title =""
    var theme = 0
    var colorBar = 0
    var colorBackGround = 0
    var items: ArrayList<Item>? = null

    class Item() {
        constructor(id: Int, tick: Boolean, content: String) : this() {
            this.id = id
            this.tick = tick
            this.content = content
        }

        var id = 0
        var idTask = 0
        var tick = false
        var content = ""
        var hasFocus = false
    }

}