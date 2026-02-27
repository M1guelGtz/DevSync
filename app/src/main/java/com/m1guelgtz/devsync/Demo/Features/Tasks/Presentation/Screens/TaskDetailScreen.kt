package com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.AppSpacer
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.AppText
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Molecules.LoadingIndicator
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Organisms.AppTopBar
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.theme.*
import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Domain.Entities.TaskStatus
import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Presentation.Components.Atoms.Avatar
import com.m1guelgtz.templatecarsapi.Demo.Features.Tasks.Presentation.ViewModels.TaskViewModel
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.ViewModels.UserListState
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.ViewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String,
    projectId: String,
    onBackClick: () -> Unit,
    onDeleteSuccess: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val usersState by userViewModel.usersState.collectAsStateWithLifecycle()
    
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Find task in the state
    val task = uiState.selectedTask

    LaunchedEffect(taskId, projectId) {
        viewModel.setProjectId(projectId)
        // Wait a bit for tasks to load, then find the task
        kotlinx.coroutines.delay(500)
        // This assumes we have a way to get a specific task
        // For now we'll use the selected task from state
    }

    val allUsers = (usersState as? UserListState.Success)?.users ?: emptyList()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Task Details",
                showBackButton = true,
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = OnSurface)
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorColor)
                    }
                }
            )
        },
        containerColor = Background
    ) { padding ->
        if (task == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator(message = "Loading task details...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Title Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "TITLE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W600,
                            color = OnSurfaceVariant,
                            letterSpacing = 0.05.sp
                        )
                        Text(
                            text = task.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            color = OnSurface
                        )
                    }
                }

                // Status Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "STATUS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.W600,
                                color = OnSurfaceVariant,
                                letterSpacing = 0.05.sp
                            )
                            AppSpacer.Small()
                            StatusBadge(status = task.status)
                        }
                    }
                }

                // Description Section
                if (!task.description.isNullOrBlank()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "DESCRIPTION",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.W600,
                                color = OnSurfaceVariant,
                                letterSpacing = 0.05.sp
                            )
                            Text(
                                text = task.description,
                                fontSize = 15.sp,
                                color = OnSurface,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }

                // Assignment Section
                task.assignedTo?.let { assignedUserId ->
                    val assignedUser = allUsers.find { it.id == assignedUserId }
                    assignedUser?.let { user ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "ASSIGNED TO",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W600,
                                    color = OnSurfaceVariant,
                                    letterSpacing = 0.05.sp
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Avatar(
                                        initials = user.username.take(2).uppercase(),
                                        color = "#D0BCFF",
                                        size = 40.dp
                                    )
                                    Column {
                                        Text(
                                            text = user.username,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            color = OnSurface
                                        )
                                        Text(
                                            text = user.email,
                                            fontSize = 13.sp,
                                            color = OnSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Timestamps
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Created",
                                fontSize = 13.sp,
                                color = OnSurfaceVariant
                            )
                            Text(
                                text = task.createdAt,
                                fontSize = 13.sp,
                                color = OnSurface
                            )
                        }
                        HorizontalDivider(color = OutlineVariant)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Last Updated",
                                fontSize = 13.sp,
                                color = OnSurfaceVariant
                            )
                            Text(
                                text = task.updatedAt,
                                fontSize = 13.sp,
                                color = OnSurface
                            )
                        }
                    }
                }
            }
        }
    }

    // Edit Dialog
    if (showEditDialog && task != null) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = SurfaceContainer,
            title = { Text("Edit Task", color = OnSurface) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.editTitle,
                        onValueChange = { viewModel.onEditTitleChanged(it) },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = uiState.editDescription,
                        onValueChange = { viewModel.onEditDescriptionChanged(it) },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    // Status Selector
                    Column {
                        Text(
                            "STATUS",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariant
                        )
                        AppSpacer.Small()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TaskStatus.entries.forEach { status ->
                                FilterChip(
                                    selected = uiState.currentStatus == status,
                                    onClick = { viewModel.onCurrentStatusChanged(status) },
                                    label = { Text(status.name, fontSize = 11.sp) }
                                )
                            }
                        }
                    }

                    // Assignee Selector
                    Column {
                        Text(
                            "ASSIGN TO",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariant
                        )
                        AppSpacer.Small()
                        LazyColumn(
                            modifier = Modifier.height(150.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(allUsers) { user ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { viewModel.onCurrentAssignedToChanged(user.id) }
                                        .background(
                                            if (uiState.currentAssignedTo == user.id) PrimaryContainer
                                            else SurfaceContainer,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Avatar(
                                        initials = user.username.take(2).uppercase(),
                                        color = "#D0BCFF",
                                        size = 32.dp
                                    )
                                    Text(user.username, color = OnSurface, modifier = Modifier.weight(1f))
                                    if (uiState.currentAssignedTo == user.id) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateTaskFromDialog(projectId)
                    showEditDialog = false
                }) {
                    Text("Save Changes", color = Primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel", color = OnSurfaceVariant)
                }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = SurfaceContainer,
            icon = {
                Icon(Icons.Default.Delete, contentDescription = null, tint = ErrorColor)
            },
            title = { Text("Delete Task?", color = OnSurface) },
            text = {
                Text(
                    "This action cannot be undone. The task will be permanently deleted.",
                    color = OnSurfaceVariant
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteTask(projectId)
                        showDeleteDialog = false
                        onDeleteSuccess()
                    }
                ) {
                    Text("Delete", color = ErrorColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = OnSurfaceVariant)
                }
            }
        )
    }
}

@Composable
private fun StatusBadge(status: TaskStatus) {
    val (color, bgColor) = when (status) {
        TaskStatus.TODO -> TodoColor to TodoBg
        TaskStatus.IN_PROGRESS -> InProgressColor to InProgressBg
        TaskStatus.REVIEW -> ReviewColor to ReviewBg
        TaskStatus.DONE -> DoneColor to DoneBg
    }
    
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status.name,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
