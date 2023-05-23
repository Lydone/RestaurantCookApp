package com.lydone.restaurantcookapp.ui.stoplist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lydone.restaurantcookapp.data.Dish
import com.lydone.restaurantcookapp.ui.ErrorPlaceholder

@Composable
fun StopListRoute(viewModel: StopListViewModel = hiltViewModel()) {
    Screen(
        state = viewModel.state.collectAsState().value,
        onCheckedChange = viewModel::changeDishInStopList,
        viewModel::loadData,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Screen(
    state: StopListViewModel.State?,
    onCheckedChange: (Dish) -> Unit,
    onRetryClick: () -> Unit,
) {
    if (state != null) {
        if (state.dishes.isSuccess) {
            LazyVerticalGrid(
                GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.dishes.getOrThrow(), Dish::id) { dish ->
                    OutlinedCard(
                        onClick = { onCheckedChange(dish) },
                        Modifier.animateItemPlacement()
                    ) {
                        Row(
                            Modifier
                                .heightIn(min = 150.dp)
                                .padding(16.dp),
                            Arrangement.spacedBy(16.dp),
                            Alignment.CenterVertically,
                        ) {
                            Text(
                                dish.name,
                                Modifier.weight(1f),
                                style = MaterialTheme.typography.displaySmall
                            )
                            Checkbox(
                                checked = dish.inStopList,
                                onCheckedChange = { onCheckedChange(dish) }
                            )
                        }
                    }
                }
            }
        } else {
            ErrorPlaceholder(onRetryClick)
        }
    } else {
        Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
    }

}