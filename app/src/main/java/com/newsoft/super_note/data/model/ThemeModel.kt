package com.newsoft.super_note.data.model

import com.newsoft.super_note.data.model.base.ResponeBase

class ThemeModel : ResponeBase() {

    var items: ArrayList<Item>? = null

    class Item {
        constructor(id:Int, theme: String){
            this.id= id
            this.theme = theme

        }
        var id = 0
        var theme = ""

    }

}