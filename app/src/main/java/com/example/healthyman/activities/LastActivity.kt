package com.example.healthyman.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.healthyman.R
import com.example.healthyman.viewModel.GraphDetailViewModel
import com.example.healthyman.viewModel.LastViewModel

class LastActivity : AppCompatActivity() {
    private lateinit var tvTimer: TextView
    private lateinit var btnPause: Button
    private lateinit var btnResume: Button
    private var countdownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 30000 // 30 seconds in milliseconds
    private var isPaused = false
    private lateinit var viewModel: LastViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last)

        val issueName = intent.getStringExtra("issueNa") ?: ""
        val exernumphy = intent.getStringExtra("exernumphy") ?: ""
        val exernummen = intent.getStringExtra("exernummen") ?: ""
        val vegfoodnum = intent.getStringExtra("vegfoodnum") ?: ""
        val nonvegfoodnum = intent.getStringExtra("nonvegfoodnum") ?: ""
        val num: Int = intent.getIntExtra("num",0)

        Log.d("ppp","issueName = $issueName \n " +
                "phyExerNumber = $exernumphy \n " +
                "menExerNumber = $exernummen \n " +
                "vegNumer = $vegfoodnum \n " +
                "NonVegNumber = $nonvegfoodnum \n " +
                "WhichNumber = $num")




        tvTimer = findViewById(R.id.timerText)
        btnPause = findViewById(R.id.btn_pause)
        btnResume = findViewById(R.id.btn_resume)

        // Start countdown automatically
        startTimer()

        btnPause.setOnClickListener {
            pauseTimer()
        }

        btnResume.setOnClickListener {
            resumeTimer()
        }

        val exerimg = findViewById<ImageView>(R.id.exerciseImage)
        val exerdes = findViewById<TextView>(R.id.exerciseDescription)
        val exername = findViewById<TextView>(R.id.exerciseName)
        val last_ll = findViewById<LinearLayout>(R.id.last_ll)
        val last_pg = findViewById<ProgressBar>(R.id.last_pb)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(LastViewModel::class.java)

        when(num){
            1 ->{

                viewModel.phyExer.observe(this) { phyDetails ->
                    last_pg.visibility = View.INVISIBLE
                    last_ll.visibility = View.VISIBLE
                    exername.text = phyDetails.name

                    exerdes.text = phyDetails.description

                    Glide.with(this).load(phyDetails.imageurl).into(exerimg)

                    Log.d("ppp","det = $phyDetails")
                }
                viewModel.fetchPhyExer(issueName,exernumphy)
            }
            2 -> {

                viewModel.menExer.observe(this) { menDetails ->
                    last_pg.visibility = View.INVISIBLE
                    last_ll.visibility = View.VISIBLE
                    exername.text = menDetails.name

                    exerdes.text = menDetails.description

                    Glide.with(this).load(menDetails.imageurl).into(exerimg)

                    Log.d("ppp","det = $menDetails")
                }
                viewModel.fetchMenExer(issueName,exernummen)
            }
            3 -> {

                viewModel.vegFood.observe(this) { vegfood ->
                    last_pg.visibility = View.INVISIBLE
                    last_ll.visibility = View.VISIBLE
                    exername.text = vegfood.name

                    val dummytext = findViewById<TextView>(R.id.dummy2)
                    val dummytext2 = findViewById<TextView>(R.id.dummy3)
                    val dummyll = findViewById<LinearLayout>(R.id.dummy_ll)

                    dummytext.text="Dish Name :-"
                    dummytext2.text="Eating Time :-"

                    tvTimer.visibility= View.INVISIBLE
                    dummyll.visibility = View.INVISIBLE
                    exerdes.text = vegfood.eatingtime

                    Glide.with(this).load(vegfood.imageurl).into(exerimg)

                    Log.d("ppp","det = $vegfood")
                }
                viewModel.fetchVegFood(issueName,vegfoodnum)
            }
            4 -> {
                viewModel.nonVegFood.observe(this) { nonvegfood ->
                    last_pg.visibility = View.INVISIBLE
                    last_ll.visibility = View.VISIBLE
                    exername.text = nonvegfood.name

                    val dummytext = findViewById<TextView>(R.id.dummy2)
                    val dummytext2 = findViewById<TextView>(R.id.dummy3)
                    val dummyll = findViewById<LinearLayout>(R.id.dummy_ll)

                    dummytext.text="Dish Name :-"
                    dummytext2.text="Eating Time :-"
                    tvTimer.visibility= View.INVISIBLE
                    dummyll.visibility = View.INVISIBLE
                    exerdes.text = nonvegfood.eatingtime

                    Glide.with(this).load(nonvegfood.imageurl).into(exerimg)

                    Log.d("ppp","det = $nonvegfood")
                }
                viewModel.fetchNonVegFood(issueName,nonvegfoodnum)


            }

        }


    }







    private fun startTimer() {
        countdownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                timeLeftInMillis = 30000
                startTimer()
            }
        }.start()
    }

    private fun pauseTimer() {
        countdownTimer?.cancel()
        isPaused = true
    }

    private fun resumeTimer() {
        if (isPaused) {
            startTimer()
            isPaused = false
        }
    }

    private fun updateTimerText() {
        val secondsLeft = (timeLeftInMillis / 1000).toInt()
        tvTimer.text = secondsLeft.toString()
    }
}