package com.ghettowhitestar.picfavor.presentation.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
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
    private val listenerLike: (PicsumPhoto, Bitmap) -> Unit
) :
    ListAdapter<PicsumPhoto, GalleryPhotoAdapter.PhotoViewHolder>(PhotoDiff) {

    object PhotoDiff: DiffUtil.ItemCallback<PicsumPhoto>(){
        override fun areContentsTheSame(oldItem: PicsumPhoto, newItem: PicsumPhoto): Boolean {
            return oldItem == newItem
        }
        override fun areItemsTheSame(oldItem: PicsumPhoto, newItem: PicsumPhoto): Boolean {
            return if( oldItem.id == newItem.id)
           {
               oldItem.isLikedPhoto == newItem.isLikedPhoto
           }else{
               false
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(currentList[position], listenerLike)
    }

    override fun getItemCount(): Int = currentList.size

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
                setLikeImage(photo.isLikedPhoto)
                pictureImage.tag = photo.downloadUrl
                likeButton.setOnClickListener {
                 setLikeImage(!photo.isLikedPhoto)
                    val bitmap = (pictureImage.drawable as BitmapDrawable).bitmap
                    listenerLike(photo, bitmap)
                }
            }
        }

        /** Устанавливаем иконку лайка в зависимости лайкнут/не лайкнут */
        private fun setLikeImage(isLike: Boolean) {
            if (isLike)
                binding.likeButton.setImageResource(R.drawable.ic_like)
            else
                binding.likeButton.setImageResource(R.drawable.ic_dislike_white)
        }
    }
}