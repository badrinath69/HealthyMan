package com.example.healthyman.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthyman.R
import com.example.healthyman.Util.DayPartFormatter
import com.example.healthyman.adapters.ExerAdapter
import com.example.healthyman.adapters.FoodAdapter
import com.example.healthyman.viewModel.GraphDetailViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class DetailGraph : AppCompatActivity() {
    private lateinit var viewModel: GraphDetailViewModel
    private lateinit var physicalExerciseAdapter: ExerAdapter
    private lateinit var mentalExerciseAdapter: ExerAdapter
    private lateinit var vegFoodAdapter: FoodAdapter
    private lateinit var nonVegFoodAdapter: FoodAdapter


    private lateinit var moodGraph: BarChart
    private val moodForTime = mapOf(
        "Morning" to "Stress",
        "Afternoon" to "Anxiety Disorder",
        "Evening" to "Personality Disorder",
        "Night" to "Depression"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_graph)
        val disorderName = intent.getStringExtra("issueName") ?: ""

        // Find the views
        val disorderNameTextView = findViewById<TextView>(R.id.graph_name)
        val definitionTextView = findViewById<TextView>(R.id.graph_def)
        val disorderImage = findViewById<ImageView>(R.id.graph_img)
        val physicalExerciseRecyclerView = findViewById<RecyclerView>(R.id.graph_phyexe_rv)
        val mentalExerciseRecyclerView = findViewById<RecyclerView>(R.id.graph_menexe_rv)
        val vegFoodRecyclerView = findViewById<RecyclerView>(R.id.graph_veg)
        val nonVegFoodRecyclerView = findViewById<RecyclerView>(R.id.graph_nonveg)
        val pgbar = findViewById<ProgressBar>(R.id.graph_pb)
        val dummy1 = findViewById<TextView>(R.id.dummy1)
        val dummy101 = findViewById<TextView>(R.id.dummy101)
        val dummy102 = findViewById<TextView>(R.id.dummy102)
        val dummy103 = findViewById<TextView>(R.id.dummy103)
        val dummy104 = findViewById<TextView>(R.id.dummy104)
        val dummy105 = findViewById<TextView>(R.id.dummy105)
        val dummy106 = findViewById<TextView>(R.id.dummy106)






        // Set up the RecyclerViews
        physicalExerciseRecyclerView.layoutManager = LinearLayoutManager(this)
        physicalExerciseRecyclerView.isNestedScrollingEnabled = false
        mentalExerciseRecyclerView.layoutManager = LinearLayoutManager(this)
        mentalExerciseRecyclerView.isNestedScrollingEnabled = false
        vegFoodRecyclerView.layoutManager = LinearLayoutManager(this)
        vegFoodRecyclerView.isNestedScrollingEnabled = false
        nonVegFoodRecyclerView.layoutManager = LinearLayoutManager(this)
        nonVegFoodRecyclerView.isNestedScrollingEnabled = false



        // Initialize the graph
        moodGraph = findViewById(R.id.graphid)

        // Determine the current part of the day and update graph accordingly
        updateGraphForCurrentTime()




        // ViewModel setup
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(GraphDetailViewModel::class.java)

        viewModel.graphDetails.observe(this) { disorderDetails ->

            Log.d("op","${disorderDetails.menexercises}")

            pgbar.visibility = View.INVISIBLE
            disorderNameTextView.visibility = View.VISIBLE
            moodGraph.visibility = View.VISIBLE
            dummy1.visibility = View.VISIBLE
            dummy101.visibility = View.VISIBLE
            dummy102.visibility = View.VISIBLE
            dummy103.visibility = View.VISIBLE
            dummy104.visibility = View.VISIBLE
            dummy105.visibility = View.VISIBLE
//            dummy106.visibility = View.VISIBLE

            disorderNameTextView.text = disorderDetails.name
            dummy105.text = "REMEDIES TO CONTROL "+"${disorderDetails.name}"
            definitionTextView.text = disorderDetails.definition

            Glide.with(this).load(disorderDetails.imageurl).into(disorderImage)

            // Set up adapters for physical, mental exercises, and foods
            physicalExerciseAdapter = ExerAdapter(disorderDetails.phyexercises) {item ->
                val intent = Intent(this,LastActivity::class.java)
                intent.putExtra("issueNa",disorderName)
                intent.putExtra("num",1)

                intent.putExtra("exernumphy",item.name)
                Log.d("pp","$item")
                startActivity(intent)
            }



            physicalExerciseRecyclerView.adapter = physicalExerciseAdapter

            mentalExerciseAdapter = ExerAdapter(disorderDetails.menexercises) {item ->
                val intent = Intent(this,LastActivity::class.java)
                intent.putExtra("exernummen",item.name)
                intent.putExtra("num",2)

                intent.putExtra("issueNa",disorderName)

                Log.d("ppo","$item")
                startActivity(intent)
            }
            mentalExerciseRecyclerView.adapter = mentalExerciseAdapter

            vegFoodAdapter = FoodAdapter(disorderDetails.foodsVeg){item ->
                val intent = Intent(this,LastActivity::class.java)
                intent.putExtra("vegfoodnum",item.name)
                intent.putExtra("num",3)
                intent.putExtra("issueNa",disorderName)

                Log.d("pp","$item")
                startActivity(intent)
            }
            vegFoodRecyclerView.adapter = vegFoodAdapter

            nonVegFoodAdapter = FoodAdapter(disorderDetails.foodsNonVeg){item ->
                val intent = Intent(this,LastActivity::class.java)
                intent.putExtra("nonvegfoodnum",item.name)
                intent.putExtra("num",4)
                intent.putExtra("issueNa",disorderName)


                Log.d("pp","$item")
                startActivity(intent)
            }
            nonVegFoodRecyclerView.adapter = nonVegFoodAdapter
        }

        // Fetch disorder details
        viewModel.fetchGraphDetails(disorderName)
    }
    private fun updateGraphForCurrentTime() {
        val currentPartOfDay = getCurrentPartOfDay()
        Log.d("NewActivity", "Current part of day: $currentPartOfDay")

        // Get the mood for the current part of the day
        val mood = moodForTime[currentPartOfDay]
        mood?.let {
            populateGraphWithMood(it, currentPartOfDay)
        }
    }

    private fun getCurrentPartOfDay(): String {
        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeString = formatter.format(currentTime)
        val currentHour = timeString.substring(0, 2).toInt()

        return when {
            currentHour in 0..11 -> "Morning"
            currentHour in 12..15 -> "Afternoon"
            currentHour in 16..18 -> "Evening"
            currentHour in 19..23 -> "Night"
            else -> "Morning" // Default to morning if something goes wrong
        }
    }

    private fun populateGraphWithMood(mood: String, partOfDay: String) {
        // List to hold BarEntries (representing the y-values for parts of the day)
        val entries = ArrayList<BarEntry>()

        // Generate a random value (score for the current mood)
        val randomValue = Random.nextFloat() * 10  // Random mood score between 0 and 10
        entries.add(BarEntry(0f, randomValue))

        // Create BarDataSet
        val barDataSet = BarDataSet(entries, "$mood during $partOfDay")

        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeString = formatter.format(currentTime)
        val currentHour = timeString.substring(0, 2).toInt()


        when {
            currentHour in 0..11 -> {
                barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
                barDataSet.valueTextSize = 12f
            }
            currentHour in 12..15 -> {
                barDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
                barDataSet.valueTextSize = 12f
            }
            currentHour in 16..18 -> {
                barDataSet.setColors(*ColorTemplate.LIBERTY_COLORS)
                barDataSet.valueTextSize = 12f
            }
            currentHour in 19..23 -> {
                barDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
                barDataSet.valueTextSize = 12f
            }
            else ->{
                barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
                barDataSet.valueTextSize = 12f
            }
        }

        // Set data to the chart
        val barData = BarData(barDataSet)
        moodGraph.data = barData



        moodGraph.description.text = "Mood vs. Daytime"
        moodGraph.description.textSize = 12f
        moodGraph.description.isEnabled = true // Make sure it's en

        // Customize X-Axis (Moods)
        val xAxis: XAxis = moodGraph.xAxis
        xAxis.valueFormatter = DayPartFormatter(listOf(partOfDay))  // Show only the current part of the day
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f // Interval between labels

        // Customize Y-Axis (Random mood scores)
        val yAxisLeft: YAxis = moodGraph.axisLeft
        yAxisLeft.granularity = 1f
        moodGraph.axisRight.isEnabled = false // Disable the right Y-Axis

        // Refresh the chart
        moodGraph.invalidate()
    }

}