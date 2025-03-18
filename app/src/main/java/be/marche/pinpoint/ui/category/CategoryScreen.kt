package be.marche.pinpoint.ui.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.marche.pinpoint.data.MarsUiState
import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.sync.SyncViewModel
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import be.marche.pinpoint.ui.mars.ResultScreen
import be.marche.pinpoint.viewModel.MarsViewModel
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

private val RallyDefaultPadding = 12.dp

@Composable
fun CategoryListScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val marsViewModel: MarsViewModel = viewModel()
    val syncViewModel: SyncViewModel = koinViewModel()
    val marsUiState = marsViewModel.marsUiState
    val syncState = syncViewModel.syncUiState
    val categories = syncViewModel.categories

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {

        Text("Liste catégories internet")
        Spacer(Modifier.height(RallyDefaultPadding))

        when (marsUiState) {
            is MarsUiState.Pending -> {}
            is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is MarsUiState.Error -> ErrorScreen(
                marsUiState.message,
                modifier = modifier.fillMaxSize()
            )

            is MarsUiState.Success -> ResultScreen(
                marsUiState.message, modifier = modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.height(RallyDefaultPadding))

        Text("Liste catégories db")

        when (syncState) {
            is MarsUiState.Pending -> {}
            is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is MarsUiState.Error -> ErrorScreen(
                syncState.message,
                modifier = modifier.fillMaxSize()
            )
            is MarsUiState.Success -> {
                categories.forEach { category ->
                    NewsCard(category, {})
                }
            }
        }

        IconButton(onClick = {
            syncViewModel.sync()
        }) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .size(35.dp)
                    .clickable {
                        syncViewModel.sync()
                    }
            )
        }

    }
}


@Composable
fun NewsCard(
    news: Category,
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
                    model = news.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.7f)
                        .padding(5.dp)
                )
                Spacer(Modifier.padding(2.dp))

                Column(Modifier.padding(2.dp)) {
                    Text(
                        text = news.name,
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
fun CategoryShowScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {


        Text("Show category")

    }
}