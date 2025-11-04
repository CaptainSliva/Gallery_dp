package com.example.gallery_dp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.gallery_dp.R
import com.example.gallery_dp.adapters.PicturesRVadapter
import com.example.gallery_dp.change_listener.AlbumChangeType
import com.example.gallery_dp.change_listener.OnChangeAlbumListener
import com.example.gallery_dp.change_listener.OnMediaSelectedListener
import com.example.gallery_dp.data.Album
import com.example.gallery_dp.data.PERMISSION_DELETE_REQUEST_CODE
import com.example.gallery_dp.data.allAlbums
import com.example.gallery_dp.data.listpicture
import com.example.gallery_dp.databinding.ListPicturesFragmentBinding
import com.example.gallery_dp.dialogs.AddCommentDialogFragment
import com.example.gallery_dp.dialogs.DeleteDialogFragment
import com.example.gallery_dp.other.SpacingItemDecoration
import com.example.gallery_dp.utils.FunctionsMediaStore
import com.example.gallery_dp.utils.FunctionsMediaStore.deleteMediaFile
import com.example.gallery_dp.view_model.ListPicturesViewModel
import kotlinx.coroutines.launch

/**
 * A simple [androidx.fragment.app.Fragment] subclass as the second destination in the navigation.
 */
class ListPicturesFragment :
    Fragment(),
    OnMediaSelectedListener,
    OnChangeAlbumListener {
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
        adapter = PicturesRVadapter(mutableListOf(), this)

//        val layoutManager =
//            GridLayoutManager(
//                requireContext(),
//                3,
//            )

        rvPictures.adapter = adapter
//        rvPictures.layoutManager = layoutManager
        rvPictures.addItemDecoration(SpacingItemDecoration(2))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listPicturesViewModel.rvPictures.collect { picture ->
                    adapter.updateList(picture, AlbumChangeType.ADD)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            listPicturesViewModel.addPictures(
                FunctionsMediaStore.getAllMedia(
                    requireContext(),
                    bucketID,
                ),
            )
        }

        binding.flShare.setOnClickListener {
            if (listpicture.isNotEmpty()) {
                // val sendCommentText = db.findImageByHash(md5(it.thumbnail))
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE)
                sendIntent.setType("*/*")
                sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(listpicture.map { it.uri }))
//                        sendIntent.putExtra(Intent.EXTRA_TEXT, sendCommentText) //TODO
                startActivity(sendIntent)
            }
        }

        binding.flCommentate.setOnClickListener {
            if (listpicture.isNotEmpty()) {
                AddCommentDialogFragment().show(childFragmentManager, "COMMENTATE_MEDIA")
            }
        }

        binding.flToAlbum.setOnClickListener { view ->
            if (listpicture.isNotEmpty()) {
                view.findNavController().navigate(R.id.action_listPicturesFragment_to_chooseAlbumFragment)
            }
        }
        binding.flDelete.setOnClickListener {
            if (listpicture.isNotEmpty()) {
//                val deleteRequestLauncher =
//                    registerForActivityResult(
//                        ActivityResultContracts.StartIntentSenderForResult(),
//                    ) { result ->
//                        if (result.resultCode == Activity.RESULT_OK) {
//                            // ✅ Пользователь подтвердил удаление
//                            Log.d("Gallery", "Файл(ы) успешно удалены")
//                        } else {
//                            Log.d("Gallery", "Удаление отменено пользователем")
//                        }
//                    }
//                deleteMedia(requireContext(), listOf(uri)) { intentSender ->
//                    deleteRequestLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
//                }

                // TODO на 29 api ПОЧИНИЛОСЬ!!!??
                // TODO на 36 api ПОЧИНИЛОСЬ!!!?? непонял... ↓ не актуально, получается
//                2025-10-27 19:29:05.458 28547-28547 AndroidRuntime          context.photo                        E  FATAL EXCEPTION: main
//                Process: context.photo, PID: 28547
//                java.lang.IllegalArgumentException: All requested items must be Media items
//                at android.database.DatabaseUtils.readExceptionFromParcel(DatabaseUtils.java:183)
//                at android.database.DatabaseUtils.readExceptionFromParcel(DatabaseUtils.java:153)
//                at android.content.ContentProviderProxy.call(ContentProviderNative.java:764)
//                at android.content.ContentResolver.call(ContentResolver.java:2468)
//                at android.provider.MediaStore.createRequest(MediaStore.java:1544)
//                at android.provider.MediaStore.createDeleteRequest(MediaStore.java:1766)
//                at com.example.gallery_dp.ListPictures.ListPicturesFragment.onViewCreated$lambda$7(ListPicturesFragment.kt:155)
//                at com.example.gallery_dp.ListPictures.ListPicturesFragment.$r8$lambda$9d9WkKDGrbN6EJgpBEdOiN6Wkgk(Unknown Source:0)
//                TODO ↓ актуально получается для альбомов которые делал я
//                2025-10-28 13:56:50.451  4595-4595  AndroidRuntime          context.photo                        E  FATAL EXCEPTION: main
//                Process: context.photo, PID: 4595
//                java.lang.IllegalArgumentException: All requested items must be Media items
//                at android.database.DatabaseUtils.readExceptionFromParcel(DatabaseUtils.java:172)
//                at android.database.DatabaseUtils.readExceptionFromParcel(DatabaseUtils.java:142)
//                at android.content.ContentProviderProxy.call(ContentProviderNative.java:764)
//                at android.content.ContentResolver.call(ContentResolver.java:2466)
//                at android.provider.MediaStore.createRequest(MediaStore.java:1189)
//                at android.provider.MediaStore.createDeleteRequest(MediaStore.java:1352)
//                at com.example.gallery_dp.ui.ListPicturesFragment.onViewCreated$lambda$5(ListPicturesFragment.kt:153) - val pendingIntent = MediaStore.createDeleteRequest( requireContext().contentResolver, listOf(converUri))
//                at com.example.gallery_dp.ui.ListPicturesFragment.$r8$lambda$fI8N2E9RhmsibUb-5lKtfZdNArs(Unknown Source:0)
//                TODO ↓ ещё вот так умеет
//                2025-10-28 13:58:34.389  4690-4690  AndroidRuntime          context.photo                        E  FATAL EXCEPTION: main
//                Process: context.photo, PID: 4690
//                java.lang.IllegalArgumentException: All requested items must be Media items
//                at android.database.DatabaseUtils.readExceptionFromParcel(DatabaseUtils.java:172)
//                at android.database.DatabaseUtils.readExceptionFromParcel(DatabaseUtils.java:142)
//                at android.content.ContentProviderProxy.call(ContentProviderNative.java:764)
//                at android.content.ContentResolver.call(ContentResolver.java:2466)
//                at android.provider.MediaStore.createRequest(MediaStore.java:1189)
//                at android.provider.MediaStore.createDeleteRequest(MediaStore.java:1352)
//                at com.example.gallery_dp.ui.ListPicturesFragment.onViewCreated$lambda$5(ListPicturesFragment.kt:167) - val pendingIntent = MediaStore.createDeleteRequest( requireContext().contentResolver, listOf(converUri))
//                at com.example.gallery_dp.ui.ListPicturesFragment.$r8$lambda$fI8N2E9RhmsibUb-5lKtfZdNArs(Unknown Source:0)
//                at com.example.gallery_dp.ui.ListPicturesFragment$$ExternalSyntheticLambda3.onClick(D8$$SyntheticClass:0)
//                at android.view.View.performClick(View.java:7659)
//                at android.view.View.performClickInternal(View.java:7636)
//                at android.view.View.-$$Nest$mperformClickInternal(Unknown Source:0)
//                at android.view.View$PerformClick.run(View.java:30156)
//                at android.os.Handler.handleCallback(Handler.java:958)
//                at android.os.Handler.dispatchMessage(Handler.java:99)
//                at android.os.Looper.loopOnce(Looper.java:205)
//                at android.os.Looper.loop(Looper.java:294)
//                at android.app.ActivityThread.main(ActivityThread.java:8177)
//                at java.lang.reflect.Method.invoke(Native Method)
//                at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:552)
//                at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:971)

//                listpicture.forEach {
//                    val converUri = convertUri(it.path, it.uri)
//                    println(it.uri)
//                    println(it.path)
//                    println(FunctionsUri.convertUri(it.path, it.uri))
//                    deleteMediaFile(requireContext(), requireActivity(), converUri)
//                }

                DeleteDialogFragment(requireContext(), allAlbums[0], false).show(childFragmentManager, "COMMENTATE_MEDIA")
            }
        }
    }

//    when {
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
//            // Android 11+ - используем createDeleteRequest
//            val pendingIntent =
//                MediaStore.createDeleteRequest(
//                    requireContext().contentResolver,
//                    listOf(converUri),
//                )
//            activity?.startIntentSenderForResult(
//                pendingIntent.intentSender,
//                PERMISSION_DELETE_REQUEST_CODE,
//                null,
//                0,
//                0,
//                0,
//                null,
//            )
//        }
//        Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
//            // Android 10 - используем обработку RecoverableSecurityException
//            // deleteFileOnApi29(converUri) //TODO
//        }
//    }

//    fun deleteMediaFile(
//        context: Context,
//        uri: Uri,
//    ): Boolean =
//        try {
//            val resolver = context.contentResolver
//            val rowsDeleted = resolver.delete(uri, null, null)
//            rowsDeleted > 0
//        } catch (e: RecoverableSecurityException) {
//            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
//                val intentSender = e.userAction.actionIntent.intentSender
//                startIntentSenderForResult(
//                    intentSender,
//                    PERMISSION_DELETE_REQUEST_CODE,
//                    null,
//                    0,
//                    0,
//                    0,
//                    null,
//                )
//            } else {
//                val pendingIntent =
//                    MediaStore.createDeleteRequest(
//                        requireContext().contentResolver,
//                        listOf(uri),
//                    )
//                activity?.startIntentSenderForResult(
//                    pendingIntent.intentSender,
//                    PERMISSION_DELETE_REQUEST_CODE,
//                    null,
//                    0,
//                    0,
//                    0,
//                    null,
//                )
//            }
//
//            false
//        } catch (e: Exception) {
//            e.printStackTrace()
//            false
//        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PERMISSION_DELETE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    println("Активность ОК!")
                    // data?.extras?.get("data") as Bitmap
                    // db.deleteCommentByHash
                } else {
                    println("Активность НЕТ!")
                }
            }
        }
    }

    override fun onMediaSelectedListener(isVisible: Boolean) {
        if (isVisible) {
            binding.llContainerOptions.visibility = View.VISIBLE
        } else {
            binding.llContainerOptions.visibility = View.INVISIBLE
            listpicture.clear()
        }
    }

    override fun onCreateAlbum(
        album: Album,
        changeType: AlbumChangeType,
    ) {
        // adapter.updateList(listpicture, changeType)
        if (changeType == AlbumChangeType.DELETE) {
            deleteMediaFile(
                requireContext(),
                requireActivity(),
                { intentSender ->
                    startIntentSenderForResult(
                        intentSender,
                        PERMISSION_DELETE_REQUEST_CODE,
                        null,
                        0,
                        0,
                        0,
                        null,
                    )
                },
            )
        }
    }
}
