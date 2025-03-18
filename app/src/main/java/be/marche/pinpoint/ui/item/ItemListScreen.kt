package be.marche.pinpoint.ui.item

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import be.marche.pinpoint.data.ItemUiState
import be.marche.pinpoint.entity.Item
import be.marche.pinpoint.item.ItemViewModel
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import org.koin.androidx.compose.koinViewModel
import java.io.File

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

    LaunchedEffect(Unit) {
        itemViewModel.loadItems()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "item list Screen" }
    ) {
        Text("Liste items ")
        Spacer(Modifier.height(RallyDefaultPadding))

        when (itemUiState) {
            is ItemUiState.Pending -> {}
            is ItemUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is ItemUiState.Error -> ErrorScreen(
                itemUiState.message,
                modifier = modifier.fillMaxSize()
            )

            is ItemUiState.Success -> {
                itemViewModel.items.forEach { item ->
                    ItemCard(item, {})
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    news: Item,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .height(200.dp)
            .clickable { onItemClick() },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(0.5.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(Modifier.padding(2.dp)) {
                AsyncImage(
                    model = news.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.7f)
                        .padding(5.dp)
                )
                Spacer(Modifier.padding(2.dp))

                Column(Modifier.padding(2.dp)) {
                    Text(
                        text = "${news.latitude}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Clip
                    )
                    Text(
                        text = news.description.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "id: ${news.id}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "autor jf",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Text(
                    text = "Super date",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
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

        val painterStateFlow = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.imageUrl.toUri())
                .size(Size.ORIGINAL)
                .build()
        )
        val imageState = painterStateFlow.state.collectAsState()

        Log.d("ZEZE", imageState.value.toString())
        Log.d("ZEZE", "Saved Image URL: ${article.imageUrl}")
        Log.d("ZEZE", "file Image URL: ${File(article.imageUrl)}")
        Log.d("ZEZE", "uri Image URL: ${Uri.parse(article.imageUrl)}")

        Image(
            painter = painterStateFlow,
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(MaterialTheme.colorScheme.primary.copy(0.3f))
        )

        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(article.imageUrl)
                .size(Size.ORIGINAL)
                .build(),
            //  model = article.imageUrl.toUri(),
            contentDescription = article.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(MaterialTheme.colorScheme.primary.copy(0.3f))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.longitude.toString(),
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
