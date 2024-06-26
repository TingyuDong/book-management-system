package com.thoughtworks.mobile.ui.fragments.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thoughtworks.mobile.data.modal.Book
import com.thoughtworks.mobile.ui.theme.MobileTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickAddButton: () -> Unit,
    onClickBook: (Book) -> Unit
) {
    val uiState: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    MobileTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                content = {
                    items(uiState.books.chunked(3)) { rowBooks ->
                        BookItems(
                            books = rowBooks,
                            onClick = { book ->
                                onClickBook(book)
                            }
                        )
                    }
                },
            )
            FloatingButton(onClick = onClickAddButton)
        }
    }
}

@Composable
fun BookItems(books: List<Book>, onClick: (Book) -> Unit) {
    Row(Modifier.padding(8.dp)) {
        books.forEach { book ->
            BookItem(book) { book ->
                onClick(book)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookItem(book: Book, onClick: (Book) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(width = 120.dp, height = 150.dp),
        onClick = { onClick(book) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = book.name)
            Text(text = "By ${book.author}")
        }
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { onClick() },
        ) {
            Icon(Icons.Filled.Add, "Add book button")
        }
    }
}