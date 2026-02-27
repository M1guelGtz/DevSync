package com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Components.Organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.MediumSpacer
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.SubtitleText
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.TitleText
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Domain.Entities.User
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Components.Atoms.UserAvatar

@Composable
fun UserDetailContent(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                username = user.username,
                size = 80.dp
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                TitleText(text = user.username)
                MediumSpacer()
                SubtitleText(text = user.email)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        
        InfoRow(label = "ID", value = user.id)
        MediumSpacer()
        InfoRow(label = "Email", value = user.email)
        MediumSpacer()
        InfoRow(label = "Usuario", value = user.username)
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SubtitleText(text = label)
        Spacer(modifier = Modifier.height(4.dp))
        TitleText(text = value)
    }
}
