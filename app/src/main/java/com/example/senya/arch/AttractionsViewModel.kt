package com.example.senya.arch

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.senya.data.Attraction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AttractionsViewModel: ViewModel() {
    private val repository = AttractionsRepository()

    //home fragment
    val attractionListLiveData = MutableLiveData<ArrayList<Attraction>>()

    //attractiondetail fragment
    val selectedAttractionLiveData = MutableLiveData<Attraction>()

    //location menu
    val locationSelectedLiveData = MutableLiveData<Attraction>()

    fun init(context: Context){
            val attractionsList = repository.parseAttractions(context)
            attractionListLiveData.postValue(attractionsList)
    }

    fun onAttractionSelected(attractionId: String){
        val attraction = attractionListLiveData.value?.find {
            it.id == attractionId
        } ?: return

        selectedAttractionLiveData.postValue(attraction)
    }
}