package com.mess.messcartoon.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import coil.request.ImageRequest
import com.blankj.utilcode.util.LogUtils
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter

//@Composable
//fun LazyLoadImageList(
//    imageUrls: List<String>,
//    modifier: Modifier = Modifier,
//    estimatedHeightDp: Dp = 400.dp,
//    contentScale: ContentScale = ContentScale.FillWidth
//) {
//    val listState = rememberLazyListState()
//    val visibleItems by remember {
//        derivedStateOf {
//            listState.layoutInfo.visibleItemsInfo.map { it.index }.toSet()
//        }
//    }
//
//    // 初始缩放值为 1f（即原始大小）
//    var scale by remember { mutableStateOf(1f) }
//    var offsetX by remember { mutableStateOf(0f) }
//    var offsetY by remember { mutableStateOf(0f) }
//
//    val transformableState = rememberTransformableState { zoomChange, offsetChange, _ ->
////        scale = (scale * zoomChange).coerceIn(1f, 5f) // 限制缩放范围
////        offsetX += offsetChange.x
////        offsetY += offsetChange.y
//        scale = (zoomChange * scale).coerceAtLeast(1f)
//        scale = if (scale > 5f) {
//            5f
//        } else {
//            scale
//        }
//    }
//
//    LazyColumn(
////        state = listState,
////        modifier = modifier
////    ) {
////        itemsIndexed(imageUrls) { index, imageUrl ->
////            LogUtils.d(imageUrl)
////            if (index in visibleItems) {
//                Box(
//                    modifier = modifier
//                    .fillMaxSize()
//                    .background(Color.Black)
//                    .transformable(state = transformableState)
//                    .graphicsLayer {
//                        scaleX = scale
//                        scaleY = scale
//                        translationX = offsetX
//                        translationY = offsetY
//                    }
//                ) {
//                    SubcomposeAsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(imageUrl)
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = "Image $index",
//                        contentScale = contentScale,
//                        loading = {
//                            Box(
//                                Modifier
//                                    .fillMaxWidth()
//                                    .height(estimatedHeightDp),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                CircularProgressIndicator()
//                            }
//                        },
//                        error = {
//                            Box(
//                                Modifier
//                                    .fillMaxWidth()
//                                    .height(estimatedHeightDp)
//                                    .background(Color.Red),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text("加载失败", color = Color.White)
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp)
//                    )
//                }
//
//            } else {
//                // 占位
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(estimatedHeightDp)
//                        .background(Color.LightGray)
//                )
//            }
//        }
//    }
//}

@Composable
fun LazyLoadImageList(
    imageUrls: List<String>,
    modifier: Modifier = Modifier
) {
    val listState = remember { LazyListState() }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState
    ) {
        itemsIndexed(imageUrls) { index, imageUrl ->
            ZoomableImage(
                imageUrl = imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp)
                    .padding(vertical = 4.dp)
            )
        }
    }
}
@Composable
fun ZoomableImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    minScale: Float = 1f,
    maxScale: Float = 5f
) {
    val painter = rememberAsyncImagePainter(imageUrl)

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // 控制滚动开关（在放大状态时禁用外层滚动）
    var scrollingEnabled by remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    val newScale = (scale * zoom).coerceIn(minScale, maxScale)

                    // 只在放大时应用拖动偏移
                    if (newScale > 1f) {
                        val maxOffsetX = 1000f // 可自定义为图片尺寸的一半等
                        val maxOffsetY = 1000f
                        val newOffset = offset + pan
                        offset = Offset(
                            newOffset.x.coerceIn(-maxOffsetX, maxOffsetX),
                            newOffset.y.coerceIn(-maxOffsetY, maxOffsetY)
                        )
                        scrollingEnabled = false
                    } else {
                        offset = Offset.Zero
                        scrollingEnabled = true
                    }
                    scale = newScale
                }
            }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .fillMaxWidth()
        )

    }
}
