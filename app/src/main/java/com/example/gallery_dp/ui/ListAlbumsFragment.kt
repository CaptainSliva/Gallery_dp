package com.example.gallery_dp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.gallery_dp.adapters.AlbumsRVadapter
import com.example.gallery_dp.change_listener.AlbumChangeType
import com.example.gallery_dp.change_listener.OnChangeAlbumListener
import com.example.gallery_dp.data.Album
import com.example.gallery_dp.data.allAlbums
import com.example.gallery_dp.databinding.ListAlbumsFragmentBinding
import com.example.gallery_dp.dialogs.CreateAlbumDialogFragment
import com.example.gallery_dp.other.SpacingItemDecoration
import com.example.gallery_dp.utils.FunctionsApp
import com.example.gallery_dp.view_model.ListAlbumsViewModel
import kotlinx.coroutines.launch

/**
 * A simple [androidx.fragment.app.Fragment] subclass as the default destination in the navigation.
 */

class ListAlbumsFragment :
    Fragment(),
    OnChangeAlbumListener {
    private var _binding: ListAlbumsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adapter: AlbumsRVadapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ListAlbumsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val listAlbumsViewModel = ViewModelProvider(this)[ListAlbumsViewModel::class.java]
        val rvAlbums = binding.rvAlbums
        val fab = binding.floatingActionButton
        adapter = AlbumsRVadapter(childFragmentManager, allAlbums)
        rvAlbums.adapter = adapter
        rvAlbums.addItemDecoration(
            SpacingItemDecoration(
                FunctionsApp.dpToPx(
                    binding.root.context,
                    1,
                ),
            ),
        )

        // Используется, если список адаптера изначально пуст
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                listAlbumsViewModel.rvAlbums.collect { albums ->
//                    adapter.updateList(albums)
//                }
//            }
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            listAlbumsViewModel.addAlbums(binding.root.context)
        }

        fab.setOnClickListener {
            CreateAlbumDialogFragment().show(childFragmentManager, "CREATE_ALBUM")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateAlbum(
        album: Album,
        changeType: AlbumChangeType,
    ) {
        adapter.updateList(listOf(album), changeType)
    }
}
