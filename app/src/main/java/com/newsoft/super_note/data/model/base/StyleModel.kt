package com.newsoft.super_note.data.model.base

class StyleModel : ResponeBase() {

    var items: ArrayList<StyleModel.Item>? = null


    class Item {
        constructor(name:String){
            this.name=name
        }
        var name = ""
        var file_db = ""
    }

}