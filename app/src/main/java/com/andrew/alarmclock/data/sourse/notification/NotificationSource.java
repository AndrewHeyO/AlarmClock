package com.andrew.alarmclock.data.sourse.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.service.disturb.DisturbReceiver;
import com.andrew.alarmclock.utils.Constant;

import javax.inject.Inject;

public class NotificationSource implements INotificationSource {

    private Context context;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    @Inject
    public NotificationSource(Context context,
                              NotificationManager manager,
                              NotificationCompat.Builder builder) {
        this.context = context;
        this.manager = manager;
        this.builder = builder;
    }

    @Override
    public void createNotification(String description, boolean shouldDisturb, boolean isDeleted) {
        if(isDeleted) return;
        boolean isKitkat = android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
        if(shouldDisturb) {
            createNotification(R.string.notification_title,
                    isKitkat ? R.drawable.ic_notification_alarm_on : R.drawable.ic_notification_alarm_on_vector,
                    description,
                    R.string.notification_alarms_turn_off,
                    isKitkat ? R.drawable.ic_notification_bell_off : R.drawable.ic_notification_bell_off_vector,
                    DisturbReceiver.DISTURB_ACTION);
        } else {
            createNotification(R.string.notification_title_off,
                    isKitkat ? R.drawable.ic_notification_alarm_off : R.drawable.ic_notification_alarm_off_vector,
                    "",
                    R.string.notification_alarms_turn_on,
                    isKitkat ? R.drawable.ic_notification_bell_on : R.drawable.ic_notification_bell_on_vector,
                    DisturbReceiver.NO_DISTURB_ACTION);
        }
    }

    @Override
    public void removeNotification() {
        manager.cancel(Constant.NOTIFICATION_ID);
    }


    private void createNotification(int titleStringId, int titleIconId, String description,
                                    int actionStringId, int actionIconId, String actionDisturb) {
        NotificationCompat.Action action;
        builder.setContentTitle(context.getString(titleStringId));
        builder.setSmallIcon(titleIconId);
        builder.setContentText(description);
        action = new NotificationCompat.Action(actionIconId,
                context.getString(actionStringId),
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, DisturbReceiver.class)
                                .setAction(actionDisturb),
                        PendingIntent.FLAG_UPDATE_CURRENT));
        if(builder.mActions.size() == 1) {
            builder.mActions.set(0, action);
        } else {
            builder.addAction(action);
        }
        manager.notify(Constant.NOTIFICATION_ID, builder.build());
    }
}
