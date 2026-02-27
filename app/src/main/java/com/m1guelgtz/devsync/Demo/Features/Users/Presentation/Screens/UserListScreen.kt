package com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
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
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Components.Molecules.UserCard
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.ViewModels.UserListState
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.ViewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    onBackClick: () -> Unit,
    onUserClick: (String) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val usersState by viewModel.usersState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Team Members",
                showBackButton = true,
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = { viewModel.loadUsers() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = OnSurface
                        )
                    }
                }
            )
        },
        containerColor = Background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = usersState) {
                is UserListState.Loading -> {
                    LoadingIndicator(message = "Loading team members...")
                }
                is UserListState.Error -> {
                    ErrorMessage(
                        message = state.message,
                        onRetry = { viewModel.loadUsers() }
                    )
                }
                is UserListState.Success -> {
                    if (state.users.isEmpty()) {
                        EmptyState(
                            message = "No team members found",
                            icon = {
                                Icon(
                                    Icons.Default.Person,
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
                            // Header with count
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                ) {
                                    Text(
                                        text = "${state.users.size} Members",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W500,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }

                            items(state.users, key = { it.id }) { user ->
                                UserCard(
                                    user = user,
                                    onClick = { onUserClick(user.id) },
                                    modifier = Modifier.animateItem()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
