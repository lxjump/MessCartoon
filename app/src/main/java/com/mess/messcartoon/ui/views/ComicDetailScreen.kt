package com.mess.messcartoon.ui.views

import ComicChapter
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.R
import com.mess.messcartoon.model.Comic
import com.mess.messcartoon.model.ComicDetail
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.utils.Formatter
import com.mess.messcartoon.utils.NavigationHelper
import com.mess.messcartoon.viewmodel.ComicDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicDetailScreen(
    name: String, pathWord: String, viewModel: ComicDetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val chapterListLoaded by viewModel.chapterListLoaded.collectAsState()

    val tabsToShow by viewModel.tabsToShow.collectAsState()

    val navController = LocalNavController.current

    val loadedSuccess by viewModel.loadedSuccess.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        LogUtils.d(navController.backQueue.map { it.destination.route }.toString())
    }

    LaunchedEffect(pathWord) {
        val routes = navController.backQueue.mapNotNull { it.destination.route }
        LogUtils.d("Back stack: $routes")

        if (!loadedSuccess) {
            LogUtils.d("没有加载成功,或者尚未加载")
            viewModel.loadData(pathWord)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.updateComicReader() // 👈 页面销毁前调用
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.displayCutout) // 避开挖孔
            .statusBarsPadding(), // 避开状态栏（可能包含刘海）
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            when (val state = uiState) {
                is ComicDetailViewModel.UiState.Loading -> {
                    LoadingView()
                }

                is ComicDetailViewModel.UiState.Error -> {
                    ErrorView(onRetry = { viewModel.loadData(pathWord) })
                }

                is ComicDetailViewModel.UiState.Success -> {
                    ComicPanel()
                }
            }
        }
        Row {
            when (chapterListLoaded) {
                FetchStatus.FetchSuccess -> {
                    TabRow(selectedTabIndex = selectedTabIndex) {
                        tabsToShow.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                    LogUtils.d(selectedTabIndex)
                                },
                                text = {
                                    Text(
                                        tab
                                    )
                                }
                            )
                        }
                    }
                }

                FetchStatus.Loading -> {
                    LottieLoadingIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                FetchStatus.FetchFailed -> {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onErrorContainer,
                            contentColor = MaterialTheme.colorScheme.onError,
                        ),
                        onClick = {
                            LogUtils.d("重试")
                            viewModel.retryFetChapters()
                        },
                    ) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }
        }
        Row {
            when (selectedTabIndex) {
                0 -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(viewModel.comicChapterDefaultList) { chapter ->
                            ChapterListItem(chapter, {

                            })
                        }
                    }
                }

                1 -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(viewModel.comicChapterTankobonList) { chapter ->
                            ChapterListItem(chapter, {

                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChapterListItem(chapter: ComicChapter, onClick: () -> Unit, viewModel: ComicDetailViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    val comicReader by viewModel.comicReader.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                LogUtils.d("跳转到章节")
                NavigationHelper.navigateToChapter(
                    navController,
                    chapter.name,
                    chapter.comicPathWord,
                    chapter.uuid
                )
            })
            .background(MaterialTheme.colorScheme.surface)
            .border(
                0.5.dp,
                MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)

    ) {
        // ⬆️ 章节标题行（主信息，字体大 + 粗）
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                LogUtils.d("chapter.index = ${chapter.index}")
                Text(
                    text = (chapter.index + 1).toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (viewModel.isReading(chapter)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = chapter.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (viewModel.isReading(chapter)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
            if (viewModel.isReading(chapter)) {
                Icon(
                    painter = painterResource(R.mipmap.icon_reading),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // ⬇️ 日期（副信息，字体小 + 浅色）
        Text(
            text = chapter.datetimeCreated,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Composable
fun ComicPanel(viewModel: ComicDetailViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val comicDetail by viewModel.comicDetail.collectAsState()

    val comicReader by viewModel.comicReader.collectAsState()
    comicDetail?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
        ) {
            // 背景图毛玻璃模糊
//            val bac = "https://hi77-overseas.mangafuna.xyz/buxihuannnverfanerxihuanmamawoma/593c1/16403460415833/c800x.webp"
//            val bac = "https://ww2.sinaimg.cn/mw690/005EUiO2ly1hxj8yk8u5oj30m81c37d9.jpg"
            Image(painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context).data(it.cover).crossfade(true).build()
//                model = ImageRequest.Builder(context).data(bac).crossfade(true).build()
            ),
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .blur(20.dp)
                    .graphicsLayer { alpha = 0.8f })

            // 毛玻璃遮罩层提升前景内容对比度
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, Color.Black.copy(alpha = 0.4f)
                            )
                        )
                    )
            )

            // 前景内容区域
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    NetworkImage(
                        imageUrl = it.cover,
                        contentDescription = it.name,
                        modifier = Modifier
                            .height(160.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .padding(start = 5.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis, // 超过两行显示省略号
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        SectionItem(
                            R.mipmap.icon_location,
                            "${stringResource(R.string.region)} ${it.region.display}"
                        )
                        SectionItem(
                            R.mipmap.icon_popular,
                            "${stringResource(R.string.popular)} ${
                                Formatter.formatToTenThousand(
                                    it.popular
                                )
                            }"
                        )
                        SectionItem(
                            R.mipmap.icon_comic_status,
                            "${stringResource(R.string.comic_status)} ${it.status.display}"
                        )
                        SectionItem(
                            R.mipmap.icon_comic_time,
                            "${stringResource(R.string.recently_update)} ${it.datetimeUpdated}"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = it.brief,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))
                val test = arrayOf(
                    "搞额笑",
                    "搞笑",
                    "搞打撒都是笑",
                    "搞嗯问我笑",
                    "搞笑",
                    "搞笑",
                    "搞热热笑",
                    "搞而且委屈笑",
                    "搞笑"
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 5
                ) {
                    it.author.forEach { tag ->
                        SuggestionChip(onClick = { LogUtils.d("author tag", tag.name) },
                            label = {
                                Text(
                                    tag.name,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            })
                    }
                    it.theme.forEach { tag ->
                        SuggestionChip(onClick = { LogUtils.d("theme tag", tag.name) },
                            label = { Text(tag.name) })
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ), onClick = {
                        LogUtils.d("收藏")
                        if (comicReader?.isInShelf == true) {
                            viewModel.removeFromShelf()
                        } else {
                            viewModel.addToShelf()
                        }
                    }
                    ) {
                        Text(text = stringResource(
                            if (comicReader?.isInShelf == true)
                                R.string.remove_shelf
                            else
                                R.string.add_shelf
                        ))
                    }
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ), onClick = { LogUtils.d("阅读") }
                    ) {
                        val textString = stringResource(
                            if (comicReader?.lastReadTime == 0L)
                                R.string.start_read
                            else
                                R.string.continue_read
                        )
                        Text(textString)
                    }
                }


            }
        }
    }
}


@Composable
fun SectionItem(iconId: Int, message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
//            .padding(start = 5.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            modifier = Modifier
                .height(20.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp)
                .align(Alignment.CenterVertically),
            text = message,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp
        )
    }
}

