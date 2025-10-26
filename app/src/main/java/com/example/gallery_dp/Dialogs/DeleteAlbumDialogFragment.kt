package com.example.gallery_dp.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.gallery_dp.ChangeListener.AlbumChangeType
import com.example.gallery_dp.ChangeListener.OnChangeAlbumListener
import com.example.gallery_dp.CustomClasses.Album
import com.example.gallery_dp.Functions.FunctionsDialogs
import com.example.gallery_dp.Functions.FunctionsDialogs.showDeleteAlbumMessage
import com.example.gallery_dp.Functions.FunctionsMediaStore
import com.example.gallery_dp.Functions.FunctionsMediaStore.getListAlbums
import com.example.gallery_dp.R
import com.example.gallery_dp.allAlbums
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteAlbumDialogFragment(
    private val context: Context,
    private val album: Album,
) : BottomSheetDialogFragment() {
    private lateinit var listener: OnChangeAlbumListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.dialog_delete_album, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val bDelete = view.findViewById<Button>(R.id.b_delete)
        val bCancel = view.findViewById<Button>(R.id.b_cancel)

        bDelete.setOnClickListener {
            showDeleteAlbumMessage(context, album)
            listener = parentFragment as OnChangeAlbumListener
            allAlbums = getListAlbums(requireContext()) as MutableList<Album>
            listener.onCreateAlbum(allAlbums[0], AlbumChangeType.DELETE)
            dismiss()
        }

        bCancel.setOnClickListener {
            dismiss()
        }

        Log.i("DELETE", "${album.name}")
    }
}
