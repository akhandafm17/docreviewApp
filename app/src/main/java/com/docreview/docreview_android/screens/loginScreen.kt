package com.docreview.docreview_android.screens


import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.docreview.docreview_android.R
import com.docreview.docreview_android.model.Login
import com.docreview.docreview_android.navigation.NavBarScreen
import com.docreview.docreview_android.screens.viewModel.UserViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavHostController,viewModel: UserViewModel = hiltViewModel()){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var username by remember {mutableStateOf("") }
    var password by remember {mutableStateOf("") }
    var flag by remember {mutableStateOf(false) }
    var showLoginEror by remember {mutableStateOf(false) }
    var isPasswordVisible by remember {mutableStateOf(false)}
    val getLogin = viewModel.getLogin.observeAsState()
    val isFormValid by derivedStateOf {
        true
    }


    Scaffold(backgroundColor = MaterialTheme.colors.primary) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .weight(1f)
                    .size(300.dp)

            )
            Text(text = stringResource(id = R.string.app_name), fontSize = 40.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Card(
                Modifier
                    .weight(2f)
                    .padding(top = 30.dp)
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Text(text = "Login", fontWeight = FontWeight.Medium, fontSize = 30.sp)
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = username,
                            onValueChange = { username = it },
                            label = { Text(text = "Username") },
                            singleLine = true,
                            trailingIcon = {
                                if (username.isNotBlank())
                                    IconButton(onClick = { username = "" }) {
                                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                                    }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "Password") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Password Toggle"
                                    )
                                }
                            }
                        )
                        if (showLoginEror){
                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                elevation = 3.dp,
                                backgroundColor = Color(0xFFFF8981),
                                shape = RoundedCornerShape(corner = CornerSize(16.dp))
                            ) {
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(11.dp)
                                            .fillMaxWidth()
                                            .align(Alignment.CenterVertically)) {
                                        Text(text = stringResource(id = R.string.falseinputs),
                                            style = MaterialTheme.typography.h6,
                                            color = Color.White,
                                            fontSize = 10.sp)
                                    }

                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    var login: Login = Login(
                                        username = username,
                                        password = password,
                                    )
                                     viewModel.login(login)

                                    if (getLogin.value!!.success){
                                        //val userJson = Gson().toJson(login)
                                        //navController.currentBackStackEntry?.arguments?.putParcelable("login",login)
                                        // https://www.geeksforgeeks.org/jetpack-compose-navigation-and-passing-data-in-android/
                                        navController.navigate(NavBarScreen.Home.route)
                                    }else{
                                        showLoginEror = true
                                    }

                                }

                            },
                            enabled = isFormValid,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "Log In")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = { }) {
                                Text(text = "Forgot Password?", color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}




