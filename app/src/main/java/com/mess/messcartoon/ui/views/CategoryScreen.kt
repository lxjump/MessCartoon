package com.mess.messcartoon.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material3.IconButton
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.R
import com.mess.messcartoon.model.CategoryComic
import com.mess.messcartoon.model.CategoryList
import com.mess.messcartoon.model.CategoryOrdering
import com.mess.messcartoon.model.CategoryTheme
import com.mess.messcartoon.model.CategoryTop
import com.mess.messcartoon.viewmodel.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce


@Composable
fun CategoryScreen(viewModel: CategoryViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        if (viewModel.categoryList.isEmpty()) {
            viewModel.loadData()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout) // 避开挖孔
            .statusBarsPadding(), // 避开状态栏（可能包含刘海）
    ) {
        when (uiState) {
            is CategoryViewModel.UiState.Error -> ErrorView(onRetry = {
                viewModel.reload()
            })

            is CategoryViewModel.UiState.Loading -> LoadingView()
            is CategoryViewModel.UiState.Success -> {
                ThemeRow(viewModel.categoryTheme, onThemeSelected = { index ->
                    viewModel.changeTheme(index)
                })
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                )
                TopRow(viewModel.categoryTop, onTopSelected = {index ->
                    viewModel.changeTop(index)
                })
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                )
                SortOptionBar(viewModel.categoryOrder, onOrderClick = {index ->
                    LogUtils.d("SortOptionBar click $index")
                    viewModel.changeOrder(index)
                })
                CategoryList(viewModel.categoryList)
            }
        }
    }

}

// 单行横向滚动布局
@Composable
fun ThemeRow(items: List<CategoryTheme>, onThemeSelected: (Int) -> Unit, viewModel: CategoryViewModel = hiltViewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val selectedIndex by viewModel.themeIndex.collectAsState()
    val maxItemsInRow = 6
    Column(modifier = Modifier.padding(start = 10.dp)) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp), maxItemsInEachRow = maxItemsInRow
        ) {
            val displayTags = if (expanded) items else items.take(maxItemsInRow - 1)
            displayTags.forEachIndexed { index, tag ->
                FilterChip(
                    selected = selectedIndex == index,
                    onClick = {
                        onThemeSelected(index)
                    },
                    label = { Text(tag.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.Transparent,
                        selectedContainerColor = Color.Transparent,
                        labelColor = MaterialTheme.colorScheme.onSurface,
                        selectedLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    border = null
                )
            }

            // 展开 / 收起按钮（始终显示）
            IconButton(
                onClick = {
                    expanded = !expanded
                }, modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "收起" else "展开"
                )
            }
        }
    }
}

@Composable
fun TopRow(items: List<CategoryTop>, onTopSelected: (Int) -> Unit, viewModel: CategoryViewModel = hiltViewModel()) {
    val selectedIndex by viewModel.topIndex.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(start = 10.dp)) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp), maxItemsInEachRow = 6
        ) {
            items.forEachIndexed { index, top ->
                FilterChip(selected = selectedIndex == index, onClick = {
                    LogUtils.d("theme tag ${top.name}, ${top.pathWord}")
                    onTopSelected(index)
                }, label = { Text(top.name) }, colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.Transparent,
                    selectedContainerColor = Color.Transparent, // 可自定义选中颜色
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedLabelColor = MaterialTheme.colorScheme.primary
                ), border = null
                )
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun SortOptionBar(
    data: List<CategoryOrdering>,
    onOrderClick: (Int) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val selectedIndex by viewModel.orderIndex.collectAsState()
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(data.size),
        ) {
            itemsIndexed(data) { index, tag ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(), // 填充格子宽度
                    contentAlignment = Alignment.Center  // 内容居中
                ) {
                    SortItem(label = tag.name,
                        isSelected = index == selectedIndex,
                        ascending = tag.order,
                        onClick = {
                            LogUtils.d("click $index")
                            onOrderClick(index)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SortItem(
    label: String, isSelected: Boolean, ascending: Boolean, onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        )
        Icon(
            imageVector = if (ascending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(FlowPreview::class)
@Composable
fun CategoryList(
    categoryList: List<CategoryComic>,
    viewModel: CategoryViewModel = hiltViewModel()
) {

    val lazyGridState = rememberLazyGridState()
    val isLoadMoreError by viewModel.isLoadMoreError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isEndReached by viewModel.isEndReached.collectAsState()
    // 滚动监听
    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo }
            .debounce(300) // 防抖
            .collect { visibleItems ->
                LogUtils.d("visibleItems ${visibleItems.isNotEmpty()}")
                if (visibleItems.isNotEmpty()) {
                    val totalItems = viewModel.categoryList.size
                    val lastVisibleIndex = visibleItems.last().index

                    // 接近底部时加载更多（距离底部3项）
                    LogUtils.d("接近底部时加载更多")
                    if (lastVisibleIndex >= totalItems - 3 &&
                        !viewModel.isLoading.value &&
                        !viewModel.isEndReached.value
                    ) {
                        viewModel.loadMore()
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
        items(categoryList) { item: CategoryComic ->
            CategoryCard(
                name = item.name,
                coverUrl = item.cover,
                item.pathWord,
                item.author,
                item.popular,
                item.datetimeUpdated ?: stringResource(R.string.comic_unknown_updatetime)
            )
        }
        // 底部加载状态
        item(span = { GridItemSpan(maxLineSpan) }) {
            LogUtils.d("viewModel.isLoading.value = ${viewModel.isLoading.value}")
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

                isLoadMoreError -> {
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