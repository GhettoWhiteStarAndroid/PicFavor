package com.ghettowhitestar.picfavor.presentation.like

import com.ghettowhitestar.picfavor.core.BasePhotoFragment
import com.ghettowhitestar.picfavor.presentation.PhotoViewModel
import com.ghettowhitestar.picfavor.presentation.VisibilityStates
import com.ghettowhitestar.picfavor.utils.observe
import com.ghettowhitestar.picfavor.utils.toast

class LikesFragment : BasePhotoFragment() {

    override fun PhotoViewModel.observe() {
        observe(likedPhotoList) {
            it.let { items ->
                binding.layoutState(
                    if (it.isEmpty())
                        VisibilityStates.Empty
                    else
                        VisibilityStates.Success
                )
                adapter.updateItems(items)
            }
        }
        observe(toastMessage) {
            it.let { message ->
                context?.toast(message)
            }
        }
    }
}