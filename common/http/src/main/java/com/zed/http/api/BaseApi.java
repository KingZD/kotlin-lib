package com.zed.http.api;

/**
 * Desc  :
 *
 * <p>
 * 注意：
 * 如果方法的泛型指定的类不是ResponseBody,
 * retrofit会将返回的string用json转换器自动转换该类的一个对象,转换不成功就报错
 * 如果不需要Gson转换,那么就指定泛型为ResponseBody,只能是ResponseBody
 * </p>
 */
public interface BaseApi {


}
