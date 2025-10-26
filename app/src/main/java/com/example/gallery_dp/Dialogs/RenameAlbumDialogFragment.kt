package com.example.gallery_dp.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import com.example.gallery_dp.ChangeListener.AlbumChangeType
import com.example.gallery_dp.ChangeListener.OnChangeAlbumListener
import com.example.gallery_dp.CustomClasses.Album
import com.example.gallery_dp.Functions.FunctionsDialogs
import com.example.gallery_dp.Functions.FunctionsDialogs.showRenameAlbumMessage
import com.example.gallery_dp.Functions.FunctionsMediaStore
import com.example.gallery_dp.Functions.FunctionsMediaStore.getListAlbums
import com.example.gallery_dp.R
import com.example.gallery_dp.allAlbums
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RenameAlbumDialogFragment(
    private val context: Context,
    private val album: Album,
) : BottomSheetDialogFragment() {
    private lateinit var listener: OnChangeAlbumListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.dialog_rename_album, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<TextInputEditText>(R.id.album_name)
        val bNeg = view.findViewById<MaterialButton>(R.id.b_neg)
        val bPos = view.findViewById<MaterialButton>(R.id.b_pos)
        editText.setText(album.name)
        editText.setSelection(editText.text.toString().length) // Курсор в конец текста

        bPos.setOnClickListener {
            val newName = editText.text.toString().trim()
            if (newName.isNotEmpty()) {
                showRenameAlbumMessage(context, album, newName)
                // TODO("rename функция на 10 не работает")
                //  private fun renameAlbum(
                //        albumPath: File,
                //        newName: String,
                //    ) {
                // //        try {
                //        val stringPath = albumPath.toString()
                //        println("Old dest! " + stringPath)
                //        val newDest = stringPath.slice(0..stringPath.lastIndexOf("/")) + newName
                //        println("New dest! " + newDest)
                //        albumPath.renameTo(File(newDest))
                // //        } catch (e: Exception) {
                // //        }
                //    }
                // 2025-10-19 15:01:52.544  5482-5482  System.out              context.photo                        I  Old dest! /storage/emulated/0/Pictures/ContextPhoto/2
                // 2025-10-19 15:01:52.544  5482-5482  System.out              context.photo                        I  New dest! /storage/emulated/0/Pictures/ContextPhoto/26
                // Такой вывод я получаю, но по факту альбом не переименовывается на 10 андроиде. На 14 и 16 функция работает корректно.

                listener = parentFragment as OnChangeAlbumListener
                val albumsNames = allAlbums.map { it.name }
                var newAlbum = allAlbums[0]
                getListAlbums(requireContext()).forEachIndexed { i, album ->
                    if (album.name != albumsNames[i]) {
                        newAlbum = album
                        allAlbums[i].bID = album.bID
                    }
                }
                if (newAlbum != allAlbums[0]) {
                    allAlbums = allAlbums.sortedBy { it.name } as MutableList<Album>
                    listener.onCreateAlbum(newAlbum, AlbumChangeType.RENAME)
                } else {
                    Toast.makeText(context, context.getString(R.string.cant_rename_album), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, context.getString(R.string.enter_name), Toast.LENGTH_SHORT).show()
            }
            Log.i("NEWNAME", "$newName")
            dismiss()
        }
        bNeg.setOnClickListener {
            dismiss()
        }

        Log.i("RENAME", "$album")
    }
}
