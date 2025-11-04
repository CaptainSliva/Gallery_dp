package com.example.gallery_dp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery_dp.R
import com.example.gallery_dp.change_listener.AlbumChangeType
import com.example.gallery_dp.change_listener.OnMediaSelectedListener
import com.example.gallery_dp.data.Picture
import com.example.gallery_dp.data.allAlbums
import com.example.gallery_dp.data.listpicture
import com.example.gallery_dp.databinding.ItemMiniatureBinding

class PicturesRVadapter(
    private var listMiniatures: MutableList<Picture>,
    private val listener: OnMediaSelectedListener,
) : RecyclerView.Adapter<PicturesRVadapter.ViewHolder>() {
//    private val VIEW_TYPE_ITEM = 0
//    private val VIEW_TYPE_LOADING = 1

//    private var onLoadMoreListener: OnLoadMoreListener? = null
//    private var isLoading = false
//    private var noMore = false

//    override fun getItemViewType(position: Int): Int =
//        if (position == listThumbnails.size - 1 && isLoading && !noMore) {
//            VIEW_TYPE_LOADING
//        } else {
//            VIEW_TYPE_ITEM
//        }

    private var isSelected = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            ItemMiniatureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = listMiniatures[position]
        holder.thumbnail.setImageBitmap(item.thumbnail)
        holder.timeline.text = item.duration
        if (isSelected) {
            holder.selector.visibility = View.VISIBLE
        } else {
            holder.selector.isChecked = false
            holder.selector.visibility = View.INVISIBLE
        }
        if (item.duration.isNotEmpty()) {
            holder.timeline.text = item.duration
        }

        holder.itemView.setOnClickListener { view ->
            val bundle =
                bundleOf(
                    "mediaUri" to item.uri.toString(),
                    "mediaPath" to item.path,
                    "mediaDur" to item.duration,
                    "positionPicture" to position,
                )
            println(item)
            view.findNavController().navigate(R.id.action_listPicturesFragment_to_fullscreenMediaFragment, bundle)
        }
        holder.itemView.setOnLongClickListener { view ->
            listpicture.clear()
            holder.selector.isChecked = true
            isSelected = !isSelected
            listener.onMediaSelectedListener(isSelected)
            notifyDataSetChanged()

            true
        }
        holder.selector.setOnCheckedChangeListener { selector, isChecked ->
            if (isChecked) {
                listpicture.add(listMiniatures[position])
            } else {
                listpicture.remove(listMiniatures[position])
            }
        }
    }

    override fun getItemCount(): Int = listMiniatures.size

    fun updateList(
        newList: List<Picture>,
        changeType: AlbumChangeType,
    ) {
        when (changeType) {
            AlbumChangeType.ADD -> {
                val insertFromPosition = listMiniatures.size
                listMiniatures = newList.toMutableList()
                notifyItemRangeInserted(insertFromPosition, listMiniatures.size)
            }
            AlbumChangeType.DELETE -> {
                val listBID = newList.map { it.bID }
                println("size ${listMiniatures.size}")
                listMiniatures.forEachIndexed { i, it ->
                    if (it.bID in listBID) {
                        listMiniatures.removeAt(i)
                        notifyItemRemoved(i)
                    }
                }
            }
            else -> {}
        }
    }

    inner class ViewHolder(
        binding: ItemMiniatureBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        val thumbnail = binding.ivThumbnail
        val timeline = binding.tvTiming
        val selector = binding.cbSelect
        val miniatureContainer = binding.miniatureContainer
    }
}
