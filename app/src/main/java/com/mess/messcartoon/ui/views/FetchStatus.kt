package com.mess.messcartoon.ui.views

enum class FetchStatus(val status: Int) {
    Loading(0),
    FetchSuccess(1),
    FetchFailed(-1)
}