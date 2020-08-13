package com.hex.shaadi.ui

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.hex.shaadi.R
import com.hex.shaadi.model.MatchStatus.Companion.ACCEPTED
import com.hex.shaadi.model.MatchStatus.Companion.DECLINED
import com.hex.shaadi.model.Profile
import kotlinx.android.synthetic.main.item_match.view.*
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MatchesViewHolder(
    itemView: View,
    private val weakMatchesActionsInterface: WeakReference<MatchesActionsInterface>
) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("SetTextI18n")
    fun onBind(profile: Profile) {
        with(itemView) {
            name.text = profile.name
            avatar.setImageURI(profile.thumbnail)

            // Since desired fields are not present in the response payload of the api call, using
            // uuid as a place holder
            subtext1.text = "${profile.age}, ${profile.uuid}"
            subtext2.text = "${profile.city}, ${profile.state}"

            button_accept.setOnClickListener { changeStatus(profile, ACCEPTED) }
            button_decline.setOnClickListener { changeStatus(profile, DECLINED) }
        }
        setupMatchStatus(profile.matchStatus)
    }

    private fun changeStatus(profile: Profile, newStatus: Int) =
        with(itemView.context as LifecycleOwner) {
            weakMatchesActionsInterface.get()?.also {
                lifecycleScope.launch {
                    // Update profile status in cached data-set
                    profile.matchStatus = newStatus
                    // handles viewholder invalidation
                    it.setStatus(adapterPosition, profile.uuid, newStatus)
                }
            }
        }

    private fun setupMatchStatus(matchStatus: Int) {
        when (matchStatus) {
            ACCEPTED -> setupAsMatchAccepted()
            DECLINED -> setupAsMatchDeclined()
            else -> setupAsMatchStatusUnknown()
        }
    }

    private fun setupAsMatchAccepted() = with(itemView) {
        button_accept.isVisible = false
        button_decline.isVisible = false

        match_status.isVisible = true
        match_status.text = context.getString(R.string.invite_accepted)
    }

    private fun setupAsMatchDeclined() = with(itemView) {
        button_accept.isVisible = false
        button_decline.isVisible = false

        match_status.isVisible = true
        match_status.text = context.getString(R.string.invite_declined)
    }

    private fun setupAsMatchStatusUnknown() = with(itemView) {
        button_accept.isVisible = true
        button_decline.isVisible = true
        match_status.isVisible = false
    }
}