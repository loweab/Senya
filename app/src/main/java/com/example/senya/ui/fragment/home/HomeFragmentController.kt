package com.example.senya.ui.fragment.home

import com.airbnb.epoxy.EpoxyController
import com.example.senya.R
import com.example.senya.data.Attraction
import com.example.senya.databinding.EpoxyModelHeaderBinding
import com.example.senya.databinding.EpoxyModelLoadingBinding
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

        val firstGroup = attractions.filter { it.title.startsWith("s", true) ||
                it.title.startsWith("d", true) }

        HeaderEpoxyModel("Recently Viewed").id("header_1").addTo(this)
        firstGroup.forEach{
            AttractionEpoxyModel(it, onClickedCallback)
                .id(it.id)
                .addTo(this)
        }

        HeaderEpoxyModel("All Attractions").id("header_2").addTo(this)
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
            if(attraction.image_urls.isNotEmpty()){
                Picasso.get().load(attraction.image_urls[0]).into(headerEpoxyRecyclerView);
            }
            monthsToVisitTextView.text = attraction.months_to_visit
            root.setOnClickListener {
                onClicked(attraction.id)
            }
        }

    }

    data class HeaderEpoxyModel(val headerText: String): ViewBindingKotlinModel<EpoxyModelHeaderBinding>(R.layout.epoxy_model_header){
        override fun EpoxyModelHeaderBinding.bind() {
            headerTextView.text = headerText
        }

    }
}