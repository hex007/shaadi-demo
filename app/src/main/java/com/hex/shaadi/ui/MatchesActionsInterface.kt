package com.hex.shaadi.ui

interface MatchesActionsInterface {

    suspend fun setStatus(uuid: String, newStatus: Int)
}