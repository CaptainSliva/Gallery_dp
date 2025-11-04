package com.example.gallery_dp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery_dp.R
import com.example.gallery_dp.change_listener.AlbumChangeType
import com.example.gallery_dp.data.Album
import com.example.gallery_dp.data.allAlbums
import com.example.gallery_dp.databinding.ItemAlbumBinding
import com.example.gallery_dp.utils.FunctionsApp.handleMenuItemClick

class AlbumsRVadapter(
    private val childFragmentManager: FragmentManager,
    private val listAlbums: MutableList<Album>,
) : RecyclerView.Adapter<AlbumsRVadapter.ViewHolder>() {
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

        val bundle =
            bundleOf(
                "bucketID" to item.bID,
                "albumName" to item.name,
                "amountOfItems" to item.itemsCount,
            )
        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_listAlbumsFragment_to_listPicturesFragment, bundle)
        }

        holder.itemView.setOnLongClickListener { view ->
            val popupMenu =
                PopupMenu(context, view).apply {
                    menuInflater.inflate(R.menu.album_popup, menu)

                    setOnMenuItemClickListener {
                        handleMenuItemClick(childFragmentManager, context, it, item)
                        true
                    }
                }
            popupMenu.show()

            true
        }
    }

    override fun getItemCount(): Int = listAlbums.size

    fun updateList(
        newList: List<Album>,
        changeType: AlbumChangeType,
    ) {
//        listAlbums = newList.toMutableList()
        when (changeType) {
            AlbumChangeType.ADD -> {
                if (newList.size == 1) {
                    println(allAlbums.map { it.name })
                    val albumIndex = allAlbums.map { it.name }.indexOf(newList[0].name)
                    listAlbums.add(albumIndex, newList[0])
                    if (listAlbums[listAlbums.size - 1].name == newList[0].name) listAlbums.removeAt(listAlbums.size - 1)
                    notifyItemInserted(albumIndex)
                } else {
                    val insertFromPosition = listAlbums.size
                    listAlbums.addAll(newList)
                    notifyItemRangeInserted(insertFromPosition, listAlbums.size)
                }
            }
            AlbumChangeType.RENAME -> {
                allAlbums.forEachIndexed { i, it ->
                    if (it.bID == newList[0].bID) {
                        listAlbums[i] = newList[0]
                        allAlbums[i] = newList[0]
                        notifyItemChanged(i)
                    }
                }
            }
            AlbumChangeType.DELETE -> {
                listAlbums.clear()
                listAlbums.addAll(allAlbums)
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(
        binding: ItemAlbumBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        val albumContainer = binding.albumContainer
        val albumPreview = binding.ivAlbumPreview
        val albumName = binding.tvAlbumName
        val albumItemsCount = binding.tvAlbumItemsCount
    }
}
