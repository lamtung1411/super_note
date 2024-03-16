package com.newsoft.super_note.utils

interface Defaults {
    companion object {
        const val DATE_FORMAT_SEND = "yyyy-MM-dd"
        const val TOKEN_FAILURE_MSG = "Phiên đăng nhập hết hạn"
        const val ERROR_API = "Có lỗi trong quá trình kết nối!"
        const val COUNT_CUSTOMER = "count_customer"
        const val ACCOUNTANT_ORDER = "AccountantIOrder"

        const val NEW_MESSAGE = "new_message"
        const val CHECK_CHAT_ID = "check_chat_id"
        const val RESULT_CODE_LIST_CAR = 225
        const val RESULT_CODE_NOTIFICATION = 226

        const val RELOAD_MERCHANT = "reload_merchant"

        //TODO type thông báo backend trả về
        const val NOTIFICATION  = "notification"
    }
}