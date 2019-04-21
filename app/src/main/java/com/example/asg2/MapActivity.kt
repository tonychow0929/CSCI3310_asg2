package com.example.asg2

import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity
import android.widget.*;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import android.app.*
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity: AppCompatActivity() {
    //lateinit var mMap: GoogleMap
    lateinit var latlon: FloatArray;
    private var selectedMode: Boolean = false;
    private var enableRadioBarListener = true;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)
        var intent = getIntent()
        this.selectedMode = intent.getBooleanExtra("com.example.asg2.SELECTED_MODE", true)
        this.latlon = intent.getFloatArrayExtra("com.example.asg2.PICTURE_LATLON")
        //var isFromAnotherActivity = intent.getBooleanExtra("com.example.asg2.FROM_ANOTHER_ACTIVITY", false);
        var radioGroup = findViewById<android.widget.RadioGroup>(R.id.radioGroup)
        if (this.selectedMode) {
            radioGroup.check(R.id.locationOption)
            switchToMap(false)
        } else {
            radioGroup.check(R.id.panoOption)
            switchToPano(false)
        }
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#404FB2")))
        radioGroup.setOnCheckedChangeListener { group, id -> if (enableRadioBarListener) { if (id == R.id.locationOption) switchToMap(true) else switchToPano(true) } }
    }

    fun switchToMap(addStack: Boolean): Unit {
        var fragmentManager = supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction();
        var mapFragment = MyMapFragment()
        mapFragment.activity = this
        fragmentTransaction = fragmentTransaction.replace(R.id.fragment_container, mapFragment, "map_fragment")
        if (addStack) {
            fragmentTransaction = fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    fun switchToPano(addStack: Boolean): Unit {
        var fragmentManager = supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()
        var panoFragment = PanoFragment()
        panoFragment.activity = this
        fragmentTransaction = fragmentTransaction.replace(R.id.fragment_container, panoFragment, "pano_fragment")
        if (addStack) {
            fragmentTransaction = fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        var count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
            var radioBar = findViewById<android.widget.RadioGroup>(R.id.radioGroup)
            enableRadioBarListener = false
            if (radioBar.checkedRadioButtonId == R.id.locationOption) {
                radioBar.check(R.id.panoOption)
            } else {
                radioBar.check(R.id.locationOption)
            }
            enableRadioBarListener = true
        }
    }
}

class MyMapFragment: Fragment, OnMapReadyCallback {
    //private lateinit var view: View;
    lateinit var activity: MapActivity set;
    constructor() : super() {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, p2: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.map_fragment, container, false);
        android.util.Log.e("","Hey?");
        var map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this);
        android.util.Log.e("","wait1"); return view;
    }

    override fun onMapReady(googleMap: GoogleMap) {
        android.util.Log.e("","hi")
        //activity.mMap = googleMap
        val latlonObject = LatLng(activity.latlon[0].toDouble(), activity.latlon[1].toDouble())
        googleMap.addMarker(MarkerOptions().position(latlonObject))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlonObject, 18f))
        android.util.Log.e("","done")
    }
}

class PanoFragment: Fragment, OnStreetViewPanoramaReadyCallback {
    lateinit var activity: MapActivity set;
    constructor() : super() {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, p2: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.pano_fragment, container, false)
        var pano = childFragmentManager.findFragmentById(R.id.pano) as SupportStreetViewPanoramaFragment
        pano.getStreetViewPanoramaAsync(this)
        return view
    }

    override fun onStreetViewPanoramaReady(pano: StreetViewPanorama) {
        pano.setPosition(LatLng(activity.latlon[0].toDouble(), activity.latlon[1].toDouble()))
        pano.isZoomGesturesEnabled = false
        pano.isUserNavigationEnabled = false
        pano.isStreetNamesEnabled = true
        pano.isPanningGesturesEnabled = true
    }
}
