package com.example.nsn11.dotcom;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

public class NotificationActivity {
    private Context mContext=null;

    private static final String NOTIFICATION_CHANNEL_ID="Noticiation_Channel_ID";

    public NotificationActivity(Context context)
    {
        mContext=context;
    }

    private void createNotificationChannel(String channelID, String title, String description)
    {   //Notification 채널을 만들지만 API 26이상에서만 만들기 때문에
        //NotificationChannel 클래스를 지원하는 새로운 라이브러리는 없음
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O)  //오레오 버전 이상일 시 저거 영문O임
        {
            int importance = NotificationManager.IMPORTANCE_DEFAULT; //기본 중요도로 처리

            //추가 작업이 필요한 경우:NotificationManager를 구성해야만 함.
            //채널 아이디, Notificacion 제목, 알림 중요도 설정
            NotificationChannel channel = new NotificationChannel(channelID,title,importance);

            //채널의 setDescription(노티 내용)을 이용하여 Nofiticaion 상세 내용을 설정한다.
            channel.setDescription(description);

            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        else
        {
            //sendNotification();
            //Toast.makeText(mContext,"버전이 낮은 경우 처리",Toast.LENGTH_SHORT).show();
        }
    }

    public void Notification()
    {
        createNotificationChannel(NOTIFICATION_CHANNEL_ID,"알림제목","알림설명");
        Intent intent = new Intent(mContext, MainActivity.class);
        //Intent intent1 = new Intent(mContext,NotiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        //PendingIntent mPendingIntent = PendingIntent.getActivities(this,0, new Intent[]{new Intent(getApplicationContext(), NotiActivity.class)},PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle("Dotcom")
                .setContentText("매칭을 시작했습니다!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mbuilder.build());
    }

    public void alarmNotification()
    {
        createNotificationChannel(NOTIFICATION_CHANNEL_ID,"알림제목","알림설명");
        Intent intent = new Intent(mContext, MainActivity.class);
        //Intent intent1 = new Intent(mContext,NotiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        //PendingIntent mPendingIntent = PendingIntent.getActivities(this,0, new Intent[]{new Intent(getApplicationContext(), NotiActivity.class)},PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle("Dotcom")
                .setContentText("견적서가 도착했습니다.확인해주세요")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mbuilder.build());
    }

}
