package com.newsoft.super_note.data.model

import com.newsoft.super_note.data.model.base.ResponeBase

class SettingModel : ResponeBase() {

    var items: ArrayList<Item>? = null

    class Item {
        constructor(id:String, title:String, icon:Int ){
            this.id=id
            this.title= title
            this.icon= icon

        }
        var id = ""
        var title = ""
        var icon = 0


    }

}