package com.turkcell.lyraapp.ui.auth.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.turkcell.lyraapp.ui.theme.LyraAppTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onEffect: (LoginEffect) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            onEffect(effect)
        }
    }

    LoginContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
private fun LoginContent(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp)
                .padding(top = 260.dp, bottom = 28.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            LyraLogo()

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Tekrar hos geldin",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hesabina giris yap, kaldigin yerden\ndinlemeye devam et.",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(28.dp))

            LoginInputFrame(
                label = "Telefon numarasi",
                leading = "[]",
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "+90",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        LoginTextInput(
                            value = uiState.phoneNumber,
                            onValueChange = { value ->
                                onEvent(LoginEvent.PhoneChanged(value))
                            },
                            placeholder = "5XX XXX XX XX",
                            keyboardType = KeyboardType.Phone,
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(14.dp))

            LoginInputFrame(
                leading = "[]",
                trailing = if (uiState.isPasswordVisible) "hide" else "show",
                onTrailingClick = {
                    onEvent(LoginEvent.PasswordVisibilityClicked)
                },
                content = {
                    LoginTextInput(
                        value = uiState.password,
                        onValueChange = { value ->
                            onEvent(LoginEvent.PasswordChanged(value))
                        },
                        placeholder = "Sifre",
                        keyboardType = KeyboardType.Password,
                        visualTransformation = if (uiState.isPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                    )
                },
            )

            TextButton(
                onClick = {
                    onEvent(LoginEvent.ForgotPasswordClicked)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 6.dp),
            ) {
                Text(
                    text = "Sifremi unuttum",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    onEvent(LoginEvent.LoginClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            ) {
                Text(
                    text = "Giris yap  ->",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Hesabin yok mu?",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                )
                TextButton(
                    onClick = {
                        onEvent(LoginEvent.RegisterClicked)
                    },
                ) {
                    Text(
                        text = "Kayit ol",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = textStyle,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
private fun LyraLogo() {
    val logoShape = RoundedCornerShape(16.dp)
    Box(
        modifier = Modifier
            .size(54.dp)
            .shadow(
                elevation = 20.dp,
                shape = logoShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary,
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = logoShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        EqualizerIcon(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Composable
private fun LoginInputFrame(
    label: String? = null,
    leading: String,
    trailing: String? = null,
    onTrailingClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(horizontal = 14.dp),
    ) {
        if (label != null) {
            Text(
                text = label,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 4.dp)
                    .align(Alignment.TopStart),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = if (trailing == null) 0.dp else 48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = leading,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(12.dp))
            content()
        }

        if (trailing != null && onTrailingClick != null) {
            TextButton(
                onClick = onTrailingClick,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Text(
                    text = trailing,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun EqualizerIcon(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.4.dp.toPx()
        val centerY = size.height / 2f
        val lines = listOf(
            0.26f to 0.34f,
            0.40f to 0.62f,
            0.54f to 0.88f,
            0.68f to 0.52f,
            0.82f to 0.28f,
        )

        lines.forEach { (xRatio, heightRatio) ->
            val lineHeight = size.height * heightRatio
            val x = size.width * xRatio
            drawLine(
                color = color,
                start = Offset(x, centerY - lineHeight / 2f),
                end = Offset(x, centerY + lineHeight / 2f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round,
            )
        }
    }
}

@Preview(name = "Login Light", showBackground = true)
@Composable
private fun LoginScreenLightPreview() {
    LyraAppTheme(darkTheme = false) {
        LoginContent(
            uiState = LoginUiState(),
            onEvent = {},
        )
    }
}

@Preview(name = "Login Dark", showBackground = true)
@Composable
private fun LoginScreenDarkPreview() {
    LyraAppTheme(darkTheme = true) {
        LoginContent(
            uiState = LoginUiState(),
            onEvent = {},
        )
    }
}
