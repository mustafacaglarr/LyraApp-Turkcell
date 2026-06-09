package com.turkcell.lyraapp.ui.auth.register

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.turkcell.lyraapp.ui.theme.LyraAppTheme

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onEffect: (RegisterEffect) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            onEffect(effect)
        }
    }

    RegisterContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
private fun RegisterContent(
    uiState: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(horizontal = 42.dp)
                .padding(top = 38.dp, bottom = 28.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            IconButtonFrame(onClick = { onEvent(RegisterEvent.BackClicked) }) {
                BackIcon(color = MaterialTheme.colorScheme.onSurface)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Hesap oluştur",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Birkaç adımda Lyra’ya katıl ve çalma\nlisteni oluştur.",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                RegisterInputFrame(modifier = Modifier.weight(1f)) {
                    RegisterTextInput(
                        value = uiState.firstName,
                        onValueChange = { value -> onEvent(RegisterEvent.FirstNameChanged(value)) },
                        placeholder = "Ad",
                        keyboardType = KeyboardType.Text,
                        textStyle = MaterialTheme.typography.bodyMedium,
                    )
                }

                RegisterInputFrame(modifier = Modifier.weight(1f)) {
                    RegisterTextInput(
                        value = uiState.lastName,
                        onValueChange = { value -> onEvent(RegisterEvent.LastNameChanged(value)) },
                        placeholder = "Soyad",
                        keyboardType = KeyboardType.Text,
                        textStyle = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            RegisterInputFrame(label = "Telefon numarası") {
                PhoneIcon(color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "+90",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(14.dp))
                RegisterTextInput(
                    value = uiState.phoneNumber,
                    onValueChange = { value -> onEvent(RegisterEvent.PhoneChanged(value)) },
                    placeholder = "5XX XXX XX XX",
                    keyboardType = KeyboardType.Phone,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            RegisterInputFrame {
                LockIcon(color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(14.dp))
                RegisterTextInput(
                    value = uiState.password,
                    onValueChange = { value -> onEvent(RegisterEvent.PasswordChanged(value)) },
                    placeholder = "Şifre",
                    keyboardType = KeyboardType.Password,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    visualTransformation = if (uiState.isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                )
                IconButtonFrame(onClick = { onEvent(RegisterEvent.PasswordVisibilityClicked) }) {
                    EyeIcon(color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            PasswordStrengthHint()

            Spacer(modifier = Modifier.height(22.dp))

            TermsRow(
                isChecked = uiState.isTermsAccepted,
                onClick = { onEvent(RegisterEvent.TermsAcceptanceClicked) },
            )

            Spacer(modifier = Modifier.height(22.dp))

            Button(
                onClick = { onEvent(RegisterEvent.RegisterClicked) },
                enabled = uiState.canSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f),
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            ) {
                Text(
                    text = "Kayıt ol  →",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Zaten hesabın var mı?",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                )
                TextButton(onClick = { onEvent(RegisterEvent.LoginClicked) }) {
                    Text(
                        text = "Giriş yap",
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
private fun RegisterInputFrame(
    modifier: Modifier = Modifier,
    label: String? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = modifier
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
            )
        }

        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

@Composable
private fun RegisterTextInput(
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
private fun PasswordStrengthHint() {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(3.dp)
                        .background(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(2.dp),
                        ),
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "En az 8 karakter, bir rakam içermeli.",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun TermsRow(
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(
                    color = if (isChecked) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    shape = RoundedCornerShape(5.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            CheckIcon(color = MaterialTheme.colorScheme.onPrimary)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Kullanım Koşulları ve Gizlilik\nPolitikası’nı okudum, kabul ediyorum.",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun IconButtonFrame(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun BackIcon(color: Color) {
    Canvas(modifier = Modifier.size(22.dp)) {
        val strokeWidth = 2.dp.toPx()
        drawLine(color, Offset(size.width * 0.25f, size.height * 0.5f), Offset(size.width * 0.75f, size.height * 0.5f), strokeWidth, StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.25f, size.height * 0.5f), Offset(size.width * 0.45f, size.height * 0.30f), strokeWidth, StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.25f, size.height * 0.5f), Offset(size.width * 0.45f, size.height * 0.70f), strokeWidth, StrokeCap.Round)
    }
}

@Composable
private fun PhoneIcon(color: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        drawRoundRect(
            color = color,
            topLeft = Offset(size.width * 0.28f, size.height * 0.08f),
            size = Size(size.width * 0.44f, size.height * 0.84f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = 1.8.dp.toPx()),
        )
        drawCircle(color = color, radius = 0.9.dp.toPx(), center = Offset(size.width * 0.5f, size.height * 0.82f))
    }
}

@Composable
private fun LockIcon(color: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        drawRoundRect(
            color = color,
            topLeft = Offset(size.width * 0.22f, size.height * 0.42f),
            size = Size(size.width * 0.56f, size.height * 0.44f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = 1.8.dp.toPx()),
        )
        drawArc(
            color = color,
            startAngle = 200f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = Offset(size.width * 0.32f, size.height * 0.12f),
            size = Size(size.width * 0.36f, size.height * 0.48f),
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round),
        )
    }
}

@Composable
private fun EyeIcon(color: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        drawOval(
            color = color,
            topLeft = Offset(size.width * 0.12f, size.height * 0.30f),
            size = Size(size.width * 0.76f, size.height * 0.40f),
            style = Stroke(width = 1.6.dp.toPx()),
        )
        drawCircle(color = color, radius = 2.3.dp.toPx(), center = Offset(size.width * 0.5f, size.height * 0.5f))
    }
}

@Composable
private fun CheckIcon(color: Color) {
    Canvas(modifier = Modifier.size(12.dp)) {
        drawLine(color, Offset(size.width * 0.18f, size.height * 0.52f), Offset(size.width * 0.42f, size.height * 0.74f), 1.8.dp.toPx(), StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.42f, size.height * 0.74f), Offset(size.width * 0.84f, size.height * 0.28f), 1.8.dp.toPx(), StrokeCap.Round)
    }
}

@Preview(name = "Register Light", showBackground = true)
@Composable
private fun RegisterScreenLightPreview() {
    LyraAppTheme(darkTheme = false) {
        RegisterContent(
            uiState = RegisterUiState(isTermsAccepted = true),
            onEvent = {},
        )
    }
}

@Preview(name = "Register Dark", showBackground = true)
@Composable
private fun RegisterScreenDarkPreview() {
    LyraAppTheme(darkTheme = true) {
        RegisterContent(
            uiState = RegisterUiState(isTermsAccepted = true),
            onEvent = {},
        )
    }
}
