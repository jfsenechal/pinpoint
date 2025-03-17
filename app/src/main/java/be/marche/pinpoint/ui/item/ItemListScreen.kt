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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.marche.pinpoint.data.ItemUiState
import be.marche.pinpoint.entity.Item
import be.marche.pinpoint.item.ItemViewModel
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.koin.androidx.compose.koinViewModel

private val RallyDefaultPadding = 12.dp

@SuppressLint("MissingPermission")

@Composable
fun ItemListScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {

    val itemViewModel: ItemViewModel = koinViewModel()
    val itemUiState = itemViewModel.itemUiState
    itemViewModel.loadItems()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {
        when (itemUiState) {
            is ItemUiState.Pending -> {}
            is ItemUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is ItemUiState.Error -> ErrorScreen(
                itemUiState.message,
                modifier = modifier.fillMaxSize()
            )

            is ItemUiState.Success -> ListContent(
                itemUiState.items, {}, {}
            )
        }
    }
}

@Composable
fun ListContent(
    itemList: List<Item>,
    onAction: () -> Unit,
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
            val shouldPaginate = remember {
                derivedStateOf {
                    val totalItems = listState.layoutInfo.totalItemsCount
                    val lastVisibleIndex = listState.layoutInfo
                        .visibleItemsInfo.lastOrNull()?.index ?: 0
                    lastVisibleIndex == totalItems - 1
                }
            }

            LaunchedEffect(key1 = listState) {
                snapshotFlow { shouldPaginate.value }
                    .distinctUntilChanged()
                    .filter { it }
                    .collect { onAction() }
            }

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
            text = article.description.toString(),
            fontSize = 22.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "article.id",
            fontSize = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = article.imageUrl,
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.7f))
    )
}
