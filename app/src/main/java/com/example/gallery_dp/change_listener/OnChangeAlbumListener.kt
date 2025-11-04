package com.example.gallery_dp.change_listener

import com.example.gallery_dp.data.Album

interface OnChangeAlbumListener {
    fun onCreateAlbum(
        album: Album,
        changeType: AlbumChangeType,
    )
}
