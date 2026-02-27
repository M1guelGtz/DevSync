package com.m1guelgtz.templatecarsapi.Demo.Features.Auth.Presentation.Components.Organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.AppButton
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.AppIcon
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.AppTextField
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.LargeSpacer
import com.m1guelgtz.templatecarsapi.Demo.Core.Ui.components.Atoms.MediumSpacer
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginForm(
    email: String,
    password: String,
    showPassword: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowPasswordToggle: () -> Unit,
    onLoginClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            placeholder = "ejemplo@email.com",
            leadingIcon = {
                AppIcon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email"
                )
            },
            singleLine = true
        )
        
        MediumSpacer()
        
        AppTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            placeholder = "••••••••",
            leadingIcon = {
                AppIcon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password"
                )
            },
            trailingIcon = {
                IconButton(onClick = onShowPasswordToggle) {
                    AppIcon(
                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true
        )
        
        LargeSpacer()
        
        AppButton(
            text = "Iniciar Sesión",
            onClick = onLoginClick,
            enabled = !isLoading && email.isNotBlank() && password.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
