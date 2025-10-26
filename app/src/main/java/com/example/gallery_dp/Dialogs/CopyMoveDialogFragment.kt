package com.example.gallery_dp.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import com.example.gallery_dp.ChangeListener.AlbumChangeType
import com.example.gallery_dp.ChangeListener.OnChangeAlbumListener
import com.example.gallery_dp.CustomClasses.Album
import com.example.gallery_dp.Functions.FunctionsMediaStore
import com.example.gallery_dp.Functions.FunctionsMediaStore.copyMediaToAlbum
import com.example.gallery_dp.Functions.FunctionsMediaStore.getListAlbums
import com.example.gallery_dp.R
import com.example.gallery_dp.allAlbums
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CopyMoveDialogFragment(
    private val listUri: List<Uri>,
    private val albumName: String,
) : BottomSheetDialogFragment() {
    private lateinit var listener: OnChangeAlbumListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.dialog_copy_move, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("URIS", listUri.toString())

        val bCopy = view.findViewById<Button>(R.id.b_copy)
        val bMove = view.findViewById<Button>(R.id.b_move)
        val bCancel = view.findViewById<Button>(R.id.b_cancel)

        bCopy.setOnClickListener {
            listUri.forEach {
                if (copyMediaToAlbum(requireContext(), it, albumName)) {
                    if (it == listUri[listUri.size - 1]) {
                        listener = parentFragment as OnChangeAlbumListener
                        val albumsNames = allAlbums.map { it.name }
                        lateinit var newAlbum: Album
                        getListAlbums(requireContext()).forEach { album ->
                            if (album.name !in albumsNames) newAlbum = album
                        }
                        try {
                            allAlbums.add(newAlbum)
                            allAlbums = allAlbums.sortedBy { it.name } as MutableList<Album>
                            listener.onCreateAlbum(newAlbum, AlbumChangeType.ADD)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Альбом \"$albumName\" уже создан", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "IO ex ${it.path}", Toast.LENGTH_SHORT).show()
                }
            }
//            listUri.forEach { functionsMedia.copyFileToAlbum(requireContext(), it, albumDir) }
            dismiss()
        }
        bMove.setOnClickListener {
            Toast.makeText(requireContext(), "В разработке", Toast.LENGTH_SHORT).show()
//            Toast.makeText(requireContext(), "FIXME *todo", Toast.LENGTH_SHORT).show()
//            TODO("// FIXME:  Volume picker not found. *drop when try delete media")
//            listUri.forEach {
//                if (functionsMedia.moveMediaToAlbum(requireContext(), it, albumName)) {
//                    if (it == listUri[listUri.size - 1]) {
//                        listener = parentFragment as OnChangeAlbumListener
// //                        val albumsNames = allAlbums.map { it.name }
// //                        lateinit var newAlbum: Album
// //                        FunctionsAlbums().getListAlbums(requireContext()).forEach { album ->
// //                            if (album.name !in albumsNames) newAlbum = album
// //                        }
// //                        allAlbums.add(newAlbum)
//                        allAlbums = allAlbums.sortedBy { it.name } as MutableList<Album>
//                        listener.onCreateAlbum(allAlbums[0], AlbumChangeType.DELETE)
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "IO ex ${it.path}", Toast.LENGTH_SHORT).show()
//                }
//            }
            dismiss()
        }
        bCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}
