package com.newsoft.super_note.data.model

import com.newsoft.super_note.data.model.base.ResponeBase

class CategoryModel {

    var items: ArrayList<Item>? = null

    class Item() {
        constructor(
            idNote: Int,
            idTask: Int,
            title: String,
            lock: Boolean,
            image: Int,
            icon: Int,
            color: Int,
            colorButton: Int
        ) : this() {
            this.idNote = idNote
            this.idTask = idTask
            this.title = title
            this.lock = lock
            this.image = image
            this.icon = icon
            this.color = color
            this.colorButton = colorButton
        }

        var id = 0
        var title = ""
        var idNote = 0
        var idTask = 0
        var bin = false
        var lock = false
        var image = 0
        var icon = 0
        var color = 0
        var colorButton = 0
        var pin = false
        var timeCountPin = 0L
    }

}