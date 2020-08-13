package com.hex.shaadi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hex.shaadi.R
import com.hex.shaadi.model.Profile
import java.lang.ref.WeakReference

class MatchesAdapter(
    private val matchesActionsInterface: MatchesActionsInterface
) : RecyclerView.Adapter<MatchesViewHolder>() {

    private var matchesList = emptyList<Profile>()

    fun setData(newMatchesList: List<Profile>) {
        matchesList = newMatchesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchesViewHolder(view, WeakReference(matchesActionsInterface))
    }

    override fun getItemCount(): Int {
        return matchesList.size
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.onBind(matchesList[position])
    }
}
