package com.mess.messcartoon.utils

import android.net.Uri
import androidx.navigation.NavController

object NavigationHelper {

    /**
     * 跳转到漫画详情页
     * @param navController 当前 NavController
     * @param name 漫画名称
     * @param pathWord 漫画路径
     */
    fun navigateToComicDetail(navController: NavController, name: String, pathWord: String) {
        val encodedName = Uri.encode(name)
        val encodedPath = Uri.encode(pathWord)
        navController.navigate("comic_detail/$encodedName/$encodedPath")
    }

    /**
     * 跳转到分类/专题列表页
     */
    fun navigateToDetailList(navController: NavController, title: String, type: String) {
        val encodedTitle = Uri.encode(title)
        val encodedType = Uri.encode(type)
        navController.navigate("detail_list/$encodedTitle/$encodedType")
    }

    /**
     * 跳转到排行榜页
     */
    fun navigateToRankList(navController: NavController, title: String, type: String) {
        val encodedTitle = Uri.encode(title)
        val encodedType = Uri.encode(type)
        navController.navigate("rank_list/$encodedTitle/$encodedType")
    }

    // 你也可以继续扩展其他页面跳转函数
    /**
     * 跳转到漫画章节详情
     */
    fun navigateToChapter(navController: NavController, title: String, pathWord: String, uuid: String) {
        val encodedTitle = Uri.encode(title)
        val encodedPathWord = Uri.encode(pathWord)
        val encodedUuid = Uri.encode(uuid)
        navController.navigate("chapter/$encodedTitle/$encodedPathWord/$encodedUuid")
    }

}
