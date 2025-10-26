package com.example.gallery_dp.Dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.gallery_dp.Functions.FunctionsDialogs
import com.example.gallery_dp.Functions.FunctionsDialogs.mediaPicker
import com.example.gallery_dp.Functions.FunctionsDialogs.showCreateAlbumMessage
import com.example.gallery_dp.Functions.FunctionsUri
import com.example.gallery_dp.Functions.FunctionsUri.handleSelectedMedia
import com.example.gallery_dp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class CreateAlbumDialogFragment : BottomSheetDialogFragment() {
    lateinit var albumName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.dialog_create_album, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val pickVisualMedia =
            registerForActivityResult(
                ActivityResultContracts.PickMultipleVisualMedia(),
            ) { uris ->
                if (uris.isNotEmpty()) {
                    CopyMoveDialogFragment(uris, albumName).show(parentFragmentManager, "COPY_MOVE_DIALOG")
                    Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
                dismiss()
            }

        val pickMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = handleSelectedMedia(result.data)
                    CopyMoveDialogFragment(data, albumName).show(parentFragmentManager, "COPY_MOVE_DIALOG")
                } else {
                }
                dismiss()
            }

        val editText = view.findViewById<TextInputEditText>(R.id.album_name_text)
        val bNeg = view.findViewById<Button>(R.id.b_neg)
        val bPos = view.findViewById<Button>(R.id.b_pos)

        bPos.setOnClickListener {
            albumName = editText.text.toString().trim()
            if (albumName.isNotEmpty()) {
                showCreateAlbumMessage(requireContext(), albumName)
                mediaPicker(pickMediaLauncher, pickVisualMedia, ActivityResultContracts.PickVisualMedia.ImageAndVideo)
            } else {
                Toast.makeText(context, context?.getString(R.string.enter_name), Toast.LENGTH_SHORT).show()
            }
            Log.i("NEWNAME", "$albumName")
        }
        bNeg.setOnClickListener {
            dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}
