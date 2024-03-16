package com.newsoft.super_note.data.model

import com.newsoft.super_note.data.model.base.ResponeBase

class ChatModel {

    var items: ArrayList<Item>? = null

    class Item() {
        constructor(
            id: Int,
            avatar: Int,
            name: String,
            message: String,
            time: String
        ) : this() {
            this.id = id
            this.avatar = avatar
            this.name = name
            this.message = message
            this.time = time
        }

        var id = 0
        var avatar = 0
        var name = ""
        var message = ""
        var time = ""

    }

}