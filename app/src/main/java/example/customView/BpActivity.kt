package example.customView

import android.os.Bundle
import com.style.base.BaseTitleBarActivity
import com.style.framework.R
import com.style.framework.databinding.ActivityBpBinding
import com.style.view.healthy.BloodPressureLine.BloodItem
import java.util.*

class BpActivity : BaseTitleBarActivity() {

    lateinit var bd: ActivityBpBinding
    internal var max: Float = 0.toFloat()
    internal var min: Float = 0.toFloat()

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.activity_bp)
        setTitleBarTitle("血压曲线图")
        bd = getBinding()
        bd.btnRefresh.setOnClickListener { v -> refresh() }
    }

    fun refresh() {
        bd.bpLineBg.setData(getData())
        bd.bpLine.setData(getData())
    }

    private fun getData(): List<BloodItem> {
        val list = ArrayList<BloodItem>(100)
        val random = Random()
        for (i in 0..99) {
            val b = BloodItem(random.nextInt(30) + 50, random.nextInt(30) + 90, "00:00")
            list.add(b)
        }
        return list
    }

}
