package com.example.gallery_dp.utils

import android.content.Context
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.Database.Comment
import com.example.gallery_dp.R
import com.example.gallery_dp.adapters.AlbumsRVadapter
import com.example.gallery_dp.change_listener.AlbumChangeType
import com.example.gallery_dp.data.Album
import com.example.gallery_dp.data.allAlbums
import com.example.gallery_dp.data.listpicture
import com.example.gallery_dp.dialogs.DeleteDialogFragment
import com.example.gallery_dp.dialogs.RenameAlbumDialogFragment
import kotlin.collections.forEach

object FunctionsApp {
    fun dpToPx(
        context: Context,
        dp: Int,
    ) = (dp * context.resources.displayMetrics.density).toInt()

    fun findRightRegisterResults(
        s: String,
        trashRes: List<Comment>,
    ): List<Comment> {
        val resultList = mutableListOf<Comment>()
        trashRes.forEach { if (it.image_comment == s) resultList.add(it) }
        return resultList
    }

    fun durationTranslate(milliseconds: Int): String {
        val seconds = milliseconds / 1000
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60

        return when {
            hours > 0 -> "$hours:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}"
            else -> "$minutes:${secs.toString().padStart(2, '0')}"
        }
    }

    fun handleMenuItemClick(
        childFragmentManager: FragmentManager,
        context: Context,
        item: MenuItem,
        album: Album,
    ) {
        when (item.itemId) {
            R.id.action_rename_album -> RenameAlbumDialogFragment(context, album).show(childFragmentManager, "RENAME_ALBUM")
            R.id.action_delete_album -> DeleteDialogFragment(context, album).show(childFragmentManager, "DELETE_ALBUM")
        }
    }

    fun changeAlbumPhotoAmount(
        albumName: String,
        decr: Boolean = false,
    ) {
        if (listpicture.isNotEmpty()) {
            for (element in allAlbums) {
                if (element.name == albumName && decr) element.itemsCount -= listpicture.size
                if (element.name == albumName && !decr) element.itemsCount += listpicture.size
            }
        }
    }
}
