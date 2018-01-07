package com.zed.example.presenter

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import com.bytc.qudong.control.fragment.UIHomeConstraint
import com.facebook.rebound.SimpleSpringListener
import com.facebook.rebound.Spring
import com.facebook.rebound.SpringChain
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.zed.example.adapter.HomeTypeAdapter
import com.zed.example.base.BasePresenter
import com.zed.example.net.bean.HomeBean
import com.zed.image.round.CornerType
import java.util.*

/**
 * @author zd
 * @package com.bytc.qudong.presenter
 * @fileName HomePresenter
 * @date on 2017/12/1 0001 16:10
 * @org 湖北博娱天成科技有限公司
 * @describe TODO
 * @email 1053834336@qq.com
 */
class HomePresenter(view: UIHomeConstraint) : BasePresenter<UIHomeConstraint, HomeBean>(view) {

    var homeTopHolder: HomeTypeAdapter? = null
    var homeTypeList: MutableList<HomeBean>? = null
    var springs: List<Spring>? = null
    override fun init() {
        initRlTop()
        getView()?.getSmartPull()?.setOnLoadmoreListener(this)
        getView()?.getSmartPull()?.setOnRefreshListener(this)
        homeIndex()
    }

    //精选
    private fun initRlTop() {
        homeTypeList = arrayListOf()
        homeTopHolder = homeTopHolder ?: HomeTypeAdapter(mActivity, homeTypeList)
        getView()?.getRlView()?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        getView()?.getRlView()?.adapter = homeTopHolder
    }

    override fun onLoadmore(refresh: RefreshLayout?) {
        super.onLoadmore(refresh)
        /** 固定的header1 刷新除了header之外的item数据 **/
        homeTopHolder?.notifyItemRangeChanged(getView()?.getRlView()?.headerViewSize!!.plus(homeTypeList!!.size), homeTypeList!!.size)
        refresh?.finishLoadmore()
    }

    override fun onRefresh(refresh: RefreshLayout?) {
        super.onRefresh(refresh)
        homeIndex()
    }

    private fun homeIndex() {
        homeTypeList?.clear()

        val data: MutableList<HomeBean> = arrayListOf()
        data.add(HomeBean(CornerType.ALL, getImg()))
        data.add(HomeBean(CornerType.TOP, getImg()))
        data.add(HomeBean(CornerType.BOTTOM, getImg()))
        data.add(HomeBean(CornerType.LEFT, getImg()))
        data.add(HomeBean(CornerType.RIGHT, getImg()))

        data.add(HomeBean(CornerType.LEFT_TOP, getImg()))
        data.add(HomeBean(CornerType.LEFT_BOTTOM, getImg()))

        data.add(HomeBean(CornerType.RIGHT_BOTTOM, getImg()))
        data.add(HomeBean(CornerType.RIGHT_TOP, getImg()))


        data.add(HomeBean(CornerType.RECTANGLE_LEFT_BOTTOM, getImg()))
        data.add(HomeBean(CornerType.RECTANGLE_LEFT_TOP, getImg()))
        data.add(HomeBean(CornerType.RECTANGLE_RIGHT_BOTTOM, getImg()))
        data.add(HomeBean(CornerType.RECTANGLE_RIGHT_TOP, getImg()))

        data.add(HomeBean(CornerType.NONE, getImg()))

        isClearOrAddDataByPullState(homeTypeList, data, getView()?.getRlView(), getView()?.getSmartPull(), homeTopHolder)
    }

    fun getImg(): String {
        val img = arrayOf(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515937631&di=e8f670019c206ddc4b49d99619849713&imgtype=jpg&er=1&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201410%2F26%2F20141026102230_5wyKw.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515342991499&di=fd882734c9f740f18fc6f9ee03217221&imgtype=0&src=http%3A%2F%2Fwww.yaojingweiba.com%2Fuploads%2Fallimg%2F120808%2F1-120PQUZ3.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515346107727&di=47e38ce9e99238f2208add0c38e41062&imgtype=0&src=http%3A%2F%2Ffd.topitme.com%2Fd%2F8c%2F3e%2F111386953141e3e8cdo.jpg"
        )
        return img[Random().nextInt(2)]
    }

    //显示技能动画
    fun showAnimation(body: ViewGroup) {
        val tag = body.tag
        val childCount = body.childCount
        val springChain = SpringChain.create()
        val currentValue: Double
        val endValue: Double
        (0 until childCount)
                .map { body.getChildAt(it) }
                .forEach {
                    springChain.addSpring(object : SimpleSpringListener() {
                        override fun onSpringUpdate(spring: Spring?) {
                            it.translationY = spring!!.currentValue.toFloat()
                            when (tag) {
                                null, "HIDE" -> {
                                    it.visibility = View.VISIBLE
                                    if (spring.currentValue == 0.0)
                                        spring.destroy()
                                }
                                else -> {
                                    if (spring.currentValue == body.measuredHeight.toDouble()) {
                                        it.visibility = View.INVISIBLE
                                        spring.destroy()
                                    }
                                }
                            }
                        }
                    })
                }
        when (tag) {
            null, "HIDE" -> {
                endValue = 0.0
                currentValue = body.measuredHeight.toDouble()
                body.tag = "SHOW"
            }
            else -> {
                endValue = body.measuredHeight.toDouble()
                currentValue = 0.0
                body.tag = "HIDE"
            }
        }
        springs = springChain.allSprings
        for (i in 0 until springs!!.size) {
            springs?.get(i)?.currentValue = currentValue
        }
        springChain.setControlSpringIndex(0).controlSpring.endValue = endValue
    }

}