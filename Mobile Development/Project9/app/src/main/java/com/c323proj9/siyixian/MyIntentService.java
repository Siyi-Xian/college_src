package com.c323proj9.siyixian;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    private Long time;

    //Thread management is done by OS.

    public MyIntentService() {
        super("my Single Working Service!");
    }

    public MyIntentService(String name) {
        super("my Single Working Service!");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        time = intent.getLongExtra("Time", 0);
        Toast.makeText(this, "Alarm sat!", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Alarm stopped!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        synchronized (this) {
            try {
                wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            stopSelf();
            SharedPreferences sharedPreferences = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
            String message_subject = sharedPreferences.getString("Message", "");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(message_subject)
                    .setContentText(message_subject);
            Intent resultIntent = new Intent(this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }
    }

}
