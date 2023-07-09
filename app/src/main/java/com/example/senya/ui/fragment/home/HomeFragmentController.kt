package com.example.senya.ui.fragment.home

import com.airbnb.epoxy.EpoxyController
import com.example.senya.R
import com.example.senya.data.Attraction
import com.example.senya.databinding.ViewHolderAttractionBinding
import com.example.senya.ui.epoxy.LoadingEpoxyModel
import com.example.senya.ui.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class HomeFragmentController(private val onClickedCallback: (String) -> Unit) :
    EpoxyController(){

    var isLoading : Boolean = false
        set(value){
            field = value
            if(field){
                requestModelBuild()
            }
        }

    var attractions = ArrayList<Attraction>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }


    override fun buildModels() {
        if(isLoading){
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if(attractions.isEmpty()){

            return
        }

        attractions.forEach{
            AttractionEpoxyModel(it, onClickedCallback)
                .id(it.id)
                .addTo(this)
        }

    }

    data class AttractionEpoxyModel(val attraction: Attraction, val onClicked: (String) -> Unit) :
        ViewBindingKotlinModel<ViewHolderAttractionBinding>(R.layout.view_holder_attraction) {
        override fun ViewHolderAttractionBinding.bind() {
            titleTextView.text = attraction.title
            Picasso.get().load(attraction.image_urls[0]).into(headerImageView);
            monthsToVisitTextView.text = attraction.months_to_visit
            root.setOnClickListener {
                onClicked(attraction.id)
            }
        }

    }
}