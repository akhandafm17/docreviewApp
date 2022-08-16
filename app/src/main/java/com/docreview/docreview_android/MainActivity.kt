package com.docreview.docreview_android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.NotificationCompat
import com.docreview.docreview_android.screens.MainScreen
import com.docreview.docreview_android.ui.theme.DocreviewDemoTheme
import com.smartarmenia.dotnetcoresignalrclientjava.*
import dagger.hilt.android.AndroidEntryPoint


//private val connection: HubConnection = WebSocketHubConnectionP2("http://10.0.2.2:5001/notification","")
private val connection: HubConnection = WebSocketHubConnectionP2("http://loremipsum.works/notification","")

@AndroidEntryPoint
class MainActivity : ComponentActivity(), HubConnectionListener, HubEventListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connection.addListener(this@MainActivity)
        connection.subscribeToEvent("SendNotification", this@MainActivity)
        showNotification(1,"New Comment added")
        setContent {
            try {
                connection.invoke("SendNotification", "docId")
            } catch (e: Exception) {
                //Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                e.message?.let { Log.d("SIGNALR", it) }
            }
            connect()
            DocreviewDemoTheme() {
                MainScreen()
            }
            //Creating hub connection
           // if (signalRListener.startConnection()){
           //     showNotification(signalRListener.getDocreviewText(),signalRListener.getCommentText())
           // }

        }
    }

    override fun onConnected() {
        Log.d("SIGNALR", "connected")
    }

    override fun onDisconnected() {
        Log.d("SIGNALR", "disconnected")
    }

    override fun onMessage(message: HubMessage?) {
        Log.d("SIGNALR", "onMessage: "+message.toString())
    }

    override fun onError(exception: Exception?) {
        Log.d("SIGNALR", "onError: "+exception.toString())
    }

    override fun onEventMessage(message: HubMessage?) {
        Log.d("SIGNALR", "onEventMessage: "+message.toString())

    }
    override fun onDestroy() {
        super.onDestroy()
        connection.removeListener(this)
        connection.unSubscribeFromEvent("SendNotification", this)
        connection.disconnect()
    }
    private fun connect() {
        try {
            connection.connect()
        } catch (ex: Exception) {
            ex.message?.let { Log.d("SIGNAlR", it) }
            runOnUiThread { Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_SHORT).show() }
        }
    }

    fun showNotification(docreviewTitle:Int, commentText: String){
        val intent = Intent(applicationContext, MainActivity::class.java)
        val CHANNEL_ID = "MYCHANNEL"
        val notificationChannel =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_HIGH)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 1, intent, 0)
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Docreview")
            .setContentText(commentText)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("New Comment in docreview \" ${docreviewTitle.toString()}\" "))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notification: Notification = builder


       val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
        //val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(1, notification)
    }

}

