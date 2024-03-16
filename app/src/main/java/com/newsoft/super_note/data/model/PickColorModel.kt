package com.newsoft.super_note.data.model

import com.newsoft.super_note.data.model.base.ResponeBase

class PickColorModel : ResponeBase() {

    var items: ArrayList<Item>? = null

    class Item {
        constructor(id:Int, colorText: Int){
            this.id= id
            this.colorText = colorText

        }
        var id = 0
        var colorText = 0

    }

}