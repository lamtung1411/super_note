package com.newsoft.super_note.data.model

import com.newsoft.super_note.data.model.base.ResponeBase

class DeleteModel : ResponeBase() {

    var items: ArrayList<Item>? = null

    class Item {
        constructor(id:String, title:String, image:Int,icon:Int,color:Int,colorButton: Int ){
            this.id=id
            this.title= title
            this.image= image
            this.icon= icon
            this.color= color
            this.colorButton= colorButton
        }
        var id = ""
        var title = ""
        var image = 0
        var icon = 0
        var color = 0
        var colorButton = 0

    }

}