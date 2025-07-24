package com.example.stockpricetracker

import android.view.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockpricetracker.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.graphics.Color
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AssetAdapter
    private var timeSeries: Map<String, Map<String, String>> = emptyMap()

    private var currentSymbol = "TCS.BSE" // ✅ default symbol

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Setup RecyclerView
        adapter = AssetAdapter()
        binding.assetsRecycler.layoutManager = LinearLayoutManager(this)
        binding.assetsRecycler.adapter = adapter
        binding.assetsRecycler.setHasFixedSize(true)
        binding.assetsRecycler.isNestedScrollingEnabled = true
        binding.assetsRecycler.overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS

        // ✅ Load default stock on startup
        fetchStockData(currentSymbol)

        // ✅ SearchView listener
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    currentSymbol = it.trim().uppercase()  // 👈 update symbol
                    fetchStockData(currentSymbol)         // 👈 fetch new data
                    binding.searchView.clearFocus()       // 👈 hide keyboard
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        // ✅ Tabs for daily/weekly/monthly
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (timeSeries.isEmpty()) return
                when (tab?.position) {
                    0 -> adapter.submitList(extractDailyStockEntries(currentSymbol, timeSeries))
                    1 -> adapter.submitList(extractWeeklyStockEntries(currentSymbol, timeSeries))
                    2 -> adapter.submitList(extractMonthlyStockEntries(currentSymbol, timeSeries))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun fetchStockData(symbol: String) {
        val apiKey = "I9AI0R24KGY1Z0U6"
        val call = RetrofitInstance.api.getDailyStock(symbol, "full", apiKey)

        call.enqueue(object : Callback<AlphaVantageResponse> {
            override fun onResponse(call: Call<AlphaVantageResponse>, response: Response<AlphaVantageResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        timeSeries = body.timeSeries
                        adapter.submitList(extractDailyStockEntries(symbol, timeSeries))
                        binding.textStockName.text = "Stock: $symbol"
                        binding.tabLayout.getTabAt(0)?.select()
                    } else {
                        Toast.makeText(this@MainActivity, "No data received.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "API Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AlphaVantageResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}

