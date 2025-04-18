package be.marche.pinpoint.ui.item

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.marche.pinpoint.data.ItemUiState
import be.marche.pinpoint.entity.Item
import be.marche.pinpoint.item.ItemViewModel
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import be.marche.pinpoint.ui.components.TitleWithDivider
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import org.koin.androidx.compose.koinViewModel

private val RallyDefaultPadding = 12.dp

@SuppressLint("MissingPermission")

@Composable
fun ItemListScreen(
    onClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val itemViewModel: ItemViewModel = koinViewModel()
    val itemUiState = itemViewModel.itemUiState

    LaunchedEffect(Unit) {
        itemViewModel.loadItems()
    }

    TitleWithDivider("Liste items " + itemViewModel.items.size)

    Spacer(Modifier.height(RallyDefaultPadding))

    when (itemUiState) {
        is ItemUiState.Pending -> {}
        is ItemUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ItemUiState.Error -> ErrorScreen(
            itemUiState.message,
            modifier = modifier.fillMaxSize()
        )

        is ItemUiState.Success -> {
            ListContent(itemUiState.items, {})
        }
    }
}

@Composable
fun ListContent(
    itemList: List<Item>,
    onArticleClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(RallyDefaultPadding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (itemList.isNotEmpty()) {
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 8.dp),
                state = listState
            ) {
                itemsIndexed(
                    items = itemList,
                    key = { _, article -> article.id }
                ) { index, article ->
                    ArticleItem(
                        article = article,
                        onArticleClick = onArticleClick
                    )
                }
            }
        }
        else {
            Text(text = "Liste vide")
        }
    }

}

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Item,
    onArticleClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onArticleClick(article.id) }
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = article.latitude.toString(),
            fontSize = 22.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = article.imageUrl,
            fontSize = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(article.imageUrl)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(MaterialTheme.colorScheme.primary.copy(0.3f))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.description.toString(),
            fontSize = 17.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.createdAt,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

}
