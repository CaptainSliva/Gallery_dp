package com.example.gallery_dp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gallery_dp.databinding.FragmentCommentPhotoBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CommentPhotoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentCommentPhotoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCommentPhotoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }
}
