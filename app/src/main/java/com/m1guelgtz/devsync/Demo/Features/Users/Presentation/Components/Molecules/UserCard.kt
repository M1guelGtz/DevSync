package com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Components.Molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.SmallSpacer
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.SubtitleText
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.TitleText
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Molecules.BaseCard
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Domain.Entities.User
import com.m1guelgtz.templatecarsapi.Demo.Features.Users.Presentation.Components.Atoms.UserAvatar

@Composable
fun UserCard(
    user: User,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(username = user.username)
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                TitleText(
                    text = user.username,
                    maxLines = 1
                )
                SmallSpacer()
                SubtitleText(
                    text = user.email,
                    maxLines = 1
                )
            }
        }
    }
}
