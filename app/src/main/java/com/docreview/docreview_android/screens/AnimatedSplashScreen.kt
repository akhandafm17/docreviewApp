package com.docreview.docreview_android.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.docreview.docreview_android.R
import com.docreview.docreview_android.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController){
    var startAnim by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(targetValue = if (startAnim) 1f else 0f,
    animationSpec = tween(durationMillis = 2000))
    
    LaunchedEffect(key1 = true){
        startAnim =true
        delay(2000)
        navController.navigate(Screen.Login.route)
    }
    Splash(alpha = alphaAnim.value)

}
@Composable
fun Splash(alpha: Float){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.ic_logo),contentDescription = "logo", alpha = alpha )
        Text(text = stringResource(id = R.string.app_name), fontSize = 30.sp,textAlign = TextAlign.Center)

    }
}

@Preview
@Composable
fun SplashScreenPreview(){
 Splash(alpha = 1f)
}



