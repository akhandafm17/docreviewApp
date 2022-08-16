package com.docreview.docreview_android.screens


import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.docreview.docreview_android.R
import com.docreview.docreview_android.ui.theme.Shapes



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(navController: NavHostController){
    Scaffold(backgroundColor = Color(0xFFEEDD)) {
        Column {

            HeaderText()
            GeneralOptionsUI()
            SupportOptionsUI()
        }
    }
}

@Composable
fun HeaderText() {
    Text(
        text = "Settings",
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
fun GeneralOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "General",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        lightDarkMode()

//       GeneralSettingItem()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun lightDarkMode (){
    val context = LocalContext.current
    val checkedState = remember { mutableStateOf(false) }
    Card(
        onClick = {  },
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lightdarkmode),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    Text(
                        text = "Light/Dark mode",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Switch(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    setNightMode(context,checkedState.value)
                }
            )

        }
    }
}

fun setNightMode(target: Context, state: Boolean) {
    val uiManager = target.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    if (state) {
        uiManager.nightMode = UiModeManager.MODE_NIGHT_NO
    } else {
        uiManager.nightMode = UiModeManager.MODE_NIGHT_YES
    }
}
@ExperimentalMaterialApi
@Composable
fun GeneralSettingItem(icon: ImageVector, mainText: String, subText: String, onClick: () -> Unit) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notifications),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    Text(
                        text = mainText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = subText,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-4).dp)
                    )
                }
            }

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SupportOptionsUI() {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }
    var titlePopup = remember { mutableStateOf("false") }
    if (openDialog.value) {


        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {
                Text(text = "About")
            },
            text = {
                Text("This DocreviewApp is made for the project managers. the app is used to act quickly when receiving notifications.")
            },
            confirmButton = {
                Button(

                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Ok")
                }
            }
        )

    }
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "Support",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = R.drawable.ic_contact,
            mainText = "Contact",
            onClick = {
                //titlePopup.value = "Contact"
                //openDialog.value = !openDialog.value
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.setData(Uri.parse("mailto:marwan.akhandaf@student.kdg.be"))
                startActivity(context, intent, null)

            }
        )
        val privUrl = "https://www.termsfeed.com/public/uploads/2019/04/privacy-policy-template.pdf";
        SupportItem(
            icon = R.drawable.ic_policy,
            mainText = "Privacy Policy",
            onClick = {
                CustomTabsIntent
                    .Builder()
                    .build()
                    .launchUrl(context, Uri.parse(privUrl)) }
        )

        SupportItem(
            icon = R.drawable.ic_about,
            mainText = "About",
            onClick = {
                //titlePopup.value = "About"
                openDialog.value = !openDialog.value

            }
        )
    }
}




@ExperimentalMaterialApi
@Composable
fun SupportItem(icon: Int, mainText: String, onClick: () -> Unit) {
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
                        painter = painterResource(id = icon),
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
@Composable
fun AboutDialog() {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false)  }

            Button(onClick = {
                openDialog.value = true
            }) {
                Text("Click me")
            }

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Dialog Title")
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(

                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("This is the Confirm Button")
                        }
                    },
                    dismissButton = {
                        Button(

                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("This is the dismiss Button")
                        }
                    }
                )
            }
        }

    }
}




