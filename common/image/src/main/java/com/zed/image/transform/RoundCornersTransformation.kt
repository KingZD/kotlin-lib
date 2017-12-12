package com.zed.image.transform

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import java.security.MessageDigest
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.zed.common.util.SizeUtils
import com.zed.common.util.Utils


/**
 * @author zd
 * @package com.zed.image.transform
 * @fileName RoundCornersTransformation
 * @date on 20f17/12/5 0f0f0f5 14:0f2
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class RoundCornersTransformation : Transformation<Bitmap> {

    override fun updateDiskCacheKey(messageDigest: MessageDigest?) {
    }

    private var mBitmapPool: BitmapPool? = null
    private var mRadius: Float
    private var mDiameter: Float
    private var mCornerType = CornerType.NONE

    constructor(radius: Int, type: CornerType?) {
        mBitmapPool = Glide.get(Utils.getApplicationContext()).bitmapPool
        mCornerType = type ?: CornerType.NONE
        mRadius = SizeUtils.dip2px(radius)
        mDiameter = 2 * mRadius
    }

    enum class CornerType {
        /** 所有角  */
        ALL,
        /** 左上  */
        LEFT_TOP,
        /** 左下  */
        LEFT_BOTTOM,
        /** 右上  */
        RIGHT_TOP,
        /** 右下  */
        RIGHT_BOTTOM,
        /** 左侧  */
        LEFT,
        /** 右侧  */
        RIGHT,
        /** 下侧  */
        BOTTOM,
        /** 上侧  */
        TOP,
        /**  无  **/
        NONE
    }

    override fun transform(context: Context?, resource: Resource<Bitmap>?, outWidth: Int, outHeight: Int): Resource<Bitmap>? {
        val source = resource?.get()
        val width = source!!.width
        val height = source.height
        var bitmap: Bitmap? = mBitmapPool!!.get(width, height, Bitmap.Config.ARGB_8888)
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.setAntiAlias(true)
        paint.setShader(BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        drawRoundRect(canvas, paint, width.toFloat(), height.toFloat())
        return BitmapResource.obtain(bitmap, mBitmapPool)
    }

    private fun drawRoundRect(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        when (mCornerType) {
            RoundCornersTransformation.CornerType.LEFT_TOP -> drawLeftTopCorner(canvas, paint, width, height)
            RoundCornersTransformation.CornerType.LEFT_BOTTOM -> drawLeftBottomCorner(canvas, paint, width, height)
            RoundCornersTransformation.CornerType.RIGHT_TOP -> drawRightTopCorner(canvas, paint, width, height)
            RoundCornersTransformation.CornerType.RIGHT_BOTTOM -> drawRightBottomCorner(canvas, paint, width, height)
            RoundCornersTransformation.CornerType.LEFT -> drawLeftCorner(canvas, paint, width, height)
            RoundCornersTransformation.CornerType.RIGHT -> drawRightCorner(canvas, paint, width, height)
            RoundCornersTransformation.CornerType.BOTTOM -> drawBottomCorner(canvas, paint, width, height)
            RoundCornersTransformation.CornerType.TOP -> drawTopCorner(canvas, paint, width, height)
            RoundCornersTransformation.CornerType.ALL -> canvas.drawRoundRect(RectF(0f, 0f, width, height), mRadius, mRadius, paint)
            else -> canvas.drawRoundRect(RectF(0f, 0f, width, height), mRadius, mRadius, paint)
        }
    }


    /**
     * 画左上角
     */
    private fun drawLeftTopCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        canvas.drawRect(RectF(mRadius.toFloat(), 0f, width, height), paint)
        canvas.drawRect(RectF(0f, mRadius.toFloat(), mRadius.toFloat(), height), paint)
        canvas.drawArc(RectF(0f, 0f, mDiameter.toFloat(), mDiameter.toFloat()), 180f, 90f, true, paint)
    }

    /**
     * 画左下角
     */
    private fun drawLeftBottomCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        canvas.drawRect(RectF(0f, 0f, width, height - mRadius), paint)
        canvas.drawRect(RectF(mRadius.toFloat(), height - mRadius, width, height), paint)
        canvas.drawArc(RectF(0f, height - mDiameter, mDiameter.toFloat(), height), 90f, 90f, true, paint)
    }

    /**
     * 画右上角
     */
    private fun drawRightTopCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        canvas.drawRect(RectF(0f, 0f, width - mRadius, height), paint)
        canvas.drawRect(RectF(width - mRadius, mRadius.toFloat(), width, height), paint)
        canvas.drawArc(RectF(width - mDiameter, 0f, width, mDiameter.toFloat()), 270f, 90f, true, paint)
    }

    /**
     * 画右下角
     */
    private fun drawRightBottomCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        canvas.drawRect(RectF(0f, 0f, width, height - mRadius), paint)
        canvas.drawRect(RectF(0f, height - mRadius, width - mRadius, height), paint)
        canvas.drawArc(RectF(width - mDiameter, height - mDiameter, width, height), 0f, 90f, true, paint)
    }

    /**
     * 画左 角
     */
    private fun drawLeftCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        canvas.drawRect(RectF(mRadius.toFloat(), 0f, width, height), paint)
        canvas.drawRect(RectF(0f, mRadius.toFloat(), mRadius.toFloat(), height - mRadius), paint)
        canvas.drawArc(RectF(0f, 0f, mDiameter.toFloat(), mDiameter.toFloat()), 180f, 90f, true, paint)
        canvas.drawArc(RectF(0f, height - mDiameter, mDiameter.toFloat(), height), 90f, 90f, true, paint)
    }

    /**
     * 画右角
     */
    private fun drawRightCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        canvas.drawRect(RectF(0f, 0f, width - mRadius, height), paint)
        canvas.drawRect(RectF(width - mRadius, mRadius.toFloat(), width, height - mRadius), paint)
        canvas.drawArc(RectF(width - mDiameter, 0f, width, mDiameter.toFloat()), 270f, 90f, true, paint)
        canvas.drawArc(RectF(width - mDiameter, height - mDiameter, width, height), 0f, 90f, true, paint)
    }

    /**
     * 画上 角
     */
    private fun drawTopCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        canvas.drawRect(RectF(0f, mRadius.toFloat(), width, height), paint)
        canvas.drawRect(RectF(mRadius.toFloat(), 0f, width - mRadius, mRadius.toFloat()), paint)
        canvas.drawArc(RectF(0f, 0f, mDiameter.toFloat(), mDiameter.toFloat()), 180f, 90f, true, paint)
        canvas.drawArc(RectF(width - mDiameter, 0f, width, mDiameter.toFloat()), 270f, 90f, true, paint)
    }

    /**
     * 画下 角
     */
    private fun drawBottomCorner(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        canvas.drawRect(RectF(0f, 0f, width, height - mRadius), paint)
        canvas.drawRect(RectF(mRadius.toFloat(), height - mRadius, width - mRadius, height), paint)
        canvas.drawArc(RectF(0f, height - mDiameter, mDiameter.toFloat(), height), 90f, 90f, true, paint)
        canvas.drawArc(RectF(width - mDiameter, height - mDiameter, width, height), 0f, 90f, true, paint)
    }


}