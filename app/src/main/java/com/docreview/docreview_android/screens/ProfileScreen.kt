package com.docreview.docreview_android.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.docreview.docreview_android.R
import com.docreview.docreview_android.model.Docreview
import com.docreview.docreview_android.screens.viewModel.ProfileViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.docreview.docreview_android.model.Login
import com.docreview.docreview_android.model.Resource
import com.docreview.docreview_android.screens.comments.login
import com.docreview.docreview_android.screens.viewModel.UserViewModel

import kotlinx.coroutines.launch

private var countDocs: Int = 0
private var assignedDocs: Int = countDocs
private var totalComments: Int = 0
var login: Login ?= null


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(navController: NavHostController){
    Scaffold() {
        Column() {
            ProfileSection()
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CallApi(navController = navController)
        }
    }
}

@Composable
fun ProfileSection(){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            RoundImage(
                image = painterResource(id = R.drawable.ic_avatar),
                modifier = Modifier
                    .size(100.dp)
                    .weight(3f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatSection(modifier = Modifier.weight(7f))
        }

                Spacer(modifier = Modifier.height(10.dp))

            ProfileDescription(
                displayName = "test",
                description = "Project Manager",
                documentName = "",
            )
        }

            }





@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
){
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatSection(modifier: Modifier = Modifier){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        ProfileStat(numberText = assignedDocs.toString(), text = "assigned docs")
       // ProfileStat(numberText = totalComments.toString(), text = "comments")
    }
}
@Composable
fun ProfileStat(
    numberText: String,
    text: String,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = numberText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}
@Composable
fun ProfileDescription(
    displayName: String,
    description: String,
    documentName: String,
){
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = displayName,
            fontWeight = FontWeight.Bold,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = description,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = documentName,
            color = Color(0xFF3D3D91),
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )

    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun CallApi(
    viewModel: ProfileViewModel = hiltViewModel(),navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val getAllDocreviews = viewModel.getDocreviews.observeAsState()
    val userIntent = navController.previousBackStackEntry?.arguments?.getParcelable<Login>("login")
    login = userIntent

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                scope.launch {
                   viewModel.getDocreviews()
                }

                if (!viewModel.isLoading.value) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color.Black )
                    }
                }



                if (viewModel.isLoading.value) {
                    if (viewModel.getDocreviews.value!!.isNotEmpty()) {


                            LazyColumn(
                                modifier = Modifier.padding(10.dp)
                                                   .fillMaxWidth()
                                    .fillMaxSize(1F)
                                    .padding(bottom = 45.dp)
                            ) {
                                assignedDocs = getAllDocreviews.value!!.size
                                items(getAllDocreviews.value!!.size) { index ->
                                    Spacer(modifier = Modifier.padding(top = 10.dp))
                                    DocListItem(
                                        getAllDocreviews.value!![index],
                                        navController = navController
                                    )
                                }
                            }

                    }
                }
            }
        }
    }
}

@Composable
fun DocListItem(doc: Docreview, navController: NavHostController) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = {
                //send variable
                navController.currentBackStackEntry?.arguments?.putParcelable("doc",doc)
                navController.navigate("comments")
            }),
        elevation = 3.dp,
        backgroundColor = Color(0xFF485B84),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))


    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)) {
                Text(text = doc.docname, style = typography.h6)

            }

        }
    }
}