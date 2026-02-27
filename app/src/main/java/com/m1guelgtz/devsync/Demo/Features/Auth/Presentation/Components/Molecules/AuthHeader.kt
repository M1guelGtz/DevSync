package com.m1guelgtz.templatecarsapi.Demo.Features.Auth.Presentation.Components.Molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.MediumSpacer
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.SubtitleText
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.TitleText

@Composable
fun AuthHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TitleText(text = title)
        MediumSpacer()
        SubtitleText(text = subtitle)
    }
}
