package com.grupoess.grupoessv05.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupoess.grupoessv05.R
import com.grupoess.grupoessv05.model.IntroSlide

class IntroSliderAdapter (private  val introSlides: List<IntroSlide>):
    RecyclerView.Adapter<IntroSliderAdapter.IntroSlideViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return  IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slider_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    inner class IntroSlideViewHolder(view: View): RecyclerView.ViewHolder(view){

        private  val textTittle = view.findViewById<TextView>(R.id.textTittle)
        private  val textDescription = view.findViewById<TextView>(R.id.textDescription)
        private  val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)

        fun bind(introSlide: IntroSlide){
            textTittle.text = introSlide.tittle
            textDescription.text = introSlide.description
            imageIcon.setImageResource(introSlide.icon)
        }
    }

}