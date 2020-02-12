package com.oleksiisaienko.p1_13notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Cat channel";

    private Button btnNotify;
    private int counter = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //указание звукового файла для уведомления
        final Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                //RingtoneManager.getDefaultUri(R.raw.ariya);

        //настройка вибрации для уведомления (+ нужно указать разрешение в манифесте)
        final long[] vibrate = new long[] { 100, 1000, 500, 500, 500, 500 };

        btnNotify = findViewById(R.id.btnNotify);
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Созданиея намерения (что будет по нажатию на уведомление (запуск приложения))
                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent contentIntent =
                        PendingIntent.getActivity(MainActivity.this, 0,
                                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                //Создание уведомления
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_error_outline_black_24dp)
                        .setContentTitle("Напоминание")
                        .setContentText("Пора покормить кота")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.alarm))
                        .setTicker("Последнее Китайское предупреждение!") //до Lollipop
                        .setProgress(100, 50, true)
                        //.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                        //.setDefaults(Notification.DEFAULT_ALL) //вибрация, звук, свет по умолчанию
                        .setSound(ringUri)
                        .setVibrate(vibrate)
                        //Включение светодиодной индикации
                        .setLights(Color.RED, 1, 0)
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent);

                //Вызов (передача/показ) уведомления
                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(MainActivity.this);
                //notificationManager.notify(NOTIFY_ID, builder.build()); //пример с константой - одно уведомление
                notificationManager.notify(counter++, builder.build()); // может быть несколько уведомлений
            }
        });
    }
}
