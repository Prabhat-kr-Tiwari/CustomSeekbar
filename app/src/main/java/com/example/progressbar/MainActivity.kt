package com.example.progressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    private lateinit var seekBar: SeekBar
    private lateinit var textSize: TextView
    private lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar=findViewById(R.id.seekbar2)
        textSize=findViewById(R.id.text)
        text=findViewById(R.id.text2)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Called when the seek bar's progress changes
                // You can use the 'progress' parameter to get the new progress value
                textSize.textSize= progress.toFloat()
                text.text = progress.toString()


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Called when the user starts interacting with the seek bar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Called when the user stops interacting with the seek bar
            }
        })

    }
}