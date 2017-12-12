package com.zed.http.header;

import android.text.TextUtils;

import com.zed.common.util.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Desc  : header 参数拦截器
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request.Builder newBuilder = originalRequest.newBuilder();

        // add header
        Map<String, String> headerParams = getHeader(originalRequest);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> ent : headerParams.entrySet()) {
            if (!TextUtils.isEmpty(ent.getValue())) {
                newBuilder.addHeader(ent.getKey(), ent.getValue());
                sb.append(ent.getKey()).append("=").append(ent.getValue()).append(", ");
            } else {
                newBuilder.addHeader(ent.getKey(), "");
                sb.append(ent.getKey()).append("=").append(" ").append(", ");
            }
        }
        LogUtils.d("Header: " + sb.toString());

        return chain.proceed(newBuilder.build());
    }

    private Map<String, String> getHeader(Request originalRequest) {
        Map<String, String> headerParams = new HashMap<>();

        // sign params
        headerParams.put("device-id", HeaderParams.getDevice_id());
        headerParams.put("app-version", HeaderParams.getApp_version());
        headerParams.put("sys-version", HeaderParams.getSys_version());
        headerParams.put("uuid", HeaderParams.getUUID());
        headerParams.put("appkey", HeaderParams.getAppkey());
        headerParams.put("token", HeaderParams.getToken());
        headerParams.put("timestamp", HeaderParams.getTimestamp());
        headerParams.put("model", HeaderParams.getModel());
        headerParams.put("platform", HeaderParams.getPlatform());

        Map<String, String> signParams = new HashMap<>();
        signParams.putAll(headerParams);
        headerParams.put("sign", HeaderParams.getSign(originalRequest, signParams));

        // other params
        headerParams.put("User-Agent", HeaderParams.getUser_agent());
        headerParams.put("Accept", HeaderParams.getAccept());
        headerParams.put("Channel", HeaderParams.getChannel());
        headerParams.put("app-type", HeaderParams.getApp_type());
        headerParams.put("PushId", HeaderParams.getPush_id());
        headerParams.put("Umeng", HeaderParams.getUmeng_id());
        headerParams.put("Content-Type", HeaderParams.getContent_type());
        return headerParams;
    }
}
