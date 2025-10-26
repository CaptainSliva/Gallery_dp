package com.example.gallery_dp.ListPictures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery_dp.CustomClasses.Album
import com.example.gallery_dp.CustomClasses.Picture
import com.example.gallery_dp.listpicture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.forEach

class ListPicturesViewModel : ViewModel() {
    private val _rvPictures = MutableStateFlow<List<Picture>>(emptyList())
    val rvPictures: StateFlow<List<Picture>> = _rvPictures.asStateFlow()

    fun addPictures(newPictureFlow: Flow<Picture>) {
        // Запускаем сбор в CoroutineScope с жизненным циклом
        viewModelScope.launch {
            newPictureFlow.collect { picture ->
                _rvPictures.update { currentList ->
                    currentList.toMutableList().apply {
                        add(picture)
                    }
                }
            }
        }
    }
}
