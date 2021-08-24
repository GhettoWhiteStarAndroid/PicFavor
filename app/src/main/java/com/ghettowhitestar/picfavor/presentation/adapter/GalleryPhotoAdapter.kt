package com.ghettowhitestar.picfavor.presentation.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ghettowhitestar.picfavor.R
import com.ghettowhitestar.picfavor.data.PicsumPhoto
import com.ghettowhitestar.picfavor.databinding.ItemPhotoBinding

/**
 * Единый адаптер для экрана лайкнутух и рандомных кратинок
 * @property items список отображаемых фотографий
 * @property listenerLike лямбда на метод в viewmodel при изменении лайка
 */
class GalleryPhotoAdapter(
    private var items: List<PicsumPhoto> = mutableListOf(),
    private val listenerLike: (PicsumPhoto, Bitmap) -> Unit
) :
    RecyclerView.Adapter<GalleryPhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    fun updateItems(items: MutableList<PicsumPhoto>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(items[position], listenerLike)
    }

    override fun getItemCount(): Int =items.size

    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PicsumPhoto, listenerLike: (PicsumPhoto, Bitmap) -> Unit) {
            binding.apply {
                Glide.with(itemView)
                    .asBitmap()
                    .load(photo.downloadUrl)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .into(pictureImage)

                textViewUserName.text = photo.author
                binding.likeButton.setImageResource(setLikeImage(photo.isLikedPhoto))
                likeButton.setOnClickListener {
                    val bitmap = (pictureImage.drawable as BitmapDrawable).bitmap
                    listenerLike(photo, bitmap)
                }
            }
        }

        /** Устанавливаем иконку лайка в зависимости лайкнут/не лайкнут */
        private fun setLikeImage(isLike: Boolean) =
            if (isLike)
                R.drawable.ic_like
            else
                R.drawable.ic_dislike_white
    }
}