package com.newsoft.super_note.data.model.base

class ProfileUserModel : ResponeBase() {
    /**
    "error": false,
    "msg": "Đăng nhập thành công.",
    "profile":
    {
        "token": "d99ada2a79900698c19f49dc2a7c55537429c234",
        "id": "3",
        "title": "HOÀNG VĂN QUANG",
        "phone": "0379809629",
        "email": "quangnewsoft@gmail.com",
        "username": "administrator",
        "company": null,
        "address": "HH2 Bắc Hà, số 15 Tố Hữu, Thanh Xuân, HN"
        "role": "customer" // customer là khách hàng; agency là đại lý
    }
}
     */

//    lateinit var profile: Profile
    var info = Info()
    var token = ""

    open class Info {
        var token = ""
        var id = ""
        var name = ""
        var idcode = ""
        var phone = ""
        var email = ""
        var username = ""
        var company = ""
        var address = ""
        var avatarURL = ""
        var ggid = ""
        var fileStoreURL = ""
        var role = ""
        var code = ""
        var begin = false
    }

}