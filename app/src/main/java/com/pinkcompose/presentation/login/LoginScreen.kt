package com.pinkcompose.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pinkcompose.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {

    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
            onLoginSuccess()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .background(color = colorResource(R.color.beige))
            )
            {

                val (image, logintext, column, loginBtn, demoCredential) = createRefs()

                Image(
                    painter = painterResource(R.drawable.ic_cake),
                    contentDescription = "Cake",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(column.top)
                            height = Dimension.fillToConstraints
                        }
                )

                Text(
                    text = stringResource(R.string.login),
                    fontSize = 26.sp,
                    color = colorResource(R.color.pink),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .background(
                            color = colorResource(R.color.light_pink),
                            shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                        )
                        .padding(horizontal = 15.dp, vertical = 5.dp)
                        .constrainAs(logintext) {
                            start.linkTo(parent.start)
                            bottom.linkTo(column.top)
                        }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .constrainAs(column) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
                {
                    // Email Field
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        label = { Text(stringResource(R.string.email)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = colorResource(R.color.pink),
                            unfocusedLabelColor = Color.Gray,
                            focusedLabelColor = colorResource(R.color.pink)
                        )
                    )

                    // Password Field
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = { Text(stringResource(R.string.password)) },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisible.value = !passwordVisible.value
                            }) {
                                Icon(
                                    painter = painterResource(
                                        if (passwordVisible.value)
                                            R.drawable.ic_eye_open
                                        else
                                            R.drawable.ic_eye_closed
                                    ),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = if (passwordVisible.value)
                                        "Hide password" else "Show password"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible.value)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = colorResource(R.color.pink),
                            unfocusedLabelColor = Color.Gray,
                            focusedLabelColor = colorResource(R.color.pink)
                        )
                    )
                }

                // Login Button
                Button(
                    onClick = { viewModel.onLoginClick() },
                    enabled = !state.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .constrainAs(loginBtn) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(column.bottom)
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.pink),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .background(
                            colorResource(R.color.light_pink),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                        .constrainAs(demoCredential) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(loginBtn.bottom)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Text("Demo Account", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("demo@gmail.com / 123456", fontSize = 13.sp)
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colorResource(R.color.pink)
                )
            }
        }
    }
}
