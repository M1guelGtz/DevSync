package com.m1guelgtz.templatecarsapi.Demo.Features.Projects.Presentation.Components.Atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.CaptionText

@Composable
fun ProjectStatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status.lowercase()) {
        "active" -> Color.Green.copy(alpha = 0.2f)
        "in_progress" -> Color.Blue.copy(alpha = 0.2f)
        "completed" -> Color.Gray.copy(alpha = 0.2f)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    
    val textColor = when (status.lowercase()) {
        "active" -> Color.Green.copy(alpha = 0.8f)
        "in_progress" -> Color.Blue.copy(alpha = 0.8f)
        "completed" -> Color.Gray.copy(alpha = 0.8f)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        CaptionText(
            text = status.uppercase(),
            color = textColor
        )
    }
}
