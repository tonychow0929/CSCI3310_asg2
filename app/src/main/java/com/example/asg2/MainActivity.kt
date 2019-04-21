package com.example.asg2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*;
import android.os.Bundle


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mainRecyclerView: RecyclerView = findViewById(R.id.mainRecyclerView) as RecyclerView;
        mainRecyclerView.layoutManager = GridLayoutManager(this,2)
        mainRecyclerView.adapter = ImageListAdapter(this)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#404FB2")))
    }
}
