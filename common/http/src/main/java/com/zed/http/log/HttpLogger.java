package com.zed.http.log;

import com.zed.common.util.LogUtils;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Desc  :
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        int CHUNK_STRING_LENGTH = 30000;
        if (message.length() > CHUNK_STRING_LENGTH) {
            LogUtils.d(message.substring(0, CHUNK_STRING_LENGTH));
        } else {
            LogUtils.d(message);
        }
    }
}
