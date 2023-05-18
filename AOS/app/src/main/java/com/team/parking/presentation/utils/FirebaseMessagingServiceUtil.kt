package com.team.parking.presentation.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.kakao.sdk.common.KakaoSdk.type
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.model.notification.GetNotiListRequest
import kotlin.math.log


private const val TAG = "FirebaseMessage종건"
class FirebaseMessagingServiceUtil : FirebaseMessagingService(){
    var gson = Gson()
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var mainActivity: MainActivity
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: ${token}")
    }

    /**
     * 디바이스가 FCM을 통해서 메시지를 받으면 수행된다.
     * @remoteMessage: FCM에서 보낸 데이터 정보들을 저장한다.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // FCM을 통해서 전달 받은 정보에 Notification 정보가 있는 경우 알림을 생성한다.
        if (remoteMessage.notification != null){
            sendNotification(remoteMessage)
            mainActivity= MainActivity.getInstance()!!
            sp = PreferenceManager.getDefaultSharedPreferences(this)
            mainActivity.notificationViewModel.getNotiList(sp.getLong("user_id",0))
        }else{
            Log.d(TAG, "수신 에러: Notification이 비어있습니다.")
        }
    }

    /**
     * FCM에서 보낸 정보를 바탕으로 디바이스에 Notification을 생성한다.
     */
    private fun sendNotification(remoteMessage: RemoteMessage){
        val id = 0
        var title = remoteMessage.notification!!.title
        var body = remoteMessage.notification!!.body

        Log.d(TAG, "sendNotification: ${body}")
        var data : GetNotiListRequest = gson.fromJson(body,GetNotiListRequest::class.java)
        var content = data.content

        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setAction("NOTIFICATION_CLICK");

        if(data.type == 0){
            // 로그아웃
            sp = PreferenceManager.getDefaultSharedPreferences(this)

            editor = sp.edit()
            editor.clear()
            editor.commit()

        }else {
            Log.d(TAG, "sendNotification: ${data.noti_id}")
            Log.d(TAG, "sendNotification: ${data.ticket_id}")
            Log.d(TAG, "sendNotification: ${data.type}")
            intent.putExtra("noti_id", data.noti_id)
            intent.putExtra("user_id", data.user_id)
            intent.putExtra("type", data.type)
            intent.putExtra("ticket_id", data.ticket_id)
        }

        val pendingIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val noti_id = intent.getLongExtra("noti_id", -1)
        Log.i(TAG, "222213: ${noti_id}")
        val channelId = "Channel ID"
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val channel =
                NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH)

            notificationManager.createNotificationChannel(channel)

            notificationManager.notify(id, notificationBuilder.build())
        }
    }
}