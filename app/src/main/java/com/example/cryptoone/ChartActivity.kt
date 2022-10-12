package com.example.cryptoone

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoone.api.CryptoApi
import com.example.cryptoone.api.RetrofitHelper
import com.example.cryptoone.model.details.CoinDetail
import com.example.cryptoone.repository.CryptoRepository
import com.example.cryptoone.viewModels.coinDetail.CoinDetailViewModel
import com.example.cryptoone.viewModels.coinDetail.CoinDetailViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class ChartActivity : AppCompatActivity() {

    val tag = "CHART_ACTIVITY"

    private lateinit var coinViewModel: CoinDetailViewModel

    private var coinDetailList: ArrayList<CoinDetail> = ArrayList()


    var sparkLine: ArrayList<Entry> = ArrayList()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        val selectedId = intent.extras?.getString("id")


        val chart = findViewById<LineChart>(R.id.chart)

        setUpLineChart(chart)

        getData(chart, selectedId!!)




    }

    private fun setUpLineChart(lineChart: LineChart) {
        with(lineChart) {

            axisRight.isEnabled = false
            animateX(1200, Easing.EaseInSine)

            description.isEnabled = true

            xAxis.position = XAxis.XAxisPosition.BOTTOM
//            xAxis.valueFormatter = MyAxisFormatter()
            xAxis.granularity = 1F
            xAxis.setDrawGridLines(true)
            xAxis.setDrawAxisLine(true)
            axisLeft.setDrawGridLines(true)
            extraRightOffset = 30f

            legend.isEnabled = true
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.form = Legend.LegendForm.CIRCLE

        }
    }

    private fun setDataToLineChart(lineChart: LineChart, test2: List<Double>) {

        val weekOneSales = LineDataSet(dataChart(test2), "Week 1")
        weekOneSales.lineWidth = 3f
        weekOneSales.valueTextSize = 15f
        weekOneSales.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        weekOneSales.color = ContextCompat.getColor(this, R.color.purple_500)
        weekOneSales.valueTextColor = ContextCompat.getColor(this, R.color.teal_700)
        weekOneSales.setDrawCircleHole(false)
        weekOneSales.setDrawCircles(false)


        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(weekOneSales)
//        dataSet.add(weekTwoSales)

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        lineChart.invalidate()
    }


    private fun week1(): ArrayList<Entry> {
        val sales = ArrayList<Entry>()
        sales.add(Entry(0f, 15f))
        sales.add(Entry(1f, 16f))
        sales.add(Entry(2f, 13f))
        sales.add(Entry(3f, 22f))
        sales.add(Entry(4f, 20f))
        return sales
    }

    private fun dataChart(test2: List<Double>): ArrayList<Entry> {
        val data = ArrayList<Entry>()

        test2.forEachIndexed { index, e ->
            data
                .add(Entry(index.toFloat(), e.toFloat()))
        }

        return data

    }


    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        private var items = arrayListOf("Milk", "Butter", "Cheese", "Ice cream", "Milkshake")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < items.size) {
                items[index]
            } else {
                null
            }
        }
    }


    private fun getData(lineChart: LineChart, selectedId: String) {

        val api = RetrofitHelper.getInstance().create(CryptoApi::class.java)

        val repository = CryptoRepository(api)

        coinViewModel = ViewModelProvider(
            this,
            CoinDetailViewModelFactory(repository, id = selectedId, currency = "usd" )
        )[CoinDetailViewModel::class.java]


        coinViewModel.getCoinDetailRepository().observe(this) {

            val test = it[0].sparkline_in_7d
            val test2= test.price


            setDataToLineChart(lineChart = lineChart, test2)



        }


    }


}