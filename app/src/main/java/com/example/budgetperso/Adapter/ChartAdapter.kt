package com.example.budgetperso.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetperso.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

/*class ChartAdapter(private val pieEntries: List<PieEntry>) : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_home2, parent, false)
        return ChartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        val entry = pieEntries[position]
        holder.bindData(entry)
    }

    override fun getItemCount(): Int {
        return pieEntries.size
    }

    inner class ChartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pieChart: PieChart = itemView.findViewById(R.id.chartpie)

        fun bindData(pieEntry: PieEntry) {
            val dataSet = PieDataSet(listOf(pieEntry), "")
            dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

            val data = PieData(dataSet)
            pieChart.data = data
            pieChart.description.isEnabled = false
            pieChart.legend.isEnabled = false
            pieChart.setDrawEntryLabels(false)
            pieChart.animateY(1000)
            pieChart.invalidate()
        }
    }
}
*/

