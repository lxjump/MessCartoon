package com.mess.messcartoon.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.R
import com.mess.messcartoon.model.Category
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.viewmodel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val navController = LocalNavController.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val showBottomBar = when (currentDestination) {
        "home", "search", "profile", "shelf", "category" -> true
        else -> false  // 其他页面都不显示
    }

    Scaffold(
//        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text(stringResource(R.string.home)) },
                        selected = selectedTab == 0,
                        onClick = {
                            selectedTab = 0
                            navController.navigate("home") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant // 选中时底部指示器颜色
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Menu, contentDescription = null) },
                        label = { Text(stringResource(R.string.category)) },
                        selected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                            navController.navigate("category") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant // 选中时底部指示器颜色
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = null) },
                        label = { Text(stringResource(R.string.shelf)) },
                        selected = selectedTab == 2,
                        onClick = {
                            selectedTab = 2
                            navController.navigate("shelf") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant // 选中时底部指示器颜色
                        )
                    )
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
//                        label = { Text(stringResource(R.string.mine)) },
//                        selected = false,
//                        onClick = {
//                            selectedTab = 3
//                            navController.navigate("mine") {
//                                launchSingleTop = true
//                                restoreState = true
//                            }
//                        }
//                    )
                }
            }
        }
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("category") { CategoryScreen() }
            composable("shelf") { ComicShelfScreen() }
            composable("detail_list/{title}/{type}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                LogUtils.d("ddd ${backStackEntry.arguments?.getString("type")}")
                val typeStr = backStackEntry.arguments?.getString("type") ?: DetailType.Recommend.typeName
                LogUtils.d("typeStr : $typeStr")
//                val type = DetailType.valueOf(typeStr)
                val type = DetailType.fromTypeName(typeStr)
                LogUtils.d("**********************************")
                LogUtils.d("type = $type")
                DetailListScreen(title, type)
            }
            composable("rank_list/{title}/{type}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val typeStr = backStackEntry.arguments?.getString("type") ?: RankType.RankDay.typeName
                LogUtils.d("typeStr : $typeStr")
                val type = RankType.fromTypeName(typeStr)
                RankScreen(title, type)
            }
            composable("comic_detail/{name}/{path}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("name") ?: ""
                val pathWord = backStackEntry.arguments?.getString("path") ?: ""
                ComicDetailScreen(title, pathWord)
            }

            composable("chapter/{name}/{path}/{uuid}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("name") ?: ""
                val pathWord = backStackEntry.arguments?.getString("path") ?: ""
                val uuid = backStackEntry.arguments?.getString("uuid") ?: ""
                ComicChapterScreen(title, pathWord, uuid)
            }


        }

    }
}