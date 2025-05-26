package com.mess.messcartoon.ui.views

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.R
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.model.RankComic
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.viewmodel.DetailListScreenViewModel
import com.mess.messcartoon.viewmodel.HomeViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun DetailListScreen(
    title: String,
    type: DetailType,
    viewModel: DetailListScreenViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val navController = LocalNavController.current

    // 初始加载
    LaunchedEffect(Unit) {
        if (viewModel.items.isEmpty()) {
            viewModel.loadData(type, loadMore = false)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding() // ✅ 保留底部安全区
            .windowInsetsPadding(WindowInsets.displayCutout) // 避开挖孔
            .statusBarsPadding(), // 避开状态栏（可能包含刘海）
    ) {
        // 手动构建 TopBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 内容区域

        when (val state = uiState) {
            is DetailListScreenViewModel.UiState.Loading -> {
                LoadingView()
            }

            is DetailListScreenViewModel.UiState.Error -> {
                ErrorView(onRetry = { viewModel.loadData(type) })
            }

            is DetailListScreenViewModel.UiState.Success -> {
                DetailComicView(state.data, viewModel, type, onClick = {

                })
            }
        }
    }

}

@OptIn(FlowPreview::class)
@Composable
fun DetailComicView(
    comics: List<Comic>, viewModel: DetailListScreenViewModel, type: DetailType, onClick: () -> Unit
) {


    val lazyGridState = rememberLazyGridState()
    // 滚动监听
    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo }
            .debounce(300) // 防抖
            .collect { visibleItems ->
                LogUtils.d("visibleItems ${visibleItems.isNotEmpty()}")
                if (visibleItems.isNotEmpty()) {
                    val totalItems = viewModel.items.size
                    val lastVisibleIndex = visibleItems.last().index

                    // 接近底部时加载更多（距离底部3项）
                    LogUtils.d("接近底部时加载更多")
                    if (lastVisibleIndex >= totalItems - 3 &&
                        !viewModel.isLoading.value &&
                        !viewModel.isEndReached.value
                    ) {
                        viewModel.loadData(type, loadMore = true)
                    }
                }
            }
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp, top = 12.dp),
        columns = GridCells.Fixed(3),
        state = lazyGridState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(viewModel.items) { item ->
            LogUtils.d("item 是否为空")
            ComicCard(name =item.name, coverUrl = item.cover, item.pathWord)
        }
        // 底部加载状态
        item(span = { GridItemSpan(maxLineSpan) }) {
            LogUtils.d("viewModel.isLoading.value = ${viewModel.isLoading.value}")
            when {
                viewModel.isLoading.value -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 56.dp),
                        contentAlignment = Alignment.Center

                    ) {
                        LottieLoadingIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                        )
                    }
                }

                viewModel.isEndReached.value -> {
                    Text(
                        text = "已经到底了",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }

                viewModel.loadMoreErrorState.value != null -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("加载失败")
                    }
                }
            }
        }

    }


}

@Composable
fun LottieLoadingIndicator(
    modifier: Modifier = Modifier,
    animationRes: Int = R.raw.loading_more
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animationRes)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // 无限循环
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        alignment = Alignment.Center,
        modifier = modifier
            .size(64.dp)
    )
}
