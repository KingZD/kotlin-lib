package com.zed.http;


import com.zed.common.util.LogUtils;
import com.zed.http.rx.RxSchedulers;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ZD on 2017/9/2.
 */

public class GAObservable<T> extends Observable<T> implements Disposable {
    private final Call<T> originalCall;

    public GAObservable(Call<T> originalCall) {
        this.originalCall = originalCall;
    }


    public void execOnThread(Observer<? super T> observer, LifecycleTransformer<T> transformer) {
        compose(transformer)
                .compose(RxSchedulers.<T>compose())
                .subscribe(observer);

    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
// Since Call is a one-shot type, clone it for each new observer.
        Call<T> call = originalCall;

        boolean terminated = false;
        try {
            Response<T> response = call.execute();
            if (!call.isCanceled()) {
                observer.onNext(response.body());
            }
            if (!call.isCanceled()) {
                terminated = true;
                observer.onComplete();
            }
        } catch (Throwable t) {
            Exceptions.throwIfFatal(t);
            if (terminated) {
                RxJavaPlugins.onError(t);
            } else if (!call.isCanceled()) {
                try {
                    observer.onError(t);
                } catch (Throwable inner) {
                    Exceptions.throwIfFatal(inner);
                    RxJavaPlugins.onError(new CompositeException(t, inner));
                }
            }
        }
    }


    @Override
    public void dispose() {
        originalCall.cancel();
        LogUtils.e(originalCall.request().url().toString() + " cancel");
    }

    @Override
    public boolean isDisposed() {
        return originalCall.isCanceled();
    }

    /**
     private static class BodyObserver<R> implements Observer<Response<R>> {
     private final Observer<? super R> observer;
     private boolean terminated;

     BodyObserver(Observer<? super R> observer) {
     this.observer = observer;
     }

     @Override public void onSubscribe(Disposable disposable) {
     observer.onSubscribe(disposable);
     }

     @Override public void onNext(Response<R> response) {
     if (response.isSuccessful()) {
     observer.onNext(response.body());
     } else {
     terminated = true;
     Throwable t = new HttpException(response);
     try {
     observer.onError(t);
     } catch (Throwable inner) {
     Exceptions.throwIfFatal(inner);
     RxJavaPlugins.onError(new CompositeException(t, inner));
     }
     }
     }

     @Override public void onComplete() {
     if (!terminated) {
     observer.onComplete();
     }
     }

     @Override public void onError(Throwable throwable) {
     if (!terminated) {
     observer.onError(throwable);
     } else {
     // This should never happen! onNext handles and forwards errors automatically.
     Throwable broken = new AssertionError(
     "This should never happen! Report as a bug with the full stacktrace.");
     //noinspection UnnecessaryInitCause Two-arg AssertionError constructor is 1.7+ only.
     broken.initCause(throwable);
     RxJavaPlugins.onError(broken);
     }
     }
     }
     **/
}
