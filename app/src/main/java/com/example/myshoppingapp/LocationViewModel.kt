package com.example.myshoppingapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class LocationViewModel : ViewModel() {

    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address


    fun updateLocation(newLocation: LocationData) {

        _location.value = newLocation
    }

    fun fetchAddress(latLng: String) {
        Log.i("fetchAddress", "Starting request for: $latLng")
        viewModelScope.launch {
            try {
                // Remove any spaces to ensure valid "lat,lng" format
                val formattedLatLng = latLng.replace(" ", "")

                val result = RetrofitClient.create().getAddressFromCoordinates(
                    formattedLatLng,
                    BuildConfig.MAPS_API_KEY
                )

                Log.i("fetchAddress", "Status received: ${result.status}")

                if (result.status == "OK") {
                    _address.value = result.results
                    Log.i(
                        "fetchAddress",
                        "Address found: ${result.results.firstOrNull()?.formatted_address}"
                    )
                } else {
                    Log.e(
                        "fetchAddress",
                        "API Error: ${result.status} | Message: ${result.error_message}"
                    )
                }
            } catch (e: Exception) {
                Log.e("fetchAddress", "Network/Parsing Exception: ${e.message}")
            }
        }
    }


}