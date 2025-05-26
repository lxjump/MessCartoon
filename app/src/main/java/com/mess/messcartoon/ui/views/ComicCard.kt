package com.mess.messcartoon.ui.views

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material3.MaterialTheme
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.utils.NavigationHelper
import com.mess.messcartoon.viewmodel.ComicCardViewModel
import com.mess.messcartoon.viewmodel.ComicDetailViewModel


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ComicCard(
    name: String,
    coverUrl: String,
    pathWord: String,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val navController = LocalNavController.current



    Box(
        modifier = Modifier
            .widthIn(max = (screenWidth - (2 * 30).dp) / 3)
            .heightIn(max = 165.dp)
            .clickable { NavigationHelper.navigateToComicDetail(navController, name, pathWord) }) {
        NetworkImage(
            imageUrl = coverUrl,
            contentDescription = name,
            modifier = Modifier
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter) // ⬅️ 贴在图片左下角
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                    )
                )
                .padding(
                    start = 12.dp, end = 12.dp, bottom = 5.dp  //
                )
        ) {
            Text(
                text = name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}