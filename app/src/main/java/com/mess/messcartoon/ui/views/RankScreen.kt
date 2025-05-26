package com.mess.messcartoon.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.viewmodel.RankViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce

@Composable
fun RankScreen(
    title: String,
    type: RankType,
    viewModel: RankViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    val rankTabs = listOf("日榜", "周榜", "月榜", "总榜")
    var selectedTabIndex by remember { mutableIntStateOf(when (type) {
        RankType.RankDay -> 0
        RankType.RankWeek -> 1
        RankType.RankMonth -> 2
        RankType.RankTotal -> 3
    }) }

    // 初始加载
    LaunchedEffect(Unit) {
        if (viewModel.currentItems.isEmpty()) {
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
        if (type.isRank()) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                rankTabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            val newType = when (index) {
                                0 -> RankType.RankDay
                                1 -> RankType.RankWeek
                                2 -> RankType.RankMonth
                                else -> RankType.RankTotal
                            }
                            viewModel.selectRankType(newType)
//                            viewModel.loadData(newType, loadMore = false)
                        },
                        text = { Text(tab) }
                    )
                }
            }
        }

        // 内容区域

        when (val state = uiState) {
            is RankViewModel.UiState.Loading -> {
                LoadingView()
            }
            is RankViewModel.UiState.Error -> {
                ErrorView(onRetry = { viewModel.loadData(type, false) })
            }
            is RankViewModel.UiState.Success -> {
                RankComicView(state.data, viewModel, type, onClick = {

                })
            }
        }
    }

}


@OptIn(FlowPreview::class)
@Composable
fun RankComicView(
    comics: List<Comic>, viewModel: RankViewModel, type: RankType, onClick: () -> Unit
) {


    val lazyGridState = rememberLazyGridState()
    // 滚动监听
    val selectedType by viewModel.selectedRankType.collectAsState()

    val _selectedRankType = MutableStateFlow(RankType.RankDay)
    val selectedRankType: StateFlow<RankType> = _selectedRankType.asStateFlow()

    val currentItems by remember(selectedType) {
        derivedStateOf { viewModel.currentItems }
    }

    // 滚动监听（根据当前榜单类型触发）
    LaunchedEffect(lazyGridState, selectedType) { // 关键：监听类型变化
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo }
            .debounce(300)
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    // 获取当前榜单的状态
                    val currentState = viewModel.getCurrentState(selectedType)

                    // 触发条件判断
                    if (shouldLoadMore(
                            visibleItems = visibleItems,
                            totalItems = currentState.items.size,
                            isLoading = currentState.isLoading,
                            isEndReached = currentState.isEndReached
                        )) {
                        viewModel.loadData(selectedType, true) // 明确指定当前类型
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
        items(currentItems) { item ->
            LogUtils.d("item 是否为空")
            ComicCard(name = item.name, coverUrl = item.cover, item.pathWord)
        }

        // 底部加载状态
        item(span = { GridItemSpan(maxLineSpan) }) {
            when (type) {
                RankType.RankDay -> LoadMoreIndicator(type, viewModel)
                RankType.RankWeek -> LoadMoreIndicator(type, viewModel)
                RankType.RankMonth -> LoadMoreIndicator(type, viewModel)
                RankType.RankTotal -> LoadMoreIndicator(type, viewModel)
            }
        }
    }
}

private fun shouldLoadMore(
    visibleItems: List<LazyGridItemInfo>,
    totalItems: Int,
    isLoading: Boolean,
    isEndReached: Boolean
): Boolean {
    if (isLoading || isEndReached) return false

    val lastVisibleIndex = visibleItems.last().index
    val threshold = if (totalItems > 10) 3 else 1 // 动态阈值

    return lastVisibleIndex >= totalItems - threshold
}

@Composable
fun LoadMoreIndicator(type: RankType, viewModel: RankViewModel) {

    val selectedType by viewModel.selectedRankType.collectAsState()

    // 派生当前状态（自动重组）

    val isLoading by remember(selectedType) {
        derivedStateOf { viewModel.currentLoading }
    }
    val isEndReached by remember(selectedType) {
        derivedStateOf { viewModel.currentEndReached }
    }
    val isError by remember(selectedType) {
        derivedStateOf { viewModel.currentError }
    }
    LogUtils.d("currentLoad : $isLoading")
    LogUtils.d("currentEndReached : $isEndReached")
    LogUtils.d("currentEndReached : $isError")
    when {
        isLoading -> {
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

        isEndReached -> {
            Text(
                text = "已经到底了",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        isError != null -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("加载失败")
            }
        }
    }
}

