package com.hex.shaadi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hex.shaadi.MainActivityViewModel
import com.hex.shaadi.R
import com.hex.shaadi.model.Profile
import java.lang.ref.WeakReference

class MatchesAdapter(
    mainActivityViewModel: MainActivityViewModel
) : RecyclerView.Adapter<MatchesViewHolder>() {

    private var matchesList = emptyList<Profile>()

    private val matchesActionsInterface: MatchesActionsInterface =
        object : MatchesActionsInterface {
            override suspend fun setStatus(position: Int, uuid: String, newStatus: Int) {
                mainActivityViewModel.setStatus(uuid, newStatus)
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(position)
                }
            }
        }

    fun setData(newMatchesList: List<Profile>) {
        matchesList = newMatchesList
        notifyDataSetChanged()
    }

    private val weakMatchesActionsInterface = WeakReference(matchesActionsInterface)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchesViewHolder(view, weakMatchesActionsInterface)
    }

    override fun getItemCount(): Int {
        return matchesList.size
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.onBind(matchesList[position])
    }
}
