package com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.AppSpacer
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Molecules.LoadingIndicator
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Organisms.AppTopBar
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.theme.*
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Domain.Entities.User
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Components.Atoms.UserAvatar
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.ViewModels.UserListState
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.ViewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: String,
    onBackClick: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val usersState by viewModel.usersState.collectAsStateWithLifecycle()

    // Find the user from the loaded users
    val user = remember(usersState) {
        (usersState as? UserListState.Success)?.users?.find { it.id == userId }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Member Details",
                showBackButton = true,
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = { /* Share user profile */ }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share",
                            tint = OnSurface
                        )
                    }
                }
            )
        },
        containerColor = Background
    ) { padding ->
        if (user == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator(message = "Loading user details...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Profile Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryContainer)
                        .padding(vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UserAvatar(
                        username = user.username,
                        size = 96.dp,
                        //fontSize = 36.sp
                    )
                    AppSpacer.Medium()
                    Text(
                        text = user.username,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        color = OnSurface
                    )
                    AppSpacer.Small()
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = user.email,
                            fontSize = 14.sp,
                            color = OnSurfaceVariant
                        )
                    }
                }

                // User Info Cards
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Account Info Card
                    InfoCard(
                        title = "Account Information",
                        items = listOf(
                            InfoItem(
                                icon = Icons.Default.AccountCircle,
                                label = "User ID",
                                value = user.id
                            ),
                            InfoItem(
                                icon = Icons.Default.Person,
                                label = "Username",
                                value = user.username
                            ),
                            InfoItem(
                                icon = Icons.Default.Email,
                                label = "Email",
                                value = user.email
                            ),
                            InfoItem(
                                icon = Icons.Default.DateRange,
                                label = "Member Since",
                                value = user.createdAt
                            )
                        )
                    )

                    // Quick Actions Card
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
                                text = "Quick Actions",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                color = OnSurface
                            )
                            AppSpacer.Small()
                            
                            ActionButton(
                                icon = Icons.Default.Email,
                                label = "Send Email",
                                onClick = { /* TODO: Open email client */ }
                            )
                            
                            ActionButton(
                                icon = Icons.Default.Info,
                                label = "View Activity",
                                onClick = { /* TODO: Navigate to activity */ }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    items: List<InfoItem>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                color = OnSurface
            )
            
            items.forEachIndexed { index, item ->
                if (index > 0) {
                    HorizontalDivider(color = OutlineVariant)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        item.icon,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.label,
                            fontSize = 12.sp,
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.W500
                        )
                        AppSpacer.Small()
                        Text(
                            text = item.value,
                            fontSize = 15.sp,
                            color = OnSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = OnSurface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = label,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private data class InfoItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String,
    val value: String
)
