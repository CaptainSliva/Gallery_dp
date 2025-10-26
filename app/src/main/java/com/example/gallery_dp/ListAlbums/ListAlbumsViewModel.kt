package com.example.gallery_dp.ListAlbums

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery_dp.CustomClasses.Album
import com.example.gallery_dp.CustomClasses.Picture
import com.example.gallery_dp.Functions.FunctionsMediaStore
import com.example.gallery_dp.Functions.FunctionsMediaStore.getListAlbums
import com.example.gallery_dp.allAlbums
import com.example.gallery_dp.loadAlbumsFlag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListAlbumsViewModel : ViewModel() {
    fun addAlbums(context: Context) {
        if (!loadAlbumsFlag) {
            getListAlbums(context).forEach {
                allAlbums.add(it)
            }
            loadAlbumsFlag = true
        }
    }
}
