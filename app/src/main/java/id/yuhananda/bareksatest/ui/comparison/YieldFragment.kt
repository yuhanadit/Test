package id.yuhananda.bareksatest.ui.comparison

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import id.yuhananda.bareksatest.R
import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.databinding.FragmentYieldBinding
import id.yuhananda.bareksatest.util.DummyDataFactory
import kotlin.math.roundToInt

@AndroidEntryPoint
class YieldFragment : Fragment(), ProductAdapter.ItemProductListener,
    TabLayout.OnTabSelectedListener {

    companion object {
        val months = arrayOf(
            "Jan 20",
            "Feb 20",
            "Mar 20",
            "Apr 20",
            "May 20",
            "Jun 20",
            "Jul 20",
            "Aug 20",
            "Sep 20",
            "Okt 20",
            "Nov 20",
            "Dec 20"
        )
    }

    private lateinit var viewModel: YieldViewModel
    private lateinit var binding: FragmentYieldBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYieldBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(YieldViewModel::class.java)

        setupTabLayout()
        setupSwipeRefreshLayout()
        setupLineChart()
        setupObserver()

        viewModel.getComparedProductsData()
    }

    private fun setupTabLayout() {
        binding.componentLineChart.tabLayout.addOnTabSelectedListener(this)
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getComparedProductsData()
        }
    }

    private fun setupLineChart() {
        binding.componentLineChart.lineChart.setBackgroundColor(Color.WHITE)
        binding.componentLineChart.lineChart.description.isEnabled = false
        binding.componentLineChart.lineChart.setTouchEnabled(true)
        binding.componentLineChart.lineChart.setDrawGridBackground(false)
        binding.componentLineChart.lineChart.isDragEnabled = false
        binding.componentLineChart.lineChart.setScaleEnabled(false)
        binding.componentLineChart.lineChart.setPinchZoom(false)
        binding.componentLineChart.lineChart.isHighlightPerDragEnabled = false
        binding.componentLineChart.lineChart.setOnChartValueSelectedListener(object :
            OnChartValueSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                /*binding.componentLineChart.textViewProduct1.text = "${e?.y.toString()}%"
                binding.componentLineChart.textViewProduct2.text = "${e?.y.toString()}%"
                binding.componentLineChart.textViewProduct3.text = "${e?.y.toString()}%"*/

                val data1 =
                    binding.componentLineChart.lineChart.lineData.dataSets[0].getEntryForXValue(
                        e?.x ?: 0f,0f
                    )
                binding.componentLineChart.textViewProduct1.text = "${data1.y.roundToInt()}%"
//                binding.componentLineChart.textViewProduct1.text = "${String.format("%.2f", data1.y).toDouble()}%"

                val data2 =
                    binding.componentLineChart.lineChart.lineData.dataSets[1].getEntryForXValue(
                        e?.x ?: 0F,0f
                    )
                binding.componentLineChart.textViewProduct2.text = "${data2.y.roundToInt()}%"
//                binding.componentLineChart.textViewProduct2.text = "${String.format("%.2f", data2.y).toDouble()}%"

                val data3 =
                    binding.componentLineChart.lineChart.lineData.dataSets[2].getEntryForXValue(
                        e?.x ?: 0F,0f
                    )
                binding.componentLineChart.textViewProduct3.text = "${data3.y.roundToInt()}%"
//                binding.componentLineChart.textViewProduct3.text = "${String.format("%.2f", data3.y).toDouble()}%"

                binding.componentLineChart.textViewDate.text =
                    "(20 ${months[e?.x?.toInt() ?: 0 % months.size]})"
            }

            override fun onNothingSelected() {}
        })

        val legend: Legend = binding.componentLineChart.lineChart.legend
        legend.form = LegendForm.NONE
        legend.textColor = Color.TRANSPARENT

        val xAxis: XAxis = binding.componentLineChart.lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor =
            ContextCompat.getColor(requireActivity(), R.color.onsurface_mediumemphasis)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                return months[value.toInt() % months.size]
            }

            override fun getFormattedValue(value: Float): String {
                return months[value.toInt() % months.size]
            }
        }

        val leftAxis: YAxis = binding.componentLineChart.lineChart.axisLeft
        leftAxis.isEnabled = false

        val rightAxis: YAxis = binding.componentLineChart.lineChart.axisRight
        rightAxis.textColor =
            ContextCompat.getColor(requireActivity(), R.color.onsurface_mediumemphasis)
        rightAxis.setDrawGridLines(true)
        rightAxis.setDrawZeroLine(true)
        rightAxis.zeroLineWidth = 2f
        rightAxis.gridLineWidth = 1f
        rightAxis.valueFormatter = PercentFormatter()
    }

    private fun setupObserver() {
        viewModel.dataLoading.observe(viewLifecycleOwner) { dataLoading ->
            binding.swipeRefreshLayout.isRefreshing = dataLoading
        }

        viewModel.products.observe(viewLifecycleOwner) { productList ->
            setupChartData(productList)
            setupProductsData(productList)
        }
    }

    private fun setupChartData(productList: List<Product>?) {
        if (productList != null && productList.isNotEmpty()) {
            val tabText =
                binding.componentLineChart.tabLayout.getTabAt(binding.componentLineChart.tabLayout.selectedTabPosition)?.text
            showChartFromSelectedChart(tabText)
        }
    }

    private fun setupProductsData(productList: List<Product>?) {
        binding.recyclerView.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerView.adapter =
            ProductAdapter(productList ?: arrayListOf(), requireActivity(), this)
    }

    override fun onProductButtonClick(view: View?, product: Product?) {
        if (view != null && product != null) {
            if (view.id == R.id.buttonDetail) {
                Log.d("YieldFragment", "OnClick Detail ${product.name}")
            } else if (view.id == R.id.buttonBuy) {
                Log.d("YieldFragment", "OnClick Beli ${product.name}")
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        viewModel.products.value?.let {
            showChartFromSelectedChart(tab?.text)
        }
    }

    private fun showChartFromSelectedChart(tabText: CharSequence?) {
        tabText?.let { text ->
            viewModel.products.value?.let { productList ->
                val dataSetList = ArrayList<LineDataSet>()
                if (productList.size >= 3) {
                    val values1 = getDataByTimeFrame(text)

                    val dataSet1 = LineDataSet(values1, productList[0].name)
                    dataSet1.axisDependency = AxisDependency.LEFT
                    dataSet1.color = ContextCompat.getColor(requireActivity(), R.color.green_50)
                    dataSet1.setCircleColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.green_50
                        )
                    )
                    /*dataSet1.lineWidth = 3f
                    dataSet1.circleRadius = 3f
                    dataSet1.fillAlpha = 65*/
                    dataSet1.fillColor = ContextCompat.getColor(requireActivity(), R.color.green_50)
                    dataSet1.highLightColor = Color.rgb(244, 117, 117)
                    dataSet1.setDrawCircleHole(true)
                    dataSet1.setDrawHorizontalHighlightIndicator(true)
                    dataSet1.circleHoleColor =
                        ContextCompat.getColor(requireActivity(), R.color.green_50)
                    dataSet1.valueTextColor = Color.TRANSPARENT
                    dataSet1.circleRadius = 5f
                    dataSetList.add(dataSet1)


                    val values2 = getDataByTimeFrame(text)
                    val dataSet2 = LineDataSet(values2, productList[1].name)
                    dataSet2.axisDependency = AxisDependency.LEFT
                    dataSet2.color = ContextCompat.getColor(requireActivity(), R.color.violet_50)
                    dataSet2.setCircleColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.violet_50
                        )
                    )
                    /*dataSet2.lineWidth = 3f
                    dataSet2.circleRadius = 3f
                    dataSet2.fillAlpha = 65*/
                    dataSet2.fillColor =
                        ContextCompat.getColor(requireActivity(), R.color.violet_50)
                    dataSet2.highLightColor = Color.rgb(244, 117, 117)
                    dataSet2.setDrawCircleHole(true)
                    dataSet2.setDrawHorizontalHighlightIndicator(true)
                    dataSet2.circleHoleColor =
                        ContextCompat.getColor(requireActivity(), R.color.violet_50)
                    dataSet2.valueTextColor = Color.TRANSPARENT
                    dataSet2.circleRadius = 5f
                    dataSetList.add(dataSet2)

                    val values3 = getDataByTimeFrame(text)
                    val dataSet3 = LineDataSet(values3, productList[2].name)
                    dataSet3.axisDependency = AxisDependency.LEFT
                    dataSet3.color = ContextCompat.getColor(requireActivity(), R.color.navy_50)
                    dataSet3.setCircleColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.navy_50
                        )
                    )
                    /*dataSet3.lineWidth = 3f
                    dataSet3.circleRadius = 3f
                    dataSet3.fillAlpha = 65*/
                    dataSet3.fillColor = ContextCompat.getColor(requireActivity(), R.color.navy_50)
                    dataSet3.highLightColor = Color.rgb(244, 117, 117)
                    dataSet3.setDrawCircleHole(true)
                    dataSet3.setDrawHorizontalHighlightIndicator(true)
                    dataSet3.circleHoleColor =
                        ContextCompat.getColor(requireActivity(), R.color.navy_50)
                    dataSet3.valueTextColor = Color.TRANSPARENT
                    dataSet3.circleRadius = 5f
                    dataSetList.add(dataSet3)
                }
                val lineData = LineData(dataSetList as List<ILineDataSet>?)
                binding.componentLineChart.lineChart.data = lineData
                binding.componentLineChart.lineChart.invalidate()

                binding.componentLineChart.lineChart.visibility = View.VISIBLE
                binding.componentLineChart.cardViewInfo.visibility = View.VISIBLE
                binding.componentLineChart.tabLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun getDataByTimeFrame(text: CharSequence): MutableList<Entry> {
        when (text) {
            "1W" -> {
                return DummyDataFactory.get1WDummyData()
            }
            "1M" -> {
                return DummyDataFactory.get1MDummyData()
            }
            "1Y" -> {
                return DummyDataFactory.get1YDummyData()
            }
            "3Y" -> {
                return DummyDataFactory.get3YDummyData()
            }
            "5Y" -> {
                return DummyDataFactory.get5YDummyData()
            }
            "10Y" -> {
                return DummyDataFactory.get10YDummyData()
            }
            else -> {
                return DummyDataFactory.getAllDummyData()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
}