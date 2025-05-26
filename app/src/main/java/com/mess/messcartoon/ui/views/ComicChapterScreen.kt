package com.mess.messcartoon.ui.views

import ComicChapter
import android.icu.text.CaseMap.Title
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.blankj.utilcode.util.LogUtils
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mess.messcartoon.R
import com.mess.messcartoon.model.Chapter
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.utils.UiEventUtils
import com.mess.messcartoon.viewmodel.ComicChapterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import java.io.File
import java.util.UUID
import kotlin.math.abs

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ComicChapterScreen(
    title: String,
    pathWord: String,
    uuid: String,
    viewModel: ComicChapterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentTitle = title
    // 添加UI显示状态
    var showUI by remember { mutableStateOf(false) }
    var scrollDirectionChanged by remember { mutableStateOf(false) }

    val navController = LocalNavController.current

    // 记录上一次滚动位置，用于检测上滑手势
    var lastScrollOffset by remember { mutableIntStateOf(0) }
    var scrollDirection by remember { mutableIntStateOf(0) } // 1表示上滑，-1表示下滑
    var lastScrollDirection by remember { mutableIntStateOf(0) } // 1表示上滑，-1表示下滑

    val listState = rememberLazyListState()
    val visibleItems by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.map { it.index }.toSet()
        }
    }
    // 沉浸式模式
    val systemUiController = rememberSystemUiController()

    // 添加章节导航状态
    var showPrevChapterDialog by remember { mutableStateOf(false) }
    var showNextChapterDialog by remember { mutableStateOf(false) }

    val chapterDetail by viewModel.chapterDetail.collectAsState()
    // 添加一个变量跟踪下拉手势
    var lastChapterId by remember { mutableStateOf("") }

    var isAutoScrolling by remember { mutableStateOf(false) }

    var pullDownDistance by remember { mutableFloatStateOf(0f) }
    var showPullDownIndicator by remember { mutableStateOf(false) }
    var pullDownProgress by remember { mutableFloatStateOf(0f) }
    // 设置触发阈值
    val pullThreshold = 150f

    val context = LocalContext.current

    LaunchedEffect(chapterDetail) {
        LogUtils.d("章节改变了")
        chapterDetail?.let {
            currentTitle = it.name ?: title
            // 检查章节是否变化
            if (lastChapterId != it.uuid) {
                lastChapterId = it.uuid
                // 章节变化时，重置滚动位置到顶部
                listState.scrollToItem(0)
                LogUtils.d("章节已变化，重置滚动位置")
                // 延迟重置自动滚动标志，给滚动操作留出时间
                kotlinx.coroutines.delay(500)
                isAutoScrolling = false
            }
        }
    }

    LaunchedEffect(Unit) {
        systemUiController.isSystemBarsVisible = false
        viewModel.loadData(pathWord, uuid)
    }

    val isFirstPage by remember {
        LogUtils.d("listState.firstVisibleItemIndex -> ${listState.firstVisibleItemIndex}, listState.firstVisibleItemScrollOffset -> ${listState.firstVisibleItemScrollOffset}")
        LogUtils.d("init isFirstPage " + (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset <= 10))
        derivedStateOf {
            listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset <= 10 // 允许小偏移
        }
    }

    val isLastPage by remember {
        derivedStateOf {
            val state = uiState
            if (state is ComicChapterViewModel.UiState.Success) {
                val imageUrls = state.data.chapter.contents.map { it.url }
                val lastVisibleItemIndex =
                    listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                lastVisibleItemIndex >= imageUrls.size - 1
            } else {
                false
            }
        }
    }

    LaunchedEffect(listState) {
        LogUtils.d("LaunchedEffect(listState) ")
    }


    val isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullToRefreshState()

    val onRefresh = {
        showPrevChapterDialog = true
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // 向下滑（顶部）
                if (available.y > 0 && listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0 && !showPrevChapterDialog) {
                    showPrevChapterDialog = true
                }

                // 向上滑（底部）
                val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                if (available.y < 0 && lastVisible != null &&
                    lastVisible.index == listState.layoutInfo.totalItemsCount - 1 &&
                    lastVisible.offset + lastVisible.size <= listState.layoutInfo.viewportEndOffset &&
                    !showNextChapterDialog
                ) {
                    showNextChapterDialog = true
                }

                return Offset.Zero
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .nestedScroll(nestedScrollConnection)
            .padding(WindowInsets.statusBars.asPaddingValues()),
    ) {
        AnimatedVisibility(
            visible = showPullDownIndicator,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            PullDownIndicator(
                progress = pullDownProgress,
                threshold = pullThreshold,
                modifier = Modifier.padding(top = 50.dp)
            )
        }
        when (val state = uiState) {
            is ComicChapterViewModel.UiState.Error -> ErrorView(onRetry = {
                viewModel.loadData(pathWord, uuid)
            })

            is ComicChapterViewModel.UiState.Loading -> LoadingView()
            is ComicChapterViewModel.UiState.Success -> {
                chapterDetail?.let {
                    ComicImageViewer(
                        chapter = it,
                        listState = listState,
                        onTap = {
                            //                        showUI = !showUI
                        }
                    )
                }
            }
        }

        if (showPrevChapterDialog) {
            LogUtils.d("显示上一话弹窗")
            val havePrev = chapterDetail?.prev != null
            if (!havePrev) {
                Toast.makeText(
                    LocalContext.current,
                    stringResource(R.string.tip_first_chapter),
                    Toast.LENGTH_SHORT
                ).show()
                showPrevChapterDialog = false  // 关闭弹窗而不是返回
            } else {
                ChapterNavigationDialog(
                    title = stringResource(R.string.prev_chapter),
                    message = stringResource(R.string.tip_jump_to_prev_chapter),
                    onConfirm = {
                        // 这里添加跳转到上一话的逻辑
                        // 可以调用ViewModel中的方法获取上一话信息
                        // 然后使用navController导航
                        showPrevChapterDialog = false
                        chapterDetail?.prev?.let { viewModel.loadData(pathWord, it) }
                    },
                    onDismiss = {
                        showPrevChapterDialog = false
                    }
                )
            }
        }

        // 显示下一话弹窗
        if (showNextChapterDialog) {
            val haveNext = chapterDetail?.next != null
            if (!haveNext) {
                Toast.makeText(
                    LocalContext.current,
                    stringResource(R.string.tip_newest_chapter),
                    Toast.LENGTH_SHORT
                ).show()
                showNextChapterDialog = false
            } else {

                ChapterNavigationDialog(
                    title = stringResource(R.string.next_chapter),
                    message = stringResource(R.string.tip_jump_to_next_chapter),
                    onConfirm = {
                        // 这里添加跳转到下一话的逻辑
                        // 可以调用ViewModel中的方法获取下一话信息
                        // 然后使用navController导航
                        showNextChapterDialog = false
                        // 设置自动滚动标志
                        isAutoScrolling = true
                        chapterDetail?.next?.let { viewModel.loadData(pathWord, it) }
                    },
                    onDismiss = {
                        showNextChapterDialog = false
                    }
                )
            }
        }

    }
}


// 添加下拉指示器组件
@Composable
fun PullDownIndicator(
    progress: Float,
    threshold: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 旋转的箭头图标
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .graphicsLayer {
                        rotationZ = 90f // 旋转箭头向上
                        // 根据进度添加动画效果
                        translationY = (1f - progress) * -8f
                    }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 进度指示器
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.width(100.dp),
                color = if (progress >= 1f) Color.Green else Color.White,
                trackColor = Color.Gray.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 提示文本
            Text(
                text = if (progress >= 1f)
                    stringResource(R.string.release_to_prev_chapter)
                else
                    stringResource(R.string.pull_down_for_prev_chapter),
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

// 添加章节导航弹窗组件
@Composable
fun ChapterNavigationDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable(enabled = false) { /* 防止点击穿透 */ }
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { onDismiss() }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_cancel),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { onConfirm() }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_confirm),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}
// ComicChapterScreen.kt

@Composable
fun ComicImageViewer(
    chapter: Chapter,
    listState: LazyListState = rememberLazyListState(),  // 接收外部传入的listState
    onTap: () -> Unit = {},
    viewModel: ComicChapterViewModel = hiltViewModel()
) {
//    val listState = rememberLazyListState()
    val isScaled by remember { mutableStateOf(false) }

    // 整体缩放状态
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val minScale = 1f
    val maxScale = 3f
    val imageUrls = chapter.contents.map { it.url }

    // 添加当前页面状态
    val currentPage = remember {
        derivedStateOf {
            // 获取所有可见项
            val visibleItems = listState.layoutInfo.visibleItemsInfo

            if (visibleItems.isEmpty()) {
                1 // 默认显示第1页
            } else if (visibleItems.any { it.index == imageUrls.size - 1 }) {
                imageUrls.size // 如果最后一项可见，显示为最后一页
            } else {
                // 找出可见区域最大的项
                val maxVisibleItem = visibleItems.maxByOrNull { it.size }
                (maxVisibleItem?.index ?: 0) + 1
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            LogUtils.d("chapter = $chapter")
            viewModel.updateComicReader(chapter.comicPathWord, chapter.uuid, currentPage.value) // 👈 页面销毁前调用
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    // 计算新的缩放比例
                    val newScale = (scale * zoom).coerceIn(minScale, maxScale)

                    // 计算新的偏移量
                    if (newScale > minScale) {
                        // 计算边界
                        val maxX = (size.width * (newScale - 1)) / 2
                        val maxY = (size.height * (newScale - 1)) / 2

                        offset = Offset(
                            x = (offset.x + pan.x).coerceIn(-maxX, maxX),
                            y = (offset.y + pan.y).coerceIn(-maxY, maxY)
                        )
                    } else {
                        offset = Offset.Zero
                    }

                    scale = newScale
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (scale > minScale) {
                            // 双击恢复原始大小
                            scale = minScale
                            offset = Offset.Zero
                        } else {
                            // 双击放大
                            scale = 2f
                        }
                    },
                    onTap = {
                        // 这里调用 onTap，不传递任何参数
                        onTap()
                    }
                )
            }
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
            ) {
                itemsIndexed(imageUrls) { index, url ->
                    ComicImage(
                        imageUrl = url,
                        chapter = chapter,
                        imageIndex = index,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.7f),
                    )
                }
            }

            // 添加页面指示器
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${chapter.name} ${currentPage.value}/${imageUrls.size}",
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun ComicImage(
    imageUrl: String,
    chapter: Chapter, // 添加章节参数
    imageIndex: Int,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .diskCache {
                DiskCache.Builder()
                    .directory(File(context.cacheDir, "chapter_image_cache"))
                    .maxSizeBytes(256L * 1024 * 1024) // 256MB
                    .build()
            }
            .crossfade(true)
            .build()
    }
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .diskCachePolicy(coil.request.CachePolicy.ENABLED)
                .memoryCachePolicy(coil.request.CachePolicy.ENABLED)
                .memoryCacheKey("comic_${chapter.uuid}_${imageIndex}") // 添加内存缓存键
                .diskCacheKey("comic_${chapter.uuid}_${imageIndex}")   // 添加磁盘缓存键
                .placeholder(android.R.drawable.ic_menu_gallery)
                .build(),
            imageLoader = imageLoader,
            contentDescription = "comic_${chapter.name}_${chapter.uuid}_${imageIndex}",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            onState = { state ->
                isLoading = state is AsyncImagePainter.State.Loading
            }
        )

        // 添加加载指示器
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ZoomableComicImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    onScaleChanged: (Boolean) -> Unit,
    onTap: () -> Unit = {}
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val maxScale = 5f
    val minScale = 1f
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(scale) {
        onScaleChanged(scale != minScale)
    }

    Box(
        modifier = modifier
//            .pointerInput(Unit) {
//                detectTransformGestures(
//                    panZoomLock = false  // 修改为 false，允许同时平移和缩放
//                ) { _, pan, zoom, _ ->
//                    val newScale = (scale * zoom).coerceIn(minScale, maxScale)
//
//                    // 计算新的偏移量，考虑到缩放因子
//                    val maxX = (size.width * (newScale - 1)) / 2
//                    val maxY = (size.height * (newScale - 1)) / 2
//
//                    // 允许在缩放状态下垂直滚动
//                    val newOffset = if (newScale > minScale) {
//                        Offset(
//                            x = (offset.x + pan.x).coerceIn(-maxX, maxX),
//                            y = (offset.y + pan.y).coerceIn(-maxY, maxY)
//                        )
//                    } else {
//                        Offset.Zero
//                    }
//
//                    scale = newScale
//                    offset = newOffset
//                }
//            }
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onDoubleTap = {
//                        if (scale > minScale) {
//                            scale = minScale
//                            offset = Offset.Zero
//                        } else {
//                            scale = 2.5f  // 使用中等缩放值
//                        }
//                    },
//                    onTap = { onTap() }  // 处理单击事件
//                )
//            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .diskCachePolicy(coil.request.CachePolicy.ENABLED)
                .memoryCachePolicy(coil.request.CachePolicy.ENABLED)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .build(),
            contentDescription = "漫画图片",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .fillMaxSize(),
            onState = { state ->
                isLoading = state is AsyncImagePainter.State.Loading
            }
        )

        // 添加加载指示器
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}