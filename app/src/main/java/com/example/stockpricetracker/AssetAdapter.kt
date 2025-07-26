package com.example.stockpricetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AssetAdapter : RecyclerView.Adapter<AssetAdapter.AssetViewHolder>() {

    private val stockList = mutableListOf<StockEntryDisplay>()

    fun submitList(list: List<StockEntryDisplay>) {
        stockList.clear()
        stockList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asset, parent, false)
        return AssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.bind(stockList[position])
    }

    override fun getItemCount(): Int = stockList.size

    class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val symbol: TextView = itemView.findViewById(R.id.textSymbol)
        private val date: TextView = itemView.findViewById(R.id.textDate)
        private val open: TextView = itemView.findViewById(R.id.textOpen)
        private val close: TextView = itemView.findViewById(R.id.textClose)
        private val volume: TextView = itemView.findViewById(R.id.textVolume)
        private val high: TextView = itemView.findViewById(R.id.textHigh)
        private val low: TextView = itemView.findViewById(R.id.textLow)

        fun bind(data: StockEntryDisplay) {
            symbol.text = data.symbol
            date.text = "Date : %s".format(data.date)
            open.text = "$%.2f (Open Price)".format(data.open)
            close.text = "$%.2f(Close Price)".format(data.close)
            volume.text = "%.1fK Vol".format(data.volume / 1000)
            high.text = "Highest: $%.2f".format(data.high)
            low.text = "Lowest: $%.2f".format(data.low)
        }
    }
}
