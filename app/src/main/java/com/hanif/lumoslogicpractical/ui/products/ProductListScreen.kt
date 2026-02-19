package com.hanif.lumoslogicpractical.ui.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.hanif.lumoslogicpractical.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    onProductClick: (Int) -> Unit
) {
    val products = viewModel.productsFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Product Catalog") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Logic: If we are refreshing (calling the API), ONLY show the loading indicator.
            // No matter if it's the first install or a subsequent launch.
            if (products.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                // If not loading, show the list. 
                // If the refresh resulted in an error, Paging will still have the old local data, 
                // so LazyColumn will show the cached data.
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        count = products.itemCount,
                        key = products.itemKey { it.id },
                        contentType = products.itemContentType { "product" }
                    ) { index ->
                        val product = products[index]
                        if (product != null) {
                            ProductItem(product = product, onClick = { onProductClick(product.id) })
                        }
                    }

                    // Handle loading state for append (pagination)
                    when (val state = products.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = state.error.message ?: "Error loading more",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Button(onClick = { products.retry() }) {
                                        Text("Retry")
                                    }
                                }
                            }
                        }
                        else -> {}
                    }
                }

                // Handle initial error or refresh failure
                val refreshState = products.loadState.refresh
                if (refreshState is LoadState.Error) {
                    // Show error snackbar or small banner if we have data, 
                    // or a full screen error if the list is empty.
                    if (products.itemCount == 0) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = refreshState.error.localizedMessage ?: "An unknown error occurred",
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { products.refresh() }) {
                                Text("Retry")
                            }
                        }
                    } else {
                        // Optionally show a non-intrusive error if we are showing cached data
                        Text(
                            text = "Failed to update. Showing cached data.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                
                // Handle empty state (No data in DB and API returned empty)
                if (products.loadState.refresh is LoadState.NotLoading && products.itemCount == 0) {
                    Text(
                        text = "No products found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(120.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
