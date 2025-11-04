package com.example.gallery_dp.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.example.gallery_dp.utils.FunctionsMediaStore.copyMediaToAlbum
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object FunctionsFiles {
    fun deleteAlbum(albumPath: File) {
        try {
            albumPath.listFiles().forEach {
                it.delete()
            }
            albumPath.delete()
        } catch (e: Exception) {
        }
    }

    fun renameAlbum(
        albumPath: File,
        newName: String,
    ) {
        try {
            val stringPath = albumPath.toString()
            println("Old dest! " + stringPath)
            val newDest = stringPath.slice(0..stringPath.lastIndexOf("/")) + newName
            println("New dest! " + newDest)
            albumPath.renameTo(File(newDest))
        } catch (e: Exception) {
        }
    }

    fun moveMediaToAlbum(
        context: Context,
        sourceUri: Uri,
        albumName: String,
    ): Boolean {
        val contentResolver = context.contentResolver
        if (copyMediaToAlbum(context, sourceUri, albumName)) {
            // После успешного копирования удаляем оригинал
//            try {
            contentResolver.delete(sourceUri, null, null)
            return true
//            } catch (e: Exception) {
//            Log.e("MediaMove", "Ошибка удаления оригинала", e)
            return false
//            }
        } else {
            return false
        }
    }
}
