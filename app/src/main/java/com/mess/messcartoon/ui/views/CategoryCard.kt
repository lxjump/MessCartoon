package com.mess.messcartoon.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mess.messcartoon.R
import com.mess.messcartoon.model.Author
import com.mess.messcartoon.model.Category
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.utils.Formatter
import com.mess.messcartoon.utils.NavigationHelper

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CategoryCard(
    name: String,
    coverUrl: String,
    pathWord: String,
    authors: List<Author>,
    popular: Int,
    updateTime: String
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val navController = LocalNavController.current



    Column(
        modifier = Modifier
            .widthIn(max = (screenWidth - (2 * 30).dp) / 3)
            .heightIn(max = 250.dp)
            .clickable { NavigationHelper.navigateToComicDetail(navController, name, pathWord) })
    {
        NetworkImage(
            imageUrl = coverUrl,
            contentDescription = name,
            modifier = Modifier
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        // name
        Box(
            modifier = Modifier
                .align(Alignment.Start) // ⬅️ 贴在图片左下角
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                    )
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
//                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
        // author
        Box(
            modifier = Modifier
                .align(Alignment.Start) // ⬅️ 贴在图片左下角
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                    )
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            val authorName = authors.joinToString("|") { it.name }
            Text(
                text = authorName,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                maxLines = 1,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
        // popular
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 20.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                    )
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = Formatter.formatToTenThousand(popular),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                )

            }
        }
        // update time
        Box(
            modifier = Modifier
                .align(Alignment.Start) // ⬅️ 贴在图片左下角
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                    )
                )
        ) {
            Text(
                text = updateTime,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                maxLines = 1,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun IconTextRow(
    icon: ImageVector,
    text: String,
    iconTint: Color = Color(0xFFB0A7F8),
    textColor: Color = Color(0xFFB0A7F8)
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .alignBy(FirstBaseline),
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor,
            modifier = Modifier.alignBy(FirstBaseline)
        )
    }
}
