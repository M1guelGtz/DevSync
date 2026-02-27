package com.m1guelgtz.templatecarsapi.Demo.Features.Projects.Presentation.Components.Organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.AppIcon
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.CaptionText
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.MediumSpacer
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.SubtitleText
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.TitleText
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Molecules.BaseCard
import com.m1guelgtz.templatecarsapi.Demo.Features.Projects.Domain.Entities.Project

@Composable
fun ProjectCardExtended(
    project: Project,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TitleText(
                text = project.name,
                maxLines = 2
            )
            
            project.description?.let {
                MediumSpacer()
                SubtitleText(
                    text = it,
                    maxLines = 3
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Created date",
                    size = 16.dp,
                    tint = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(4.dp))
                CaptionText(
                    text = "Creado: ${project.createdAt}"
                )
            }
        }
    }
}
