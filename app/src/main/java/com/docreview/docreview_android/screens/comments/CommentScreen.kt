package com.docreview.docreview_android.screens.comments

import android.annotation.SuppressLint
import android.util.Log
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.EditText
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.docreview.docreview_android.R
import com.docreview.docreview_android.model.*
import com.docreview.docreview_android.screens.UserInput
import com.docreview.docreview_android.screens.viewModel.CommentViewModel

import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList


var docreview: Docreview ?= null
var login: Login ?= null

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MessageCard(
    comment: Comment,
    viewModel: CommentViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_avatar),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))


        Card(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            var isExpanded by remember { mutableStateOf(false) }
            var showIconBtns by remember { mutableStateOf(false) }
            var showEditField by remember { mutableStateOf(false) }
            var editCommenttext by remember { mutableStateOf(TextFieldValue("")) }
            val focusRequester = remember { FocusRequester() }


            Column(modifier = Modifier.clickable () {
                showIconBtns = !showIconBtns
                isExpanded = !isExpanded
            }) {
                Text(
                    //text = user!!.userName,
                    text = "test",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )

                Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = comment.content,
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                if (showEditField){
                    Column(modifier = Modifier.clickable () {
                        showEditField = !showEditField
                    }) {
                        TextField(
                            value = editCommenttext,
                            onValueChange = { newText ->
                                editCommenttext = newText
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    var editedComment = CreateCommentViewModel(
                                        id = comment.commentId,
                                        content = editCommenttext.text,
                                        startIndex = 0,
                                        endIndex = 0,
                                        upvotes = 0,
                                        parent = true,
                                        selectedText = "",
                                        username = "projectmanager",
                                        //user = user!!.userName,
                                        DocReviewId = docreview!!.id,
                                        docreviewname = docreview!!.docname
                                    )
                                    scope.launch {

                                        viewModel.updateComment(comment.commentId,editedComment)
                                    }
                                    showEditField = false
                                    showEditField = true
                                    showEditField = false
                                     }
                        ),
                            modifier = Modifier
                                .onKeyEvent {
                                if (it.nativeKeyEvent.keyCode == KEYCODE_ENTER){
                                   // focusRequester.requestFocus()
                                    var editedComment = CreateCommentViewModel(
                                        id = comment.commentId,
                                        content = editCommenttext.text,
                                        startIndex = 0,
                                        endIndex = 0,
                                        upvotes = 0,
                                        parent = true,
                                        selectedText = "",
                                        username = "projectmanager",
                                        //user = user!!.userName,
                                        DocReviewId = docreview!!.id,
                                        docreviewname = docreview!!.docname
                                    )
                                     scope.launch {

                                         viewModel.updateComment(comment.commentId,editedComment)
                                     }
                                }
                                false
                            })
                    }
                }

                if (showIconBtns){
                    Row() {
                        IconButton(onClick = {
                        showEditField = true
                        }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "edit"
                            )
                        }
                        IconButton(onClick = {
                           scope.launch {
                               viewModel.deleteComment(comment.commentId)
                           }
                        }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "delete"
                            )
                        }
                    }
                }

            }
            }
        }
        }

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun Conversation(viewModel: CommentViewModel = hiltViewModel(), navController: NavHostController) {
    val getAllComments = viewModel.getComments.observeAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val docIntent = navController.previousBackStackEntry?.arguments?.getParcelable<Docreview>("doc")
    docreview = docIntent
    val userIntent = navController.previousBackStackEntry?.arguments?.getParcelable<Login>("login")
    login = userIntent


    scope.launch {
        val result = docreview?.let { viewModel.getComments(it.id) }

       // if (result is Resource.Success) {
       //     Toast.makeText(context, "Fetching comments success!", Toast.LENGTH_SHORT).show()
       // } else if (result is Resource.Error) {
       //     Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_SHORT)
       //         .show()
       // }
    }
    if (viewModel.isLoading.value) {
        if (viewModel.getComments.value!!.isNotEmpty()) {
            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(modifier = Modifier.height(570.dp)
                ) {

                        items(getAllComments.value!!.size) { index ->
                            MessageCard(getAllComments.value!![index])
                        }


                }
                Row(
                    modifier = Modifier
                        .weight(1f, false)
                        .padding(bottom = 55.dp)
                ) {
                    UserInput(
                        onMessageSent = { content ->
                        scope.launch {
                            var c = CreateCommentViewModel(
                                id = 5,
                                content = content,
                                startIndex = 0,
                                endIndex = 0,
                                upvotes = 0,
                                parent = true,
                                selectedText = "",
                                username = "projectmanager",
                                DocReviewId = docreview!!.id,
                                docreviewname = docreview!!.docname
                            )
                            docreview?.let {
                                if (c != null) {
                                    viewModel.addComment(c)
                                }
                            }
                        }
                    })
                }

            }

        }else{
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painter = painterResource(id = R.drawable.ic_empty), contentDescription = "empty")
                Spacer(modifier = Modifier.height(10.dp))
                Text( text = stringResource(id = R.string.empty),
                        style = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                    textAlign = TextAlign.Center
                ))
            }
        }
    }
}
