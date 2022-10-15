package com.example.cryptoone

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.cryptoone.model.details.CoinDetail
//import com.example.cryptoone.viewModels.coinDetail.CoinDetailViewModel
//import com.example.cryptoone.viewModels.coinDetail.CoinDetailViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.*


class ChartActivity : AppCompatActivity() {

    val tag = "CHART_ACTIVITY"

//    private lateinit var coinViewModel: CoinDetailViewModel

    private var coinDetailList: ArrayList<CoinDetail> = ArrayList()


    var sparkLine: ArrayList<Entry> = ArrayList()


    private lateinit var tvPrice: TextView
    private lateinit var iconImage: ImageView
    private lateinit var coinName: TextView
    private lateinit var coinNameSub: TextView
    private lateinit var coinPrice: TextView
    private lateinit var coinPercentage: TextView


    private lateinit var paint1 : Paint


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        val selectedId = intent.extras?.getString("id")

        iconImage =findViewById(R.id.iconImage)
        coinName = findViewById(R.id.txtCoinName)
        coinNameSub = findViewById(R.id.txtId)
        coinPrice = findViewById(R.id.txtPrice)
        coinPercentage = findViewById(R.id.txtPer)


        val customMarkerView =
            CustomMarker(context = applicationContext, R.layout.custom_marker_view)

        val chart = findViewById<LineChart>(R.id.chart).apply {
            setBackgroundColor(Color.WHITE)
            setDrawBorders(true)
            description.isEnabled = true
            setPinchZoom(false)
            setDrawMarkers(true)
            marker = customMarkerView
            setDrawBorders(false)
        }

        //setup gradient
        paint1 = chart.renderer.paintRender
        val height = chart.height

        val linGrad = LinearGradient(0f, 0f, 0f, height.toFloat(),
            Color.YELLOW, Color.RED, Shader.TileMode.REPEAT)
        paint1.shader =linGrad



        setUpLineChart(chart)

       // getData(chart, selectedId!!)




    }

    private fun setUpLineChart(lineChart: LineChart) {
        with(lineChart) {

            axisRight.isEnabled = false
            animateX(1200, Easing.EaseInSine)

            description.isEnabled = true

            xAxis.position = XAxis.XAxisPosition.BOTTOM
//            xAxis.valueFormatter = MyAxisFormatter()
//            xAxis.granularity = 1F
            xAxis.setDrawGridLines(true)
            xAxis.setDrawAxisLine(true)
            axisLeft.setDrawGridLines(true)
//            extraRightOffset = 30f


//            legend.isEnabled = false
//            legend.orientation = Legend.LegendOrientation.VERTICAL
//            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//            legend.form = Legend.LegendForm.CIRCLE

        }
    }

    private fun setDataToLineChart(lineChart: LineChart, test2: List<Double>) {

        val weekOneSales = LineDataSet(dataChart(test2), "Week 1").apply {

            lineWidth = 1f
            valueTextSize = 5f

            mode = LineDataSet.Mode.CUBIC_BEZIER

            color = ContextCompat.getColor(applicationContext, R.color.purple_500)

            setDrawCircleHole(false)
            setDrawCircles(false)

            setDrawFilled(true)


//            fillFormatter = object : DefaultFillFormatter() {
//                override fun getFillLinePosition(
//                    dataSet: ILineDataSet?,
//                    dataProvider: LineDataProvider?
//                ): Float {
//                    return super.getFillLinePosition(dataSet, dataProvider)
//                }
//            }
        }


        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(weekOneSales)
//        dataSet.add(weekTwoSales)


        val lineData = LineData(dataSet)

        lineChart.data = lineData
        lineChart.description.text = "Days"
        lineChart.setNoDataText("No forex yet!")
        lineChart.animateX(1000, Easing.EaseInExpo)



        lineChart.invalidate()
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


   /* private fun getData(lineChart: LineChart, selectedId: String) {

        val api = RetrofitHelper.getInstance().create(CryptoApi::class.java)

        val repository = CryptoRepository(api)

        coinViewModel = ViewModelProvider(
            this,
            CoinDetailViewModelFactory(repository, id = selectedId, currency = "usd")
        )[CoinDetailViewModel::class.java]


        coinViewModel.getCoinDetailRepository().observe(this) {

            val test = it[0].sparkline_in_7d
            val test2 = test.price

            val df = "$ ${it[0].currentPrice}"

            val percentage = it[0].priceChangePercentage24h

//            this.tvPrice.text = df
            this.coinName.text = "${it[0].name}"
            this.coinPrice.text = df
            this.coinPercentage.text = it[0].priceChangePercentage24h

            if (percentage != null) {
                if(percentage.toDouble()<0.0){
                    //negative
                    this.coinPercentage.setTextColor(Color.RED)
                }else if (percentage.toDouble()>0.0){
                    //positive
                    this.coinPercentage.setTextColor(Color.GREEN)
                }
            }

            this.coinNameSub.text = it[0].symbol
            Picasso.get()
                .load(it[0].image)
                .into(this.iconImage);

            setDataToLineChart(lineChart = lineChart, test2)


        }


    }

*/
}