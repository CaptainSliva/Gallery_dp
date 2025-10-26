package com.example.gallery_dp.CustomClasses

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

data class Album(
    var bID: String,
    val name: String,
    var itemsCount: Int, // TODO надо будет учесть
    val miniature: Bitmap?,
    val path: File,
)
