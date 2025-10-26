package com.example.gallery_dp.ListPictures

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.gallery_dp.Adapters.PicturesRVadapter
import com.example.gallery_dp.ChangeListener.OnMediaSelectedListener
import com.example.gallery_dp.Functions.FunctionsMediaStore.getAllMedia
import com.example.gallery_dp.Functions.FunctionsUri.convertUri
import com.example.gallery_dp.PERMISSION_REQUEST_CODE
import com.example.gallery_dp.databinding.ListPicturesFragmentBinding
import com.example.gallery_dp.listpicture
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.launch

/**
 * A simple [androidx.fragment.app.Fragment] subclass as the second destination in the navigation.
 */
class ListPicturesFragment :
    Fragment(),
    OnMediaSelectedListener {
    private var _binding: ListPicturesFragmentBinding? = null

    lateinit var adapter: PicturesRVadapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ListPicturesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val bucketID = arguments?.getString("bucketID")!!
        val albumName = arguments?.getString("albumName")!!
        val amountOfItems = arguments?.getInt("amountOfItems")!!

        val listPicturesViewModel = ViewModelProvider(this)[ListPicturesViewModel::class.java]
        val rvPictures = binding.rvPictures
        val adapter = PicturesRVadapter(childFragmentManager, listOf(), this)
        val layoutManager =
            FlexboxLayoutManager(requireContext()).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.CENTER // Центрирование элементов
                flexWrap = FlexWrap.WRAP
            }

        rvPictures.adapter = adapter
        rvPictures.layoutManager = layoutManager

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listPicturesViewModel.rvPictures.collect { picture ->
                    adapter.updateList(picture)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            listPicturesViewModel.addPictures(getAllMedia(requireContext(), bucketID))
        }

        binding.flShare.setOnClickListener {
            if (listpicture.size > 0) {
                listpicture.forEach {
                    val type =
                        requireContext()
                            .contentResolver
                            .getType(it.uri)

                    if (type?.contains("image") == true) {
                        val sendIntent = Intent()
                        sendIntent.setAction(Intent.ACTION_SEND)
                        sendIntent.setType(type)
                        sendIntent.putExtra(Intent.EXTRA_STREAM, it.uri)
//                        sendIntent.putExtra(Intent.EXTRA_TEXT, sendCommentText) //TODO
                        startActivity(sendIntent)
                    }
                    if (type?.contains("video") == true) {
                        val sendIntent = Intent()
                        sendIntent.setAction(Intent.ACTION_SEND)
                        sendIntent.setType(type)
                        sendIntent.putExtra(Intent.EXTRA_STREAM, it.uri)
//                        sendIntent.putExtra(Intent.EXTRA_TEXT, sendCommentText) //TODO
                        startActivity(sendIntent)
                    }
                }
            }
            listpicture.clear()
        }

        binding.flCommentate.setOnClickListener {
            if (listpicture.size > 0) {
                listpicture.forEach {
                    TODO(
                        "Открыть диалог для ввода комментария" +
                            "с кнопками |отмена|  |ок|",
                    )
//                    Log.d("PrintF", it.uri.toString())
//                    val bundle =
//                        bundleOf(
//                            "mediaUri" to it.uri,
//                            "mediaPath" to it.path,
//                            "mediaDur" to it.duration,
//                        )
//                    findNavController().navigate(R.id.action_fullscreenPicture_to_photoStoryFragment, bundle)
                }
            }
            listpicture.clear()
        }

        binding.flToAlbum.setOnClickListener {
            if (listpicture.size > 0) {
                listpicture.forEach {
                    TODO(
                        "Открытие списка альбомов" +
                            "тык на альбом" +
                            "открытие диалога CopyMoveDialogFragment()",
                    )
                }
            }
            listpicture.clear()
        }
        binding.flDelete.setOnClickListener {
            if (listpicture.size > 0) {
                listpicture.forEach {
                    val converUri = convertUri(it.path, it.uri)
                    println(it.uri)
                    println(it.path)
                    println(convertUri(it.path, it.uri))
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                            // Android 11+ - используем createDeleteRequest
                            val pendingIntent =
                                MediaStore.createDeleteRequest(
                                    requireContext().contentResolver,
                                    listOf(converUri),
                                )
                            activity?.startIntentSenderForResult(
                                pendingIntent.intentSender,
                                PERMISSION_REQUEST_CODE,
                                null,
                                0,
                                0,
                                0,
                                null,
                            )
                        }
                        Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
                            // Android 10 - используем обработку RecoverableSecurityException
                            // deleteFileOnApi29(converUri) //TODO
                        }
                    }
                }
            }

            listpicture.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMediaSelectedListener(isVisible: Boolean) {
        if (isVisible) {
            binding.llContainerOptions.visibility = View.VISIBLE
        } else {
            binding.llContainerOptions.visibility = View.INVISIBLE
            listpicture.clear()
        }
    }
}
