package com.zed.image;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.zed.common.util.LogUtils;
import com.zed.common.util.SizeUtils;
import com.zed.image.round.CornerType;
import com.zed.image.round.MyRoundedBitmapDrawable;
import com.zed.image.round.RoundedBitmapDrawableFactory;

import java.io.File;

/**
 * Desc  : GlideApp 图片加载工具类
 */
public class GlideUtils {

    private int error;
    private int placeHolderId;
    private int resId;
    private String path;
    private File file;
    private GlideRequests request;
    private int round;
    boolean isRound = false;
    private int width;
    private int height;
    private CornerType cornerType;
    private boolean dontCenterCrop = false;

    public GlideUtils() {
    }

    public void loadImg(final ImageView view) {
        GlideRequest<Bitmap> transition = request.asBitmap()
                .override(width, height)
                .placeholder(placeHolderId)
                .error(error)
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .dontTransform()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (file != null)
            transition.load(file);
        if (resId > 0)
            transition.load(resId);
        if (!TextUtils.isEmpty(path))
            transition.load(path);

        if (!dontCenterCrop)
            transition.centerCrop()
                    .into(new BitmapImageViewTarget(view) {
                        @Override
                        public void onResourceReady(Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            super.onResourceReady(resource, transition);
                            MyRoundedBitmapDrawable rb =
                                    RoundedBitmapDrawableFactory.create(view.getResources(), resource, cornerType);
                            if (isRound)
                                if (round <= 0)
                                    rb.setCircular(true);
                                else
                                    rb.setCornerRadius(SizeUtils.dip2px(round));
                            view.setImageDrawable(rb);
                        }

                    }).getSize(new SizeReadyCallback() {
                @Override
                public void onSizeReady(int width, int height) {
                    LogUtils.e("->" + width + "-" + height);
                }
            });
        else
            transition
                    .into(new BitmapImageViewTarget(view) {
                        @Override
                        public void onResourceReady(Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            super.onResourceReady(resource, transition);
                            MyRoundedBitmapDrawable rb =
                                    RoundedBitmapDrawableFactory.create(view.getResources(), resource, cornerType);
                            if (isRound)
                                if (round <= 0)
                                    rb.setCircular(true);
                                else
                                    rb.setCornerRadius(SizeUtils.dip2px(round));
                            view.setImageDrawable(rb);
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
            app.dontCenterCrop = false;
            return builder;
        }

        /**
         * @see Glide#with(Activity)
         */
        public static Builder with(Activity activity) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(activity);
            app.dontCenterCrop = false;
            return builder;
        }

        /**
         * @see Glide#with(FragmentActivity)
         */
        public static Builder with(FragmentActivity activity) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(activity);
            app.dontCenterCrop = false;
            return builder;
        }

        /**
         * @see Glide#with(Fragment)
         */
        public static Builder with(Fragment fragment) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(fragment.getActivity());
            app.dontCenterCrop = false;
            return builder;
        }

        /**
         * @see Glide#with(Fragment)
         */
        public static Builder with(android.support.v4.app.Fragment fragment) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(fragment.getActivity());
            app.dontCenterCrop = false;
            return builder;
        }

        /**
         * @see Glide#with(View)
         */
        public static Builder with(View view) {
            builder = new Builder();
            app = new GlideUtils();
            app.request = GlideApp.with(view);
            app.dontCenterCrop = false;
            return builder;
        }

        public Builder load(String url) {
//            if (GAApplication_.isWifiShowImg())
//                url = "";
            app.path = TextUtils.isEmpty(url) ? "http://png" : url;
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

        public Builder error(int resId) {
            app.error = resId;
            return builder;
        }

        /**
         * 左上
         */
        public Builder roundLeftTop() {
            app.cornerType = CornerType.LEFT_TOP;
            return builder;
        }

        /**
         * 左下
         */
        public Builder roundLeftBottom() {
            app.cornerType = CornerType.LEFT_BOTTOM;
            return builder;
        }

        /**
         * 右上
         */
        public Builder roundRightTop() {
            app.cornerType = CornerType.RIGHT_TOP;
            return builder;
        }

        /**
         * 右下
         */
        public Builder roundRightBottom() {
            app.cornerType = CornerType.RIGHT_BOTTOM;
            return builder;
        }

        /**
         * 左侧
         */
        public Builder roundLeft() {
            app.cornerType = CornerType.LEFT;
            return builder;
        }

        /**
         * 右侧
         */
        public Builder roundRight() {
            app.cornerType = CornerType.RIGHT;
            return builder;
        }

        /**
         * 下侧
         */
        public Builder roundBottom() {
            app.cornerType = CornerType.BOTTOM;
            return builder;
        }

        /**
         * 上侧
         */
        public Builder roundTop() {
            app.cornerType = CornerType.TOP;
            return builder;
        }

        /**
         * 无
         **/
        public Builder roundNone() {
            app.cornerType = CornerType.NONE;
            return builder;
        }

        /**
         * 左上直角
         **/
        public Builder rectangleLeftTop() {
            app.cornerType = CornerType.RECTANGLE_LEFT_TOP;
            return builder;
        }

        /**
         * 左下直角
         **/
        public Builder rectangleLeftBottom() {
            app.cornerType = CornerType.RECTANGLE_LEFT_BOTTOM;
            return builder;
        }

        /**
         * 右上直角
         **/
        public Builder rectangleRightTop() {
            app.cornerType = CornerType.RECTANGLE_RIGHT_TOP;
            return builder;
        }

        /**
         * 右下直角
         **/
        public Builder rectangleRightBottom() {
            app.cornerType = CornerType.RECTANGLE_RIGHT_BOTTOM;
            return builder;
        }

        public Builder dontCenterCrop() {
            app.dontCenterCrop = true;
            return builder;
        }

        public Builder cornerType(CornerType cornerType) {
            app.cornerType = (cornerType == null ? CornerType.NONE : cornerType);
            return builder;
        }

        public void into(ImageView v) {
            app.loadImg(v);
            app = null;
            builder = null;
        }
    }
}
