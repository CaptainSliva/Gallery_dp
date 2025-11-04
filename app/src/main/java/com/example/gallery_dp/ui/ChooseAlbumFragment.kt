package com.example.gallery_dp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gallery_dp.adapters.ChooseAlbumRVadapter
import com.example.gallery_dp.change_listener.AlbumChangeType
import com.example.gallery_dp.change_listener.OnChangeAlbumListener
import com.example.gallery_dp.data.Album
import com.example.gallery_dp.data.allAlbums
import com.example.gallery_dp.databinding.FragmentChooseAlbumBinding
import com.example.gallery_dp.other.SpacingItemDecoration
import com.example.gallery_dp.utils.FunctionsApp

class ChooseAlbumFragment :
    Fragment(),
    OnChangeAlbumListener {
    private var _binding: FragmentChooseAlbumBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChooseAlbumBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val rvAlbums = binding.rvAlbums
        val adapter = ChooseAlbumRVadapter(childFragmentManager, allAlbums)
        rvAlbums.adapter = adapter
        rvAlbums.addItemDecoration(
            SpacingItemDecoration(
                FunctionsApp.dpToPx(
                    binding.root.context,
                    1,
                ),
            ),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateAlbum(
        album: Album,
        changeType: AlbumChangeType,
    ) {
        // TODO DO DO DO DO DO!!!!!
//        (11.05-11.06)Сделать обработчик диалога удаления
//        (11.07) Проверить работу кнопки "поделиться"
//        (11.08) Сделать реактивные функции в бд
//        (11.09) Решить проблему с размножением фоток при выходе и возврате в фрагмент с фотками
//        (11.09) Сделать обновление количества фоток в альбомах
//        (11.09) Сделать обновление фоток в альбоме если произошло перемещение фото или удаление (действие одно и то же по сути)

        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStackImmediate()
        } else {
            // Если нет дочерних фрагментов, закрываем родительский
            parentFragmentManager.popBackStack()
        }
    }
}
