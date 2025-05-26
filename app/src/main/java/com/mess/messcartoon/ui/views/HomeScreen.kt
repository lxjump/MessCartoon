package com.mess.messcartoon.ui.views

import android.annotation.SuppressLint
import android.net.Uri
import androidx.benchmark.perfetto.rowOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mess.messcartoon.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.model.Banner
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.model.FinishComics
import com.mess.messcartoon.model.HomeListItem
import com.mess.messcartoon.model.HotComic
import com.mess.messcartoon.model.NewComic
import com.mess.messcartoon.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.mess.messcartoon.model.RankComic
import com.mess.messcartoon.model.RankComics
import com.mess.messcartoon.model.RecComics
import com.mess.messcartoon.model.TopicList
import com.mess.messcartoon.model.Topics
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.utils.NavigationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullToRefreshState()

    val navController = LocalNavController.current

    val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current)



    val onRefresh = {
        viewModel.refreshData()
    }
    PullToRefreshBox(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.displayCutout) // 避开挖孔
            .statusBarsPadding(), // 避开状态栏（可能包含刘海）
//            .padding(top = statusBarHeight.dp),
//            .padding(WindowInsets.statusBars.asPaddingValues()),
//            .padding(WindowInsets.statusBars.asPaddingValues()),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = refreshState,
        indicator = {
            // 不显示指示器
        }) {
        LogUtils.d("statusBarHeight -> $statusBarHeight")
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // ⬅️ 每项间隔
        ) {
            item {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            when (val state = uiState) {
                is HomeViewModel.UiState.Loading -> {
                    item {
                        LoadingView()
                    }
                }

                is HomeViewModel.UiState.Error -> {
                    item {
                        ErrorView(onRetry = { viewModel.loadData() })
                    }
                }

                is HomeViewModel.UiState.Success -> {
                    LogUtils.d("size = ${state.data.size}")
                    items(state.data) { item ->
                        when (item) {
                            is HomeListItem.BannerItem -> BannerView(
                                item.banners, modifier = Modifier.fillMaxSize()
                            )

                            is HomeListItem.RecommendComicItem -> {
                                val title = stringResource(R.string.comic_recommend)
                                RecommendView(
                                    item.comics,
                                    onClick = {
                                        NavigationHelper.navigateToDetailList(
                                            navController,
                                            title,
                                            DetailType.Recommend.typeName
                                        )
                                    })
                            }

                            is HomeListItem.TopicsComicItem -> {
//                                TopicsComicView(
//                                    item.comics,
//                                    onClick = {
//
//                                    })
                            }

                            is HomeListItem.HotComicItem -> {
                                LogUtils.d("HotItem")
                                val title = stringResource(R.string.comic_hot)
                                HotComicView(item.comics, onClick = {
                                    NavigationHelper.navigateToDetailList(
                                        navController,
                                        title,
                                        DetailType.Hot.typeName
                                    )
                                })
                            }

                            is HomeListItem.NewComicItem -> {
                                val title = stringResource(R.string.newest_comic)
                                NewComicView(
                                    item.comics, onClick = {
                                        NavigationHelper.navigateToDetailList(
                                            navController,
                                            title,
                                            DetailType.New.typeName
                                        )
                                    })
                            }

                            is HomeListItem.FinishedComicItem -> {
                                val title = stringResource(R.string.comic_finished)
                                FinishedComicView(
                                    item.comics,
                                    onClick = {
                                        NavigationHelper.navigateToDetailList(
                                            navController,
                                            title,
                                            DetailType.Finished.typeName
                                        )
                                    })
                            }

                            is HomeListItem.RankDayComicItem -> {
                                val title = stringResource(R.string.comic_rank)
                                RankDayComicView(
                                    item.comics,
                                    onClick = {
                                        NavigationHelper.navigateToRankList(
                                            navController,
                                            title,
                                            RankType.RankDay.typeName
                                        )
                                    })
                            }

                            is HomeListItem.RankMonthComicItem -> RankWeekComicView(item.comics)
                            is HomeListItem.RankWeekComicItem -> RankMonthComicView(item.comics)
                        }
                    }
                }
            }
        }
//        PullToRefreshDefaults.Indicator(state = state, isRefreshing = isRefreshing)
    }
}

@Composable
fun RankMonthComicView(comics: RankComics) {
    LogUtils.d("rankMonth")
}

@Composable
fun RankWeekComicView(comics: RankComics) {
    LogUtils.d("rankWeek")
}

@Composable
fun RankDayComicView(comics: RankComics, onClick: () -> Unit) {
    LogUtils.d("rankDay")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SectionTitle(
            iconResId = R.mipmap.icon_rank,
            title = stringResource(R.string.comic_rank),
            onClick = {
                onClick()
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 0.8.dp, color = Color.Gray
        )
        Spacer(modifier = Modifier.width(5.dp))
        LazyVerticalGrid(
            modifier = Modifier.heightIn(max = (170 * (comics.list.size / 3)).dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(comics.list) { item ->
                ComicCard(item.comic.name, item.comic.cover, item.comic.pathWord)
            }
        }
    }
}

@Composable
fun NewComicView(comics: List<NewComic>, onClick: () -> Unit) {
    LogUtils.d("newComic")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SectionTitle(
            iconResId = R.mipmap.icon_new, title = stringResource(R.string.comic_new),
            onClick = {
                onClick()
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 0.8.dp, color = Color.Gray
        )
        Spacer(modifier = Modifier.width(5.dp))
        LazyVerticalGrid(
            modifier = Modifier.heightIn(max = (170 * (comics.size / 3)).dp),
//            modifier = Modifier.wrapContentHeight(),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(comics) { item ->
                ComicCard(item.comic.name, item.comic.cover, item.comic.pathWord)
            }
        }
    }
}

@Composable
fun HotComicView(comics: List<HotComic>, onClick: () -> Unit) {
    LogUtils.d("热门更新")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SectionTitle(
            iconResId = R.mipmap.icon_hot, title = stringResource(R.string.comic_hot), onClick = {
                onClick()
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 0.8.dp, color = Color.Gray
        )
        Spacer(modifier = Modifier.width(5.dp))
        LazyVerticalGrid(
            modifier = Modifier.heightIn(max = (170 * (comics.size / 3)).dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(comics) { item ->
                ComicCard(item.comic.name, item.comic.cover, item.comic.pathWord)
            }
        }
    }
}

@Composable
fun FinishedComicView(comics: FinishComics, onClick: () -> Unit) {
    LogUtils.d("finished")
    LogUtils.d("完结")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SectionTitle(
            iconResId = R.mipmap.icon_finished,
            title = stringResource(R.string.comic_finished),
            onClick = {
                onClick()
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 0.8.dp, color = Color.Gray
        )
        Spacer(modifier = Modifier.width(5.dp))
        LazyVerticalGrid(
            modifier = Modifier.heightIn(max = (170 * (comics.list.size / 3)).dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(comics.list) { item ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    ComicCard(item.name, item.cover, item.pathWord)
                    Image(
                        painter = painterResource(R.mipmap.icon_finished_corner),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopEnd)
                            .padding(
                                end = 0.dp, top = 5.dp
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun TopicsComicView(comics: TopicList, onClick: () -> Unit) {
    val noScrollConnection = remember {
        object : NestedScrollConnection {}
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SectionTitle(
            iconResId = R.mipmap.icon_topics,
            title = stringResource(R.string.comic_topics),
            onClick = {
                onClick()
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 0.8.dp, color = Color.Gray
        )
        LazyHorizontalGrid(
            modifier = Modifier
                .height(170.dp)
                .nestedScroll(noScrollConnection),
            rows = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(comics.list) { item ->
                ComicCard(item.title, item.cover, item.pathWord)
            }
        }
    }
}


@Composable
private fun BannerView(banners: List<Banner>, modifier: Modifier) {
    val autoScrollDelayMillis: Long = 3000
    if (banners.isEmpty()) {
        return
    }
    val virtualInfiniteCount = Int.MAX_VALUE
    val actualBannerSize = banners.size

    val navController = LocalNavController.current

    // 初始位置设置在中间，这样可以向两个方向滑动
    val initialPage = virtualInfiniteCount / 2

    val pagerState =
        rememberPagerState(initialPage = initialPage, pageCount = { virtualInfiniteCount })

    val coroutineScope = rememberCoroutineScope()

    // 自动滚动效果
    LaunchedEffect(banners, autoScrollDelayMillis) {
        if (actualBannerSize <= 1) return@LaunchedEffect

        while (true) {
            delay(autoScrollDelayMillis)
            // ⛔ 如果用户在滑动，暂停自动滑动，等待下次机会
            if (!pagerState.isScrollInProgress) {
                try {
                    val nextPage = pagerState.currentPage + 1
                    pagerState.animateScrollToPage(nextPage)
                } catch (e: Exception) {
                    // 忽略异常，继续循环
                    LogUtils.e(e.toString())
                }
            }
        }

    }

    // 当组件销毁时取消自动滚动
    DisposableEffect(Unit) {
        onDispose { }
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            HorizontalPager(
                state = pagerState, modifier = Modifier.fillMaxSize()
            ) { virtualPage ->
                // 计算实际要显示的项目索引
                val actualIndex = virtualPage % actualBannerSize
                val item = banners[actualIndex]

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                        .clickable {
                            item.comic?.let { NavigationHelper.navigateToComicDetail(navController, it.name, it.pathWord) }
                        },
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        NetworkImage(
                            imageUrl = item.cover,
                            contentDescription = "Banner Image ${item.brief}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )
                        // 文本覆盖在底部
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart) // ⬅️ 贴在图片左下角
                                .fillMaxWidth()
                                .padding(
                                    start = 12.dp, end = 12.dp, bottom = 32.dp  // ⬅️ 向上偏移，避免与指示器重叠
                                )

                        ) {
                            Text(
                                text = item.brief,
                                color = Color.White,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

            }

            // 指示器
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            ) {
                DotsIndicator(
                    totalDots = actualBannerSize,
                    selectedIndex = pagerState.currentPage % actualBannerSize,
                    selectedColor = Color.White,
                    unSelectedColor = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int, selectedIndex: Int, selectedColor: Color, unSelectedColor: Color
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalDots) {
            Box(
                modifier = Modifier
                    .size(if (i == selectedIndex) 8.dp else 6.dp)
                    .clip(CircleShape)
                    .background(if (i == selectedIndex) selectedColor else unSelectedColor)
            )

            if (i != totalDots - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}


@Composable
private fun RecommendView(recComics: RecComics, onClick: () -> Unit) {
    LogUtils.d("RecommendView")
    val noScrollConnection = remember {
        object : NestedScrollConnection {}
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SectionTitle(
            iconResId = R.mipmap.icon_recommend, title = stringResource(R.string.comic_recommend),
            onClick = {
                onClick()
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 0.8.dp, color = Color.Gray
        )
        Spacer(modifier = Modifier.width(5.dp))
        LazyVerticalGrid(
            modifier = Modifier
                .height(170.dp)
                .nestedScroll(noScrollConnection),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recComics.list) { item ->
                LogUtils.d("${item.comic.name}, ${item.comic}")
                ComicCard(item.comic.name, item.comic.cover, item.comic.pathWord)
            }
        }
    }
}


@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
//        CircularProgressIndicator()
        LottieLoadingIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        )
    }
}

@Composable
fun ErrorView(onRetry: () -> Unit) {
    // 显示错误信息的 UI
    Column(modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.3f))
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .fillMaxSize()
        .clickable { onRetry() }) {
        Image(
            painter = painterResource(R.drawable.image_load_failed), // 请确保你有这个资源
            contentDescription = "加载失败", modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, shape = MaterialTheme.shapes.medium)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "搜索",
            tint = Color.Gray,
            modifier = Modifier.padding(start = 16.dp)
        )

        Text(
            text = "搜索",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Composable
fun SectionTitle(iconResId: Int, title: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }

    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Default.MoreVert,
            contentDescription = null,
            modifier = Modifier.padding(end = 2.dp)
        )
    }
}
