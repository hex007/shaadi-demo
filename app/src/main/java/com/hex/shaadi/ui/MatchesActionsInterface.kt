package com.hex.shaadi.ui

interface MatchesActionsInterface {

    suspend fun setStatus(position: Int, uuid: String, newStatus: Int)
}