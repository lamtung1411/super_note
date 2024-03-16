package com.newsoft.super_note.data.rest.exception;

import java.io.IOException;


public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "Không có kết nối mạng";
    }
}
