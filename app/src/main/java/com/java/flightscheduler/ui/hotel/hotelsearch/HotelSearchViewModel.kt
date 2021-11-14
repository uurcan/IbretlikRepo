package com.java.flightscheduler.ui.hotel.hotelsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.data.repository.HotelSearchRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotelSearchViewModel @Inject constructor(private val hotelSearchRepository: HotelSearchRepository): BaseViewModel() {
    private var cityLiveData : MutableLiveData<List<City>>? = MutableLiveData()
    var hotelSearchLiveData : MutableLiveData<HotelSearch>? = MutableLiveData()
    private val validationMessage = MutableLiveData("")

    private val passengerCountLiveData = MutableLiveData(1)
    val passengerCount: LiveData<Int> get() = passengerCountLiveData

    private val roomCountLiveData = MutableLiveData(1)
    val roomCount: LiveData<Int> get() = roomCountLiveData

    fun performValidation(city : String) : MutableLiveData<String>{
        validationMessage.value = ""
        if (city.isBlank()) {
            validationMessage.value = "City is missing"
        }
        return validationMessage
    }

    fun setHotelSearchLiveData(hotelSearch: HotelSearch){
        hotelSearchLiveData?.value = hotelSearch
    }

    fun getCities() : MutableLiveData<List<City>>?{
        val iataList = hotelSearchRepository.getCities()
        cityLiveData?.postValue(iataList)
        return cityLiveData
    }

    fun onIncreasePassengerClicked(count: Int?){
        passengerCountLiveData.value = hotelSearchRepository.increaseAuditCount(count)!!
    }

    fun onDecreasePassengerClicked(count: Int?){
        passengerCountLiveData.value = hotelSearchRepository.decreaseAuditCount(count)!!
    }

    fun onIncreaseRoomClicked(count: Int?){
        roomCountLiveData.value = hotelSearchRepository.increaseRoomCount(count)!!
    }

    fun onDecreaseRoomClicked(count: Int?){
        roomCountLiveData.value = hotelSearchRepository.decreaseRoomCount(count)!!
    }
}