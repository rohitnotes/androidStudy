package example.drag.scroll_stop

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.*
import com.style.base.BaseRecyclerViewAdapter
import com.style.base.activity.BaseActivity
import com.style.framework.R
import com.style.framework.databinding.ActivityScrollingStopTopBinding
import com.style.view.diviver.DividerItemDecoration
import example.home.contact.FriendAdapter
import java.util.*


/**
 * app:layout_scrollFlags，设置为：scroll|enterAlways|snap 便是指定标题栏随屏幕滚动实现的属性。
 * scroll表示屏幕向上滑动时，标题栏同时向上滑动并隐藏；
 * enterAlways表示屏幕向下滑动时，标题栏同时向下活动并显示；
 * snap表示Toolbar没有完全显示或隐藏时，根据滚动距离，自动选择。
 */
class ScrollingStopTopActivity : BaseActivity() {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var bd: ActivityScrollingStopTopBinding
    private lateinit var dataList: ArrayList<String>
    private lateinit var mTitleAdapter: ScrollStopTitleAdapter
    private lateinit var mContentAdapter: ScrollStopContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_stop_top)
        setFullScreenStableDarkMode(false)

        bd = getBinding()
        dataList = ArrayList()
        mTitleAdapter = ScrollStopTitleAdapter(this, dataList)
        bd.recyclerViewTitle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bd.recyclerViewTitle.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
        bd.recyclerViewTitle.adapter = mTitleAdapter
        mTitleAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(position: Int, data: String) {
                showToast(position.toString() + "")
                //bd.recyclerView.scrollToPosition(position)
                layoutManager.scrollToPositionWithOffset(position, 0)
                if (position%2==0)
                    setFullScreenStableDarkMode(true)
                else
                    setFullScreenStableDarkMode(false)
            }
        })
        for (i in 0 until 10) {
            dataList.add("$i")
        }
        mTitleAdapter.notifyDataSetChanged()

        mContentAdapter = ScrollStopContentAdapter(this, dataList)
        layoutManager = LinearLayoutManager(this)
        bd.recyclerView.layoutManager = layoutManager
        bd.recyclerView.addItemDecoration(DividerItemDecoration(this))
        bd.recyclerView.adapter = mContentAdapter
        mContentAdapter.notifyDataSetChanged()

        //只适用于recyclerView外层没有嵌套滚动布局
        bd.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //获取最后一个可见view的位置
                //val lastItemPosition = layoutManager.findLastVisibleItemPosition()
                //获取第一个可见view的位置,只适用于recyclerView外层没有嵌套嵌套滚动布局才会正确返回
                val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
                logE("firstItemPosition", "$firstItemPosition")
                bd.recyclerViewTitle.scrollToPosition(firstItemPosition)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
}