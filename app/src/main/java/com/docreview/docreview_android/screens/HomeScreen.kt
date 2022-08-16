package com.docreview.docreview_android.screens


import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.docreview.docreview_android.R
import com.docreview.docreview_android.model.Login
import com.docreview.docreview_android.ui.theme.Shapes

var logindet: Login ?= null

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Homepage(navController: NavHostController){
    Scaffold() {
        Column() {
           // Log.d("NALU",username)
            TopText()
            MiddleBody(navController = navController)
            GraphOptionsUI(navController = navController)
        }
    }
}


@Composable
fun TopText() {
    Text(
        text = "Home",
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp, start = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 35.sp
    )
}


@ExperimentalMaterialApi
@Composable
fun MiddleBody(navController: NavHostController) {
    val privUrl = "https://www.loremipsum.works"
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
       Row() {
           CardItem(
               image = R.drawable.ic_logo,
               mainText = "Web App",
               onClick = {
                   CustomTabsIntent
                       .Builder()
                       .build()
                       .launchUrl(context, Uri.parse(privUrl))
               }
           )
           CardItem(
               image = R.drawable.ic_logo,
               mainText = "Profile",
               onClick = {
                   navController.navigate("profile")
               }
           )
       }

    }
}


@ExperimentalMaterialApi
@Composable
fun CardItem(image: Int, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color(0xFF485B84),
        modifier = Modifier
            .padding(start = 10.dp, end = 20.dp)
            .width(157.dp)
            .height(210.dp),

        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {


                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    Text(
                        text = mainText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                            //.padding(top = 10.dp, bottom = 10.dp, start = 10.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                }
            }

        }
    }
}
@ExperimentalMaterialApi
@Composable
fun GraphOptionsUI(navController: NavHostController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "Statistics",
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 35.dp, bottom = 10.dp, start = 10.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 35.sp
        )
        GraphItem(
            icon = Icons.Default.Comment,
            mainText = "CommentsGraph"
        ) {
            //TODO
            navController.navigate("commentsGraph")
        }
        GraphItem(
            icon = Icons.Default.PeopleAlt,
            mainText = "UsersGraph"
        ) {
            navController.navigate("usersGraph")
        }

        GraphItem(
            icon = Icons.Default.Info,
            mainText = "OtherGraph"
        ) {
            navController.navigate("othersGraph")
        }
    }
}



@ExperimentalMaterialApi
@Composable
fun GraphItem(icon: ImageVector, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color(0xFF485B84),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(Color(0xFF485B84))
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = mainText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }

}



