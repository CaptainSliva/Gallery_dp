package com.example.gallery_dp.dialogs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.gallery_dp.R
import com.example.gallery_dp.change_listener.AlbumChangeType
import com.example.gallery_dp.change_listener.OnChangeAlbumListener
import com.example.gallery_dp.data.Album
import com.example.gallery_dp.data.allAlbums
import com.example.gallery_dp.utils.FunctionsDialogs.showDeleteAlbumMessage
import com.example.gallery_dp.utils.FunctionsMediaStore.getListAlbums
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeleteDialogFragment(
    private val context: Context,
    private val album: Album,
    private val needDelete: Boolean = true,
) : BottomSheetDialogFragment() {
    private lateinit var listener: OnChangeAlbumListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.dialog_delete, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val bDelete = view.findViewById<Button>(R.id.b_delete)
        val bCancel = view.findViewById<Button>(R.id.b_cancel)

        bDelete.setOnClickListener {
            if (needDelete) {
                showDeleteAlbumMessage(context, album)
                allAlbums = getListAlbums(requireContext()) as MutableList<Album>
            }
            listener = parentFragment as OnChangeAlbumListener
            listener.onCreateAlbum(allAlbums[0], AlbumChangeType.DELETE)
            dismiss()
        }

        bCancel.setOnClickListener {
            dismiss()
        }
        if (needDelete) {
            Log.i("DELETE", "${album.name}")
        }
    }
}
