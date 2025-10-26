package com.example.gallery_dp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.gallery_dp.ChangeListener.OnChangeAlbumListener
import com.example.gallery_dp.ChangeListener.OnMediaSelectedListener
import com.example.gallery_dp.CustomClasses.Picture
import com.example.gallery_dp.Functions.FunctionsApp
import com.example.gallery_dp.Functions.FunctionsApp.handleMenuItemClick
import com.example.gallery_dp.R
import com.example.gallery_dp.databinding.ItemMiniatureBinding
import com.example.gallery_dp.listpicture
import com.google.android.material.checkbox.MaterialCheckBox

class PicturesRVadapter(
    private val childFragmentManager: FragmentManager,
    private var listMiniatures: List<Picture>,
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
        val context = holder.miniatureContainer.context
        holder.thumbnail.setImageBitmap(item.thumbnail)
        holder.timline.text = item.duration
        if (isSelected) {
            holder.selector.visibility = View.VISIBLE
        } else {
            holder.selector.visibility = View.INVISIBLE
        }
        if (item.duration.isNotEmpty()) {
            holder.timline.text = item.duration
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

//    fun myViewHolder() {
//        val myView: View = inflater.inflate(R.layout.item_home, parent, false)
//        val params = myView.getLayoutParams() as GridLayoutManager.LayoutParams
//        params.height = (parent.getMeasuredHeight() / 2) - dim24
//        myView.setLayoutParams(params)
//        return myViewHolder(myView)
//    }

    fun updateList(newList: List<Picture>) {
        val insertFromPosition = listMiniatures.size
        listMiniatures = newList.toMutableList()
        notifyItemInserted(listMiniatures.size)
    }

    inner class ViewHolder(
        binding: ItemMiniatureBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        val thumbnail = binding.ivThumbnail
        val timline = binding.tvTiming
        val selector = binding.cbSelect
        val miniatureContainer = binding.miniatureContainer
    }
}
