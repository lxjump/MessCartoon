package com.mess.messcartoon.navigation

object NavigationDestination {

    const val HOME = "home"

    const val COMIC_DETAIL = "comic_detail"
    const val COMIC_DETAIL_ROUTE = "$COMIC_DETAIL/{name}/{path}"

    const val DETAIL_LIST = "detail_list"
    const val DETAIL_LIST_ROUTE = "$DETAIL_LIST/{title}/{type}"

    const val RANK_LIST = "rank_list"
    const val RANK_LIST_ROUTE = "$RANK_LIST/{title}/{type}"

    // 如果后续你还要添加其他页面，按类似方式扩展
}
