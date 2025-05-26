package com.mess.messcartoon.ui.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import androidx.hilt.navigation.compose.hiltViewModel
import com.blankj.utilcode.util.LogUtils
import com.mess.messcartoon.R
import com.mess.messcartoon.model.ComicReader
import com.mess.messcartoon.ui.LocalNavController
import com.mess.messcartoon.utils.Formatter
import com.mess.messcartoon.viewmodel.ComicShelfViewModel

@Composable
fun ComicShelfScreen(viewModel: ComicShelfViewModel = hiltViewModel()) {

    val navController = LocalNavController.current
    val dismissState = rememberSwipeToDismissBoxState()
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout) // 避开挖孔
            .statusBarsPadding(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            items(viewModel.comicShelfList) { item: ComicReader ->
                SwipeToDismissListItem(
                    modifier = Modifier
                        .animateItem()
                        .fillMaxWidth(),
                    onDelete = {
                        viewModel.deleteItem(item)
                    }
                ) {
                    ShelfItem(item)
                }
            }
        }
    }

}

@Composable
fun SwipeToDismissListItem(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = {},
    content: @Composable () -> Unit
) {

    // 1. State is hoisted here
    val dismissState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {

            // 2. Animate the swipe by changing the color
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                },
                label = "swipe"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color) // 3. Set the animated color here
            ) {

                // 4. Show the correct icon
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.StartToEnd -> {}

                    SwipeToDismissBoxValue.EndToStart -> {
                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 16.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                            )
                            Text(
                                modifier = Modifier
                                    .padding(end = 16.dp),
                                text = stringResource(R.string.delete_from_shelf)
                            )
                        }
                    }

                    SwipeToDismissBoxValue.Settled -> {
                        // Nothing to do
                    }
                }

            }
        }
    ) {
        content()
    }

    // 5. Trigger the callbacks
    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            LaunchedEffect(dismissState.currentValue) {
                // 6. Don't forget to reset the state value
                onDelete()
                dismissState.snapTo(SwipeToDismissBoxValue.Settled) // or dismissState.reset()
            }

        }

        SwipeToDismissBoxValue.StartToEnd -> {

        }

        SwipeToDismissBoxValue.Settled -> {
            // Nothing to do
        }
    }
}

@Composable
fun ShelfItem(item: ComicReader) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {}
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp)
    ) {
        NetworkImage(
            imageUrl = item.cover,
            contentDescription = item.name,
            modifier = Modifier
                .aspectRatio(3f / 4f)
                .height(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 中间文本区域
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (item.updateState == 0) stringResource(R.string.comic_serialization) else stringResource(
                    R.string.comic_ending
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = if (item.updateState == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.comic_update_to_chapter) + item.lastReadChapterTitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            val themeString = item.themes.joinToString(separator = " | ") { it.name }
            Text(
                text = themeString,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = Formatter.format(item.lastReadTime),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        // 右侧继续按钮
        OutlinedButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterVertically),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary), // 蓝色边框
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = if (item.lastReadChapter.isNotEmpty()) stringResource(R.string.shelf_continue) else stringResource(
                    R.string.shelf_start
                ),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            )
        }
    }
}