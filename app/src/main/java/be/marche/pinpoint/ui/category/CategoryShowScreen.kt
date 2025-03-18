package be.marche.pinpoint.ui.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import be.marche.pinpoint.ui.components.IconButtonWithText

private val RallyDefaultPadding = 12.dp

@Composable
fun CategoryShowScreen(
    categoryId: Int?,
    onClick: (Int) -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {
        Text("Show category" + categoryId)
        Spacer(Modifier.height(RallyDefaultPadding))
        IconButtonWithText(
            text = "Ajouter un objet",
            icon = Icons.Rounded.Add,
            onClick = {
                categoryId?.let { onClick(it) }
            })
    }
}