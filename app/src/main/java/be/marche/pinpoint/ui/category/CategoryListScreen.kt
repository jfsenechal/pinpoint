package be.marche.pinpoint.ui.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.marche.pinpoint.category.CategoryViewModel
import be.marche.pinpoint.data.CategoryUiState
import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.ContentScale
import coil3.size.Size

private val RallyDefaultPadding = 12.dp

@Composable
fun CategoryListScreen(
    onClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val categoryViewModel: CategoryViewModel = koinViewModel()
    val categoryUiState = categoryViewModel.categoryUiState

    LaunchedEffect(true) {
        categoryViewModel.loadCategories()
    }

    val categories = categoryViewModel.categories

    when (categoryUiState) {
        is CategoryUiState.Pending -> {}
        is CategoryUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CategoryUiState.Error -> ErrorScreen(
            categoryUiState.message,
            modifier = modifier.fillMaxSize()
        )

        is CategoryUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                // .padding(top = paddingValues.calculateTopPadding()),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                itemsIndexed(
                    items = categories,
                    key = { _, category -> category.id }
                ) { index, category ->
                    CategoryCard(
                        onDelete = { },
                        onItemClick = onClick,
                        category = category
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    onItemClick: (Int) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(8.dp)
            .clickable { onItemClick(category.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .width(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.image)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = category.name,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {

            Text(
                text = category.name,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = category.image.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

        Icon(
            modifier = Modifier
                .clickable { onDelete() },
            imageVector = Icons.Default.Clear,
            contentDescription = "DELETE" + category.name,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun CategoryCard2(
    category: Category,
    onItemClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .height(200.dp)
            .clickable { onItemClick(category.id) },
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
                    model = category.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.7f)
                        .padding(5.dp)
                )
                Spacer(Modifier.padding(2.dp))

                Column(Modifier.padding(2.dp)) {
                    Text(
                        text = category.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Clip
                    )
                    Text(
                        text = category.description.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "id: ${category.id}",
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
