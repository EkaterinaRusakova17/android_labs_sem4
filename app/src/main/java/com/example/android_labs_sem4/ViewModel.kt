package com.example.android_labs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModel(
    private val service: RickAndMortyApi = RetrofitClient.RickAndMorty
) : ViewModel() {
    private val _characterData = MutableLiveData<List<Character>>()
    val characterData: LiveData<List<Character>> = _characterData

    fun fetchCharacters(ids: List<Int>) {
        viewModelScope.launch {
            try {
                val characters = ids.map { id ->
                    service.getCharacter(id)
                }
                _characterData.value = characters
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching characters", e)
            }
        }
    }
}
