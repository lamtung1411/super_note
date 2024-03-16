package com.newsoft.super_note.data.model.base

open class ResponeBase {
    var msg = "Có lỗi trong quá trình kết nối!"
    var error = false
    var index = 0
    var balance = ""
    var phone = ""
    var content = ""
    var version_android = ""
    var code = 0
    var total = 0
    var session = ""
    var invited_code = ""
    var auth_token = ""
    var role = ""
    var version = ""
    var url = ""
    var lat = ""
    var lng = ""

    open class Item {
        var id = ""
        var title = ""
        var user_title = ""
        var avatar = ""
        var date = ""
        var level = ""
        var active = ""
        var member = ""
        var icon = ""
        var image = ""
        var url = ""
        var time_create = ""
    }
}