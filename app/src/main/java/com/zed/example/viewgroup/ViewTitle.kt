package com.zed.example.viewgroup

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.zed.common.util.UiUtil
import com.zed.example.R
import com.zed.example.control.activity.UIActivityConstraint
import com.zed.example.control.viewgroup.UIViewConstraint
import com.zed.example.net.model.BaseModel
import kotlinx.android.synthetic.main.view_title.view.*

/**
 * @author zd
 * @package com.zed.example.viewgroup
 * @fileName ViewTitle
 * @date on 2017/11/29 0029 17:23
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class ViewTitle : FrameLayout, OnClickListener, UIViewConstraint<BaseModel> {
    override fun updateUI(data: BaseModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //    UIViewConstraint
    var uiTitleController: UIActivityConstraint? = null

    constructor(context: Context) : super(context) {
        setLayoutId()
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        setLayoutId()
    }

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(context, attributes, defStyleAttr) {
        setLayoutId()
    }

    override fun setLayoutId() {
        UiUtil.inflate(context, R.layout.view_title, this, true)
        initView()
    }

    override fun initView() {
        rlBar.visibility = View.GONE
        tvBack.visibility = View.INVISIBLE
        tvTitle.visibility = View.INVISIBLE
        ivTitleIcon.visibility = View.INVISIBLE
        tvRightOption.visibility = View.GONE

        tvBack.setOnClickListener(this)
        tvRightOption.setOnClickListener(this)
        tvTitle.setOnClickListener(this)
        ivTitleIcon.setOnClickListener(this)
    }

    fun setTitle() {
        setTitle(null, -1)
    }

    fun setTitle(txt: String) {
        setTitle(txt, -1)
    }

    fun setTitle(txtId: Int) {
        setTitle(resources.getString(txtId), -1)
    }


    fun setTitle(txtId: Int, drawable: Int) {
        setTitle(resources.getString(txtId), drawable)
    }

    //右边按钮的文本 drawable -1 代表不加图案
    fun setTitle(txt: String?, drawable: Int) {
        rlBar.visibility = View.VISIBLE
        tvTitle.visibility = View.VISIBLE
        tvTitle.text = txt
        if (drawable > -1) {
            ivTitleIcon.visibility = View.VISIBLE
            ivTitleIcon.setImageResource(drawable)
        }
    }

    fun setTitle(txt: String, drawable: Int, textColor: Int) {
        rlBar.visibility = View.VISIBLE
        tvTitle.visibility = View.VISIBLE
        tvTitle.text = txt
        tvTitle.setTextColor(textColor)
        if (drawable > -1) {
            ivTitleIcon.visibility = View.VISIBLE
            ivTitleIcon.setImageResource(drawable)
        }
    }

    fun setTitleLeftTxt() {
        setTitleLeftTxt(null, -1)
    }

    //左边按钮 = 返回
    fun setTitleLeftTxt(text: String) {
        setTitleLeftTxt(text, -1)
    }

    fun setTitleLeftTxt(txtId: Int) {
        setTitleLeftTxt(resources.getString(txtId), -1)
    }

    fun setTitleLeftTxt(txtId: Int, drawable: Int) {
        setTitleLeftTxt(resources.getString(txtId), drawable)
    }

    fun setTitleLeftTxt(text: String?, drawable: Int) {
        rlBar.visibility = View.VISIBLE
        tvBack.visibility = View.VISIBLE
        tvBack.text = text
        if (drawable > -1)
            tvBack.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, drawable),
                    null, null, null)
    }

    fun setTitleRightTxt() {
        setTitleRightTxt(null, -1)
    }

    //标题右边按钮
    fun setTitleRightTxt(text: String) {
        setTitleRightTxt(text, -1)
    }

    fun setTitleRightTxt(textId: Int) {
        setTitleRightTxt(resources.getString(textId), -1)
    }

    fun setTitleRightTxt(textId: Int, drawable: Int) {
        setTitleRightTxt(resources.getString(textId), drawable)
    }

    fun setTitleRightTxt(text: String?, drawable: Int) {
        rlBar.visibility = View.VISIBLE
        tvRightOption.visibility = View.VISIBLE
        tvRightOption.text = text
        if (drawable > -1)
            tvRightOption.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, drawable),
                    null, null, null)
    }

    fun getTvBack(): TextView {
        return tvBack
    }

    fun getTvTitle(): TextView {
        return tvTitle
    }

    fun getIvTitleIcon(): ImageView {
        return ivTitleIcon
    }

    fun getTvRightOption(): TextView {
        return tvRightOption
    }

    fun showButtonLine(bool: Boolean) {
        vTitleLine.visibility = if (bool) View.VISIBLE else View.GONE
    }

    fun setButtonLineColor(color: Int) {
        vTitleLine.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvBack -> uiTitleController?.clickTitleLeft()
            R.id.tvRightOption -> uiTitleController?.clickTitleRight()
            R.id.tvTitle, R.id.ivTitleIcon -> uiTitleController?.clickTitle()
        }
    }

    override fun onDestory() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}