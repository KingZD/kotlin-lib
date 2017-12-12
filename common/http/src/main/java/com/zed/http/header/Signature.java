package com.zed.http.header;

import com.zed.common.util.EncodeUtils;
import com.zed.common.constant.AppConstant;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Desc  :
 */
public class Signature {

    public static String sign(Request original, Map<String, String> signParams) {
        //sign params
        if (original.method().equals("GET")) {
            for (int i = 0; i < original.url().querySize(); i++) {
                signParams.put(original.url().queryParameterName(i),
                    original.url().queryParameterValue(i));
            }
        } else {
            if (original.body() != null && original.body() instanceof FormBody) {
                FormBody formBody = (FormBody)original.body();
                String key, val;
                for (int i = 0; i < formBody.size(); i++) {
                    key = formBody.encodedName(i);
                    val = EncodeUtils.urlDecode(formBody.encodedValue(i));
                    signParams.put(key, val);
                }
            }
        }

        //sort
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(signParams.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        //sign
        String strToSign = "";
        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> ent = infoIds.get(i);
            if (i > 0) {
                strToSign += "&";
            }
            strToSign += ent.getKey() + "=" + ent.getValue();
        }
        strToSign += AppConstant.SECRET;
        return stringToMD5(strToSign);
    }

    /**
     * 将字符串转成MD5值
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
