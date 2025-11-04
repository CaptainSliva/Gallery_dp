package com.example.gallery_dp.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.gallery_dp.data.allAlbums
import com.example.gallery_dp.data.loadAlbumsFlag
import com.example.gallery_dp.utils.FunctionsMediaStore

class ListAlbumsViewModel : ViewModel() {
    fun addAlbums(context: Context) {
        if (!loadAlbumsFlag) {
            FunctionsMediaStore.getListAlbums(context).forEach {
                allAlbums.add(it)
            }
            loadAlbumsFlag = true
        }
    }
}
