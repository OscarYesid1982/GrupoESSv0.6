package com.grupoess.grupoessv05

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class categorias : AppCompatActivity(){
    //Member variables
    private var mRecyclerView: RecyclerView? = null
    private var mSportsData: ArrayList<Sport>? = null
    private var mAdapter: SportsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragmentlayout)

        //Initialize the RecyclerView
        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView

        //Set the Layout Manager
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)


        //Initialize the ArrayList that will contain the data
        mSportsData = ArrayList<Sport>()

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = SportsAdapter(this, mSportsData)
        mRecyclerView!!.adapter = mAdapter

        //Get the data
        initializeData()
    }

    /**
     * Method for initializing the sports data from resources.
     */
    private fun initializeData() {
        val sportsList = arrayOfNulls<String>(20)
        val sportsInfo = arrayOfNulls<String>(20)
        val sportsImageResources = arrayOfNulls<String>(20)

        //Create the ArrayList of Sports objects with the titles, images
        // and information about each sport
        for (i in sportsList.indices) {
            Log.i("Alerta",i.toString())
            mSportsData!!.add(Sport(sportsList[i], sportsInfo[i],sportsImageResources[i])            )
        }

        //Notify the adapter of the change
        mAdapter?.notifyDataSetChanged()
    }
}