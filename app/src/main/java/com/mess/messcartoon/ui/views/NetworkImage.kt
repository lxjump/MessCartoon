package com.mess.messcartoon.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.request.ImageRequest
import com.mess.messcartoon.R

import com.airbnb.lottie.compose.*
import com.blankj.utilcode.util.LogUtils
import java.io.File

@Composable
fun NetworkImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    cornerRadius: Dp = 8.dp,
    errorImage: Painter = painterResource(R.drawable.image_load_failed),
    lottieRes: Int = R.raw.lottie_loading,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center
) {
    val context = LocalContext.current

    // ✅ 可复用的自定义缓存策略 ImageLoader
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .diskCache {
                DiskCache.Builder()
                    .directory(File(context.cacheDir, "image_cache"))
                    .maxSizeBytes(256L * 1024 * 1024) // 256MB
                    .build()
            }
            .crossfade(true)
            .build()
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader
    )

    var loadingState by remember { mutableStateOf<AsyncImagePainter.State>(painter.state) }

    // ✅ 实时跟踪 painter 状态变化
    LaunchedEffect(painter) {
        snapshotFlow { painter.state }
            .collect { state ->
                loadingState = state
            }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    Box(
        modifier = modifier.clip(RoundedCornerShape(cornerRadius))
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        when (loadingState) {
            is AsyncImagePainter.State.Loading -> {
                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                )
            }

            is AsyncImagePainter.State.Error -> {
                Image(
                    painter = errorImage,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            }

            else -> {}
        }
    }
}
