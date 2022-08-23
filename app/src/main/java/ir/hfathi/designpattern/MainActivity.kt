package ir.hfathi.designpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.hfathi.designpattern.behavioralPatterns.singletion.Singleton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpSingleton()
    }

    private fun setUpSingleton() {
        val first = Singleton.instance  // This (Singleton@7daf6ecc) is a
        // singleton
        first.b = "hello singleton"

        val second = Singleton.instance
        println(first.b)        // hello singleton
        println(second.b)        // hello singleton
    }
}