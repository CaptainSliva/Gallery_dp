package com.example.gallery_dp.data

import android.content.Context
import android.os.Environment
import androidx.room.Room
import com.example.gallery.Database.AppDatabase
import com.example.gallery.Database.CommentsDao
import com.example.gallery_dp.MainActivity

val PERMISSION_REQUEST_CODE = 101
val OPEN_DOCUMENT_REQUEST_CODE = 102
val PERMISSION_DELETE_REQUEST_CODE = 103
lateinit var mainContext: MainActivity
val baseFilePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/ContextPhoto/"
var allAlbums = mutableListOf<Album>()
var loadAlbumsFlag = false
var listpicture = mutableListOf<Picture>()
var listpicturefind = mutableListOf<Picture>()
var startId = 0L
var positionDeleteMedia = mutableListOf<Int>()
const val versionDB = 1

fun connectToDB(context: Context): CommentsDao {
    val db =
        Room
            .databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "CommentsDB",
            ).build()
    return db.commentsDao()
}
