package com.example.healthyman.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyman.R
import com.example.healthyman.adapters.HomeAdapter
import com.example.healthyman.viewModel.HomeViewModel
import com.example.stress.modal.HomeIssueList
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private var fullList: List<HomeIssueList> = listOf() // Original full list of disorders

    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekBar = findViewById<SeekBar>(R.id.customSeekBar)
        val sliderValue = findViewById<TextView>(R.id.sliderValue)
        val quarterLabel = findViewById<TextView>(R.id.quarterLabel)
        val layout = findViewById<LinearLayout>(R.id.slider_main) // Root layout for color changes


        // Get the time-based value
        val timeBasedValue = getTimeBasedSliderValue()

        // Set SeekBar progress based on time
        seekBar.progress = timeBasedValue
//        sliderValue.text = "Value: $timeBasedValue"



        when (timeBasedValue) {
            25 -> {
                quarterLabel.text = "Day Time:- Morning"
                sliderValue.text = "MOOD :- Depressed"

                seekBar.setBackgroundColor(Color.CYAN)
            }

            50 -> {
                quarterLabel.text = "Day Time:- Afternoon"
                sliderValue.text = "MOOD :- Anxiety"

                seekBar.setBackgroundColor(Color.YELLOW)
            }

            75 -> {
                quarterLabel.text = "Day Time:- Evening"
                sliderValue.text = "MOOD :- Happy"

                seekBar.setBackgroundColor(Color.GREEN)
            }

            100 -> {
                quarterLabel.text = "Day Time:- Night"
                sliderValue.text = "MOOD :- Anger"

                seekBar.setBackgroundColor(Color.GRAY)
            }
        }



        val recyclerView = findViewById<RecyclerView>(R.id.home_rv)
        val pgbar = findViewById<ProgressBar>(R.id.idProgressBar)

        recyclerView.layoutManager = GridLayoutManager(this,2)



        homeAdapter = HomeAdapter(listOf()) { item ->
            // On click navigate to StressDetailActivity
            val intent = Intent(this, DetailGraph::class.java)
            intent.putExtra("issueName", item.name)
            startActivity(intent)
        }
        recyclerView.adapter = homeAdapter



        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        viewModel.homeList.observe(this) { homeList ->
            fullList = homeList
            pgbar.visibility=View.INVISIBLE
            homeAdapter.updateList(fullList)
//            homeAdapter = HomeAdapter(homeList) { list ->
//
//                // On click navigate to StressDetailActivity
//                val intent = Intent(this, DetailGraph::class.java)
//                intent.putExtra("issueName", list.name)
//                startActivity(intent)
//            }
//            recyclerView.adapter = homeAdapter
        }
        // Observe changes in the SearchView
        val searchView = findViewById<SearchView>(R.id.idSearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // No action needed on submit
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = fullList.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
                homeAdapter.updateList(filteredList) // Update adapter with filtered list
                return true
            }
        })

        viewModel.fetchDisorders() // Fetch data


    }

    private fun getTimeBasedSliderValue(): Int {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 6..11 -> 25  // Morning (6 AM - 12 PM)
            in 12..17 -> 50  // Afternoon (12 PM - 6 PM)
            in 18..20 -> 75  // Evening (6 PM - 9 PM)
            else -> 100  // Night (9 PM - 6 AM)
        }
    }


}