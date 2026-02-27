package com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Molecules.EmptyState
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Molecules.ErrorMessage
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Molecules.LoadingIndicator
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Organisms.AppTopBar
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.theme.*
import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Domain.Entities.Task
import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Domain.Entities.TaskStatus
import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Presentation.Components.Organisms.TaskCardExtended
import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Presentation.ViewModels.TaskListState
import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Presentation.ViewModels.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    projectId: String,
    onBackClick: () -> Unit,
    onTaskClick: (String) -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val tasksState by viewModel.tasksState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    var selectedFilter by remember { mutableStateOf<TaskStatus?>(null) }
    var showFilterMenu by remember { mutableStateOf(false) }

    LaunchedEffect(projectId) {
        viewModel.setProjectId(projectId)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Tasks",
                showBackButton = true,
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = OnSurface
                        )
                    }
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("All Tasks") },
                            onClick = {
                                selectedFilter = null
                                showFilterMenu = false
                            }
                        )
                        TaskStatus.entries.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.name) },
                                onClick = {
                                    selectedFilter = status
                                    showFilterMenu = false
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddTaskColumnChanged(TaskStatus.TODO) },
                containerColor = Primary,
                contentColor = OnPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Task")
            }
        },
        containerColor = Background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = tasksState) {
                is TaskListState.Loading -> {
                    LoadingIndicator(message = "Loading tasks...")
                }
                is TaskListState.Error -> {
                    ErrorMessage(
                        message = state.message,
                        onRetry = { viewModel.setProjectId(projectId) }
                    )
                }
                is TaskListState.Success -> {
                    val filteredTasks = if (selectedFilter != null) {
                        state.tasks.filter { it.status == selectedFilter }
                    } else {
                        state.tasks
                    }

                    if (filteredTasks.isEmpty()) {
                        EmptyState(
                            message = if (selectedFilter != null) {
                                "No tasks with ${selectedFilter?.name} status"
                            } else {
                                "No tasks yet. Create your first task!"
                            },
                            icon = {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Filter chip
                            if (selectedFilter != null) {
                                item {
                                    FilterChip(
                                        selected = true,
                                        onClick = { selectedFilter = null },
                                        label = { Text(selectedFilter?.name ?: "") },
                                        trailingIcon = {
                                            Icon(
                                                Icons.Default.Add,
                                                contentDescription = "Clear filter",
                                                modifier = Modifier.size(18.dp)
                                            )
                                        },
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                }
                            }

                            items(filteredTasks, key = { it.id }) { task ->
                                TaskCardExtended(
                                    task = task,
                                    onClick = { onTaskClick(task.id) },
                                    modifier = Modifier.animateItem()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Create Task Dialog
    if (uiState.addTaskColumn != null) {
        AlertDialog(
            onDismissRequest = { viewModel.onAddTaskColumnChanged(null) },
            containerColor = SurfaceContainer,
            title = { Text("Create New Task", color = OnSurface) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = uiState.newTaskTitle,
                        onValueChange = { viewModel.onNewTitleChanged(it) },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = uiState.newTaskDesc,
                        onValueChange = { viewModel.onNewDescChanged(it) },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (uiState.newTaskTitle.isNotBlank()) {
                            viewModel.createTask(projectId)
                        }
                    },
                    enabled = uiState.newTaskTitle.isNotBlank()
                ) {
                    Text("Create", color = Primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onAddTaskColumnChanged(null) }) {
                    Text("Cancel", color = OnSurfaceVariant)
                }
            }
        )
    }
}
