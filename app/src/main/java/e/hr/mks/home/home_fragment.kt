package e.hr.mks.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import e.hr.mks.R
import e.hr.mks.databinding.FragmentHomeFragmentBinding


class home_fragment : Fragment() {

    private lateinit var binding: FragmentHomeFragmentBinding
    private lateinit var chartView: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =  FragmentHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartView = view.findViewById(R.id.my_pie_chart)
        setUpPieChart()
    }



    private fun setUpPieChart() {
        chartView.setUsePercentValues(false)
        chartView.centerText = "Total 100"
        chartView.description.text = "Your Progress"
        chartView.setDrawEntryLabels(false)
        setChartData()
    }

    private fun setChartData() {
        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(25f , "approved"))
        entries.add(PieEntry(75f , "rejected"))

        val dataSet = PieDataSet(entries, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors = ArrayList<Int>()


        colors.add(Color.rgb(55, 146, 55))
        colors.add(Color.rgb(255,0,0))

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chartView.data = data

        chartView.invalidate()
    }
}