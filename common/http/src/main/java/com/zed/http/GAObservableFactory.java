package com.zed.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by ZD on 2017/9/2.
 */

public class GAObservableFactory extends CallAdapter.Factory {
    public static GAObservableFactory create() {
        return new GAObservableFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        //GAResponse<T>
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        //response
//        Class<?> rawObservableType = getRawType(observableType);
        //GAResponse<T> 里面的 T
//        Type responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new GACallAdapter(observableType);
    }

    public class GACallAdapter<R> implements CallAdapter<R, Object> {
        Type returnType;

        public GACallAdapter(Type returnType) {
            this.returnType = returnType;
        }

        @Override
        public Type responseType() {
            return returnType;
        }

        @Override
        public Object adapt(Call<R> call) {
            return new GAObservable<>(call);
        }
    }
}
