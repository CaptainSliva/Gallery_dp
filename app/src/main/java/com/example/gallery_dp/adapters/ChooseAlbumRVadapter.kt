package com.example.gallery_dp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery_dp.data.Album
import com.example.gallery_dp.data.listpicture
import com.example.gallery_dp.databinding.ItemAlbumBinding
import com.example.gallery_dp.dialogs.CopyMoveDialogFragment

class ChooseAlbumRVadapter(
    private val childFragmentManager: FragmentManager,
    private val listAlbums: MutableList<Album>,
) : RecyclerView.Adapter<ChooseAlbumRVadapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = listAlbums[position]
        val context = holder.albumContainer.context
        holder.albumPreview.setImageBitmap(item.miniature)
        holder.albumName.text = item.name
        holder.albumItemsCount.text = item.itemsCount.toString()

        holder.itemView.setOnClickListener { view ->
            CopyMoveDialogFragment(listpicture.map { it.uri }, item.name).show(childFragmentManager, "COPY_MOVE_DIALOG")
        }
    }

    override fun getItemCount(): Int = listAlbums.size

    inner class ViewHolder(
        binding: ItemAlbumBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        val albumContainer = binding.albumContainer
        val albumPreview = binding.ivAlbumPreview
        val albumName = binding.tvAlbumName
        val albumItemsCount = binding.tvAlbumItemsCount
    }
}
