package com.example.fitflex

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitflex.databinding.ActivityExcerciseBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExcerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0
    //TODO: Change the time back to 11000
    private var restTimeDuration : Long = 1

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0
    //TODO: Change the time to 31000 back
    private var exerciseTimeDuration : Long = 2

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition : Int = -1

    private var tts : TextToSpeech? = null
    private var player : MediaPlayer? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this, this)

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(restTimeDuration*1000 , 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 11 - restProgress
                binding?.tvTimer?.text = (11-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimeDuration*1000 , 1000){
                 override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 31 - exerciseProgress
                binding?.tvTimerExercise?.text = (31-exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()
                if (currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                }
                else{
                    Toast.makeText(this@ExerciseActivity , "Finished" , Toast.LENGTH_SHORT).show()
//TODO: Remove finish() after setting the intent for finish screen
                    finish()
                }
            }
        }.start()
    }

    private fun setupRestView(){

        try {
            val soundURI = Uri.parse("android.resource://com.example.fitflex/" + R.raw.rest_view_sound)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false

            // Add delay using Handler
            Handler().postDelayed({
                player?.start()
            }, 800) // 1000 milliseconds = 1 second
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        speakOut("Rest ")

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = (exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null){
            restTimer?.cancel()
            restProgress=0
        }
        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if (player != null){
            player!!.stop()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.ENGLISH)

            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("tts" , "The language specified is not supported")
            }
        }
        else{
            Log.e("tts" , "Initialization Failed")
        }
    }

    private fun speakOut(text : String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH  , null , "")
        tts!!.setPitch(0.8f)
        tts!!.setSpeechRate(0.9f)
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }
}