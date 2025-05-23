package com.followme.ui.screens.autenticacao.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.followme.R
import com.followme.ui.theme.GrayColor
import com.followme.ui.theme.Primary
import com.followme.ui.theme.Secondary
import com.followme.ui.theme.TextColor

@Composable
fun TextoCentrado(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .padding(vertical = 25.dp)
            .fillMaxWidth()
            .heightIn(min = 20.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center

    )
}

@Composable
fun TextoCentradoBold(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center
    )
}

@Composable
fun IntroduzirTextoNormal(
    labelValue: String, textValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        singleLine = true,
        maxLines = 1,
        value = textValue,
        onValueChange = {
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus

    )
}


@Composable
fun IntroduzirTextoEmail(
    labelValue: String, textValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        singleLine = true,
        maxLines = 1,
        value = textValue,
        onValueChange = {
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )
}

@Composable
fun IntroduzirPassoword(
    labelValue: String,
    fieldValue: String,
    icon: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {

    val localFocusManager = LocalFocusManager.current
    val passwordVisible = rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = fieldValue,
        onValueChange = {
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = icon, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                painterResource(id = R.drawable.passwordvisivel)

            } else {
                painterResource(id = R.drawable.passwordinvisivel)
            }

            val description = if (passwordVisible.value) {
                stringResource(id = R.string.esconder_password)
            } else {
                stringResource(id = R.string.mostrar_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}


@Composable
fun CheckBoxTermosCondicoes(
    privacidade: () -> Unit,
    termos: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        ) {

        val checkedState = rememberSaveable {
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onCheckedChange.invoke(it)
            })

        Hiperligacao_TermosCondicoes(privacidade, termos)
    }
}

@Composable
fun Hiperligacao_TermosCondicoes(privacidade: () -> Unit, termos: () -> Unit) {
    val initialText = "Aceitar "
    val privacyPolicyText = "Política "
    val andText = " e "
    val termsAndConditionsText = "Termos e Condições"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withLink(
            link = LinkAnnotation
                .Clickable(
                    tag = privacyPolicyText,
                    linkInteractionListener = { privacidade() },
                    styles = TextLinkStyles(style = SpanStyle(color = Primary))
                )
        ) {
            append(privacyPolicyText)
        }
        append(andText)

        withLink(
            link = LinkAnnotation
                .Clickable(
                    tag = termsAndConditionsText,
                    linkInteractionListener = { termos() },
                    styles = TextLinkStyles(style = SpanStyle(color = Primary))
                )
        ) {
            append(termsAndConditionsText)
        }
    }
    Text(text = annotatedString)
}


@Composable
fun Login_criarConta_hiperligacao(
    login: () -> Unit,
    criarConta: () -> Unit,
    tryingToLogin: Boolean = true
) {
    val initialText = if (tryingToLogin) "Já tens uma conta? " else "Não tens uma conta? "
    val loginText = if (tryingToLogin) "Login" else "Regista-te"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withLink(
            link = LinkAnnotation
                .Clickable(
                    tag = loginText,
                    styles = TextLinkStyles(
                        style = SpanStyle(color = Primary)
                    )
                ) {
                    if (tryingToLogin) {
                        login()
                    } else {
                        criarConta()
                    }
                }
        ) {
            append(loginText)
        }

    }
    Text(
        text = annotatedString,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}


@Composable
fun Linha_Divisora() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            thickness = 1.dp, color = GrayColor
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(R.string.ou),
            fontSize = 18.sp,
            color = TextColor
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            thickness = 1.dp, color = GrayColor
        )
    }
}


@Composable
fun BotaoLogin(
    value: String,
    onButtonClicked: () -> Unit, isEnabled: Boolean = false
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun Alerta_Erro_Login() {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("OK")
                }
            },
            title = { Text("Validar Informação") },
            text = { Text("Login Incorreto") }
        )
    }
}