package com.example.gallery_dp.ChangeListener

import com.example.gallery_dp.CustomClasses.Album

interface OnChangeAlbumListener {
    fun onCreateAlbum(
        album: Album,
        changeType: AlbumChangeType,
    )
}
