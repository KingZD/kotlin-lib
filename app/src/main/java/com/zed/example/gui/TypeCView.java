package com.zed.example.gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.zed.common.util.LogUtils;

/**
 * @author zd
 * @package com.zed.example.gui
 * @fileName TypeCView
 * @date on 2017/12/19 0019 13:47
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */

public class TypeCView extends ViewGroup {
    private RectF screenRectF;
    private RectF funnelRectF;//漏斗
    private Paint paint;
    private int startColor = Color.parseColor("#666666");
    private int endColor = Color.parseColor("#999999");
    private int strokeWidth = 5;

    public TypeCView(Context context) {
        super(context);
        init();
    }

    public TypeCView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypeCView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setWillNotDraw(false);
        screenRectF = new RectF();
        funnelRectF = new RectF();
        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenRectF.set(0, 0, w, h);
        funnelRectF.set(w / 4, h / 4, w / 4 * 3, h / 4 * 3);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = funnelRectF.left + funnelRectF.width() / 4;
        float top = funnelRectF.top + funnelRectF.height() / 5;
        float right = funnelRectF.right - funnelRectF.width() / 4;
        float bottom = funnelRectF.bottom - funnelRectF.height() / 5 * 2;
        LogUtils.i("TAG", left + "-" + top + "-" + right + "-" + bottom);
        LogUtils.i("TAG", funnelRectF.left + "-" + funnelRectF.top + "-" + funnelRectF.right + "-" + funnelRectF.bottom);
        RectF ldRect = new RectF(left, top, right, bottom);
        paint.setShader(new LinearGradient(left, top, right, bottom, startColor, endColor, Shader.TileMode.CLAMP));
        /**漏斗区域**/
        canvas.drawRect(ldRect, paint);
        /**仪器区域**/
        canvas.drawRect(funnelRectF, paint);
        //三角形
        float sjxX = left + (right - left) / 2;
        float sjxY = bottom + (bottom - top) / 2;
        Path path = new Path();
        path.moveTo(left, bottom);
        path.lineTo(sjxX, sjxY);
        path.lineTo(right, bottom);
        path.close();
        canvas.drawPath(path, paint);
        /**三角形链接的管子**/
        float gzWidth = 20;
        canvas.drawRect(new RectF(sjxX - gzWidth / 2, sjxY, sjxX + gzWidth / 2, sjxY + gzWidth * 2), paint);
        /**插入漏斗的杆子左半截**/
        float ldgzX = funnelRectF.left;
        float ldgzY = top + ldRect.height() / 2;
        canvas.drawRect(new RectF(ldgzX, ldgzY, left + ldRect.width() / 3, ldgzY + gzWidth), paint);
        /**插入漏斗的杆子左半截下面滴水的口子**/
        float ldgzdsX = left + ldRect.width() / 3 / 2 - gzWidth / 2;
        float ldgzdsY = ldgzY + gzWidth;
        canvas.drawRect(new RectF(ldgzdsX, ldgzdsY, ldgzdsX + gzWidth, ldgzdsY + gzWidth), paint);
        /**插入漏斗的杆子左半截下面滴水的口子准备滴水了**/
        float ldgzsdlX = ldgzdsX;
        float ldgzsdlY = ldgzdsY + gzWidth + strokeWidth;
        Path ldsd = new Path();
        ldsd.moveTo(ldgzsdlX + gzWidth / 4 - strokeWidth / 2, ldgzsdlY);
        ldsd.lineTo(ldgzsdlX - strokeWidth / 2, ldgzsdlY + gzWidth);

        ldsd.moveTo(ldgzsdlX + gzWidth / 2 - strokeWidth / 2, ldgzsdlY);
        ldsd.lineTo(ldgzsdlX + gzWidth / 2 - strokeWidth / 2, ldgzsdlY + gzWidth);

        ldsd.moveTo(ldgzsdlX + gzWidth / 4 * 3 - strokeWidth / 2, ldgzsdlY);
        ldsd.lineTo(ldgzsdlX + gzWidth - strokeWidth / 2, ldgzsdlY + gzWidth);
//        canvas.drawPath(ldsd, paint);

        ldsd.moveTo(ldgzsdlX - strokeWidth / 2, ldgzsdlY + gzWidth + strokeWidth);
        ldsd.lineTo(ldgzsdlX - strokeWidth / 2 - strokeWidth, ldgzsdlY + 2 * gzWidth + strokeWidth);

//        ldsd.moveTo(ldgzsdlX + gzWidth / 2 - strokeWidth / 2, ldgzsdlY + gzWidth);
//        ldsd.lineTo(ldgzsdlX + gzWidth / 2 - strokeWidth / 2 - strokeWidth / 2, ldgzsdlY + gzWidth);
//
//        ldsd.moveTo(ldgzsdlX + gzWidth - strokeWidth / 2, ldgzsdlY + gzWidth);
//        ldsd.lineTo(ldgzsdlX + gzWidth - strokeWidth / 2 - strokeWidth / 2, ldgzsdlY + gzWidth);
        canvas.drawPath(ldsd, paint);
        ldsd.close();

//        canvas.drawRect(new RectF(ldgzdsX + gzWidth / 4, ldgzdsY + gzWidth + gzWidth / 2, ldgzdsX + gzWidth / 4 * 3, ldgzdsY + gzWidth + gzWidth), paint);

        /**插入漏斗的杆子右半截**/
        float ldgzX1 = left + ldRect.width() / 3 * 2;
        canvas.drawRect(new RectF(ldgzX1, ldgzY, ldgzX1 + ldRect.width() / 3, ldgzY + gzWidth), paint);
        /**插入漏斗的杆子右半截下面滴水的口子**/
        float ldgzdsX1 = left + ldRect.width() / 3 * 2 + ldRect.width() / 3 / 2 - gzWidth / 2;
        float ldgzdsY1 = ldgzY + gzWidth;
        canvas.drawRect(new RectF(ldgzdsX1, ldgzdsY1, ldgzdsX1 + gzWidth, ldgzdsY1 + gzWidth), paint);
        /**插入漏斗的杆子右半截下面滴水的口子准备滴水了**/
        float ldgzsdlX1 = ldgzdsX1;
        float ldgzsdlY1 = ldgzdsY1 + gzWidth + strokeWidth;
        Path ldsd1 = new Path();
        ldsd1.moveTo(ldgzsdlX1 + gzWidth / 4 - strokeWidth / 2, ldgzsdlY1);
        ldsd1.lineTo(ldgzsdlX1 - strokeWidth / 2, ldgzsdlY1 + gzWidth);

        ldsd1.moveTo(ldgzsdlX1 + gzWidth / 2 - strokeWidth / 2, ldgzsdlY1);
        ldsd1.lineTo(ldgzsdlX1 + gzWidth / 2 - strokeWidth / 2, ldgzsdlY1 + gzWidth);

        ldsd1.moveTo(ldgzsdlX1 + gzWidth / 4 * 3 - strokeWidth / 2, ldgzsdlY1);
        ldsd1.lineTo(ldgzsdlX1 + gzWidth - strokeWidth / 2, ldgzsdlY1 + gzWidth);
        canvas.drawPath(ldsd1, paint);
    }
}
