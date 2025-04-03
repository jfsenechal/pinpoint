package be.marche.pinpoint.ui.category

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.marche.pinpoint.category.CategoryViewModel
import be.marche.pinpoint.data.CategoryUiState
import be.marche.pinpoint.entity.Category
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import be.marche.pinpoint.ui.components.TitleWithDivider
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import org.koin.androidx.compose.koinViewModel

private val RallyDefaultPadding = 12.dp

@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    onClickShow: (Int) -> Unit = {},
    onClickAdd: (Int) -> Unit = {},
) {
    val categoryViewModel: CategoryViewModel = koinViewModel()
    val categoryUiState = categoryViewModel.categoryUiState

    LaunchedEffect(true) {
        categoryViewModel.loadCategories()
    }

    val categories = categoryViewModel.categories

    Column(modifier = modifier) {
        TitleWithDivider("Liste des catégories")

        Spacer(modifier = Modifier.width(8.dp))

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
                        .padding(top = RallyDefaultPadding),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    itemsIndexed(
                        items = categories,
                        key = { _, category -> category.id }
                    ) { index, category ->
                        CategoryCard(
                            onItemClickShow = onClickShow,
                            onItemClickAdd = onClickAdd,
                            category = category
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    onItemClickShow: (Int) -> Unit,
    onItemClickAdd: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .width(80.dp)
                .clip(RoundedCornerShape(8.dp))
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
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            category.description?.let {
                Text(
                    text = category.description,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.requiredSize(52.dp)) {//force
                    Icon(
                        modifier = Modifier
                            .size(52.dp, 52.dp)
                            .padding(vertical = 8.dp)
                            .clickable { onItemClickAdd(category.id) },
                        imageVector = Icons.Default.Add,
                        contentDescription = "ADD" + category.name,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Box(modifier = Modifier.requiredSize(52.dp)) {
                    Icon(
                        modifier = Modifier
                            .size(52.dp, 52.dp)
                            .padding(vertical = 8.dp)
                            .clickable { onItemClickShow(category.id) },
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "LIST" + category.name,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
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

@Preview
@Composable
fun SimpleComposablePreview() {
    CategoryListScreen()
}