package eu.heha.ncfilerenamer.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.heha.ncfilerenamer.model.FileController
import eu.heha.ncfilerenamer.model.FileController.Resource
import eu.heha.ncfilerenamer.ui.MainViewModel.FileState
import eu.heha.ncfilerenamer.ui.theme.AppTheme

@Composable
fun MainScreen(
    fileState: FileState?,
    onClickResource: (Resource) -> Unit,
    onClickReload: () -> Unit,
    onClickNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(fileState?.ref ?: "") },
                actions = {
                    if (fileState != null && fileState !is FileState.Loading) {
                        if (fileState is FileState.Files && !fileState.content.resource.isRoot) {
                            IconButton(onClick = onClickNavigateUp) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Navigate Up"
                                )
                            }
                        }
                        IconButton(onClick = onClickReload) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (fileState) {
                is FileState.Loading -> item {
                    LoadingItem()
                }

                is FileState.Error -> item {
                    ErrorItem(fileState.ref, fileState.message, onClickReload)
                }

                is FileState.Files -> {
                    items(fileState.content.content) { resource ->
                        ResourceItem(
                            resource = resource,
                            onClick = { onClickResource(resource) }
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}

@Composable
private fun ResourceItem(resource: Resource, onClick: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceTint,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                resource.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                if (resource.isDirectory) "Directory" else "File",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun ErrorItem(ref: String, message: String, onClickReload: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            "Error loading '$ref'",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Text(
            message,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Button(onClick = onClickReload) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Retry")
            Text("Retry")
        }
    }
}

@Composable
private fun LoadingItem() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    BasePreview(FileState.Loading("root"))
}

@Preview
@Composable
private fun ErrorPreview() {
    BasePreview(FileState.Error("root", "Problem loading files"))
}

@Preview
@Composable
private fun FilesPreview() {
    BasePreview(
        FileState.Files(
            "root",
            FileController.ResourceContent(
                Resource("root", "root", isDirectory = true, isRoot = false),
                listOf(
                    Resource("root/file1", "file1", false),
                    Resource("root/file2", "file2", false),
                    Resource("root/dir1", "dir1", true),
                    Resource("root/dir2", "dir2", true)
                )
            )
        )
    )
}

@Composable
private fun BasePreview(fileState: FileState) {
    AppTheme {
        MainScreen(
            fileState = fileState,
            onClickResource = {},
            onClickReload = {},
            onClickNavigateUp = {}
        )
    }
}