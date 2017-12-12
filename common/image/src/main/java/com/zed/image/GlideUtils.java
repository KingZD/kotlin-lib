package com.zed.image;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.zed.common.util.LogUtils;
import com.zed.common.util.SizeUtils;

import java.io.File;

/**
 * Desc  : GlideApp 图片加载工具类
 */
public class GlideUtils {

    private int placeHolderId;
    private int resId;
    private String path;
    private File file;
    private GlideRequests request;
    private int round;
    boolean isRound = false;
    private int width;
    private int height;

    public GlideUtils() {
    }

    public void loadImg(final ImageView view) {
        GlideRequest<Bitmap> transition = request.asBitmap()
                .override(width, height)
                .placeholder(placeHolderId)
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in));
        if (file != null)
            transition.load(file);
        if (resId > 0)
            transition.load(resId);
        if (!TextUtils.isEmpty(path))
            transition.load(path);
        transition
                .dontTransform()
                .dontAnimate()
                .centerCrop()
                .skipMemoryCache(true)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable rb =
                                RoundedBitmapDrawableFactory.create(view.getResources(), resource);
                        if (isRound)
                            if (round <= 0)
                                rb.setCircular(true);
                            else
                                rb.setCornerRadius(SizeUtils.dip2px(round));
                        view.setImageDrawable(rb);
                        isRound = false;
                    }
                }).getSize(new SizeReadyCallback() {
            @Override
            public void onSizeReady(int width, int height) {
                LogUtils.e("->" + width + "-" + height);
            }
        });
    }

    public static class Builder {
        static Builder builder;
        static GlideUtils app;

        /**
         * @see Glide#with(Context)
         */
        public static Builder with(Context context) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(context);
            return builder;
        }

        /**
         * @see Glide#with(Activity)
         */
        public static Builder with(Activity activity) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(activity);
            return builder;
        }

        /**
         * @see Glide#with(FragmentActivity)
         */
        public static Builder with(FragmentActivity activity) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(activity);
            return builder;
        }

        /**
         * @see Glide#with(Fragment)
         */
        public static Builder with(Fragment fragment) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(fragment.getActivity());
            return builder;
        }

        /**
         * @see Glide#with(Fragment)
         */
        public static Builder with(android.support.v4.app.Fragment fragment) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(fragment.getActivity());
            return builder;
        }

        /**
         * @see Glide#with(View)
         */
        public static Builder with(View view) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(view);
            return builder;
        }

        public Builder load(String url) {
//            if (GAApplication_.isWifiShowImg())
//                url = "";
            app.path = url;
            return builder;
        }

        public Builder load(File file) {
//            if (GAApplication_.isWifiShowImg())
//                url = "";
            app.file = file;
            return builder;
        }

        public Builder load(int resId) {
            app.resId = resId;
            return builder;
        }

        public Builder round(int round) {
            app.isRound = true;
            app.round = round;
            return builder;
        }

        public Builder resize(int width, int height) {
            app.width = width;
            app.height = height;
            return builder;
        }

        public Builder resizeDimen(int width, int height) {
            app.width = width;
            app.height = height;
            return builder;
        }

        public Builder asBitmap() {
            return builder;
        }

        public Builder placeholder(int resId) {
            app.placeHolderId = resId;
            return builder;
        }


        public void into(ImageView v) {
            app.loadImg(v);
            app = null;
            builder = null;
        }
    }
}
