package com.newsoft.super_note.data.model

import com.newsoft.super_note.data.model.base.ResponeBase

class BackGroundModel : ResponeBase() {

    var items: ArrayList<Item>? = null

    class Item {
        constructor(id: Int,imgBackGround: Int, colorBar: Int,colorBackGround: Int){
            this.id= id
            this.imgBackGround = imgBackGround
            this.colorBar = colorBar
            this.colorBackGround = colorBackGround

        }
        var id = 0
        var imgBackGround = 0
        var colorBar = 0
        var colorBackGround = 0

    }

}