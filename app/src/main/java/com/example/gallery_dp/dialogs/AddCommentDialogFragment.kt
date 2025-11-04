package com.example.gallery_dp.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.gallery_dp.R
import com.example.gallery_dp.data.listpicture
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddCommentDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.dialog_add_comment, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val bPos = view.findViewById<Button>(R.id.b_pos)
        val bNeg = view.findViewById<Button>(R.id.b_neg)
        val etComment = view.findViewById<EditText>(R.id.comment_photo_text)

        bPos.setOnClickListener {
//            val db = CommentsDao()
//            listpicture.forEach {
//                //TODO схранить или заменить комментарий
//            }
            dismiss()
        }

        bNeg.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listpicture.clear()
    }
}
